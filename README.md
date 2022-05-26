# CloudTasksStorage

Итоговый проект по ТРПП.

Группа: ИКБО-07-20

Тема: "Облачный органайзер списка дел"

Команда: Узоров Кирилл Илюхина Юлия Михиенков Кирилл

# Приложение облачный органайзер позволяет создавать заметки, составлять список дел или покупок и хранить это в облаке, используя Firebase Database, а также получать доступ к созданным записям с различных устройств, посредством регистрации в системе. 
В качестве системы сборки используется Gradle версии 7.3.3. Зависимости, необходимые для работы проекта (описаны в файле build.gradle (:app)):
dependencies {

    implementation platform('com.google.firebase:firebase-bom:30.0.2')

    // FirebaseUI for Firebase Realtime Database
    implementation 'com.firebaseui:firebase-ui-database:8.0.1'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.6.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-database'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}

Для корректной работы приложения необходимо использовать версию  Android не ниже 5.0.
