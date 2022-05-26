package mirea.it.cloudtasksstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Класс, регистрирующий или авторизующий пользователя в системе
 */
public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "Authorization";

    private EditText mEmail;
    private EditText mPassword;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Основной метод жизненного цикла Activity, точка входа в программу
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            /**
             * Метод, который вызывается каждый раз, когда состояние авторизированного изменяется: он входит или выходит
             * @param firebaseAuth - используется для получения ссылка на пользователя
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User signed in");
                    Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
                    startActivity(intent);
                }

                //...
            }
        };

        mEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mPassword = (EditText) findViewById(R.id.editTextTextPassword);

        findViewById(R.id.buttonEntry).setOnClickListener(this);
        findViewById(R.id.buttonReg).setOnClickListener(this);

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
            startActivity(intent);
        }
    }


    /**
     * @param view - параметр, позволяющий получить id кнопки, которая была нажата
     *             Метод вызывается при нажатии на кнопку: "Войти" или "Зарегистрироваться"
     *             Метод перенаправляет пользователя на следующую страницу, если авторизация успешна.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonEntry) {
            SignIn(mEmail.getText().toString(), mPassword.getText().toString());
        } else if (view.getId() == R.id.buttonReg) {
            SignUp(mEmail.getText().toString(), mPassword.getText().toString());
        }
    }

    /**
     * @param email    - Почта пользователя
     * @param password - Пароль пользователя
     *                 Метод, позволяющий пользователю авторизоваться и попасть на слледующий экран
     */
    public void SignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * Метод, который вызывается после регистрации нового пользователя с почтой и пароле
             * В случае успешной регистрации пользователь попадает на следующую страницу.
             *
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EmailPasswordActivity.this, "Произошла какая-то ошибка... Попробуйте ещё", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param email    - Почта пользователя
     * @param password - Пароль пользователя
     *                 Метод, позволяющий пользователю зарегистрироваться и попасть на следующий экран
     */
    public void SignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * Метод, который вызывается после регистрации нового пользователя с почтой и паролем.
             * В случае успешной регистрации пользователь попадает на следующую страницу.
             *
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EmailPasswordActivity.this, "Произошла какая-то ошибка... Попробуйте ещё", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}