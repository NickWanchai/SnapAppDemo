package com.example.snapappdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class MyProfil extends AppCompatActivity {

    //redigere i tekst
    private EditText usernameEditText, nameEditText, emailEditText, bioEditText;
    private BottomNavigationView navigationView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference().child("Profil");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);

        navbar();

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

    // navigation bar
    private void navbar(){
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.profil);
        navigationView.setOnNavigationItemSelectedListener(item ->

    {
        switch (item.getItemId()) {
            case R.id.profil:
                return true;
            case R.id.home:
                Intent intent = new Intent(MyProfil.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.takePicture:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //request code er til for at finde ud af hvem anmodningen kommer fra
                    startActivityForResult(takePictureIntent, 1);
                } catch (ActivityNotFoundException e) {
                    System.out.println("error: du kan ikke tage billede pt");
                }
                return true;
        }
        return false;
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