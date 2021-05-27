package com.example.snapappdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class MyProfil extends AppCompatActivity{

    //redigere i tekst
    private EditText usernameEditText, nameEditText, emailEditText, bioEditText;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference().child("Profil");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);

        usernameEditText = findViewById(R.id.usernameText);
        nameEditText = findViewById(R.id.nameText);
        emailEditText = findViewById(R.id.emailText);
        bioEditText = findViewById(R.id.bioText);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue(String.class);
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String biografi = snapshot.child("biografi").getValue(String.class);

                usernameEditText.setText(username);
                nameEditText.setText(name);
                emailEditText.setText(email);
                bioEditText.setText(biografi);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());

            }
        });

    }


    // denne metode er til for at gemme data fra felter i DB-realtime
    public void SaveProfilPressed(View view){
        String username = usernameEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String biografi = bioEditText.getText().toString();

        Map<String, String> profilMap = new HashMap<>();

        profilMap.put("username", username);
        profilMap.put("name", name);
        profilMap.put("email", email);
        profilMap.put("biografi", biografi);

        reference.setValue(profilMap);

        System.out.println("færdig");
    }


}