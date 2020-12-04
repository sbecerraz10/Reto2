package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private EditText usernameET;
    private Button loginBtn;

    private User user;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        usernameET = findViewById(R.id.usernameET);
        loginBtn = findViewById(R.id.loginBtn);
        database = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener((v)->{
            String username = usernameET.getText().toString();

            if(username==null || username.equals("")){
                runOnUiThread( ()->{
                    Toast.makeText(this,"Ingresa un nombre de usuario",Toast.LENGTH_LONG).show();
                });
                return;
            }



            CollectionReference usersReference = database.collection("users");
            Query query = usersReference.whereEqualTo("username", username);
            query.get().addOnCompleteListener(
                    task -> {
                        User user = new User(UUID.randomUUID().toString(), username);
                        Intent i = new Intent(this, CatchPokeActivity.class);
                        if (task.isSuccessful()) {

                            if (task.getResult().size() > 0) {
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    user = document.toObject(User.class);
                                    break;
                                }
                            } else {
                                database.collection("users").document(user.getId()).set(user);
                            }
                        }
                        i.putExtra("user",user);
                        startActivity(i);
                    }
            );



        });



    }
}