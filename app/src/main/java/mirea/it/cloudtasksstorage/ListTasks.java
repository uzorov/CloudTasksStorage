package mirea.it.cloudtasksstorage;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTasks extends AppCompatActivity {

    private boolean isEmptyFieldExist;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private ArrayList<String> DiscrTasks;
    int i = 0;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    FirebaseListAdapter mAdapter;

    Map<String, String> newValue = new HashMap<>();
    private EditText add_txt;
    private Button add_btn;
    private String uniqueKey = "0";
    ListView ListUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        Button add_btn = (Button) findViewById(R.id.btn_add);
        EditText add_txt = (EditText) findViewById(R.id.txt_add_task);

        ListUserTask = (ListView) findViewById(R.id.list_for_tasks);
        myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid());


        myRef.child("Tasks").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    if (String.valueOf(task.getResult().getValue()) == "") {
                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                        };
                        DiscrTasks.add(String.valueOf(task.getResult().getValue()));
                        updateUI();
                    } else {
                        Map<String, String> Empty = new HashMap<>();
                        Empty.put("0", "");
                        myRef.child("Tasks").setValue(Empty);
                        isEmptyFieldExist = true;
                    }
                }
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                    };
                    DiscrTasks = dataSnapshot.child("Tasks").getValue(t);
                    updateUI();
                }
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("adafadf", "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);


        add_btn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           if (isEmptyFieldExist) {
                                               myRef.child("Tasks").child(uniqueKey).removeValue();
                                               isEmptyFieldExist = false;
                                           }


                                           newValue.put(Integer.toString(i), add_txt.getText().toString());
                                           myRef.child("Tasks").setValue(newValue);
                                           i++;


                                           add_txt.setText("");
                                       }
                                   }
        );


        Button delButton = (Button) findViewById(R.id.btn_del);

        delButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
//                                             for (int j = i; j >= 0; j--) {
//                                                 myRef.child("Tasks").child(Integer.toString(j)).removeValue();
//                                             }
                                             Map<String, String> Empty = new HashMap<>();
                                             Empty.put("0", "");
                                             myRef.child("Tasks").setValue(Empty);
                                             DiscrTasks.clear();
                                             newValue.clear();
                                             i = 0;
                                         }
                                     }
        );


    }


    private void updateUI() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, DiscrTasks);
        ListUserTask.setAdapter(adapter);
    }


}