package com.example.snapappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snapappdemo.repo.ProfilRepo;
import com.example.snapappdemo.repo.Repo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfil extends AppCompatActivity{

    //Viser tekst
    //private TextView usernameText, nameText, emailText, bioText;

    //redigere i tekst
    private EditText usernameEditText, nameEditText, emailEditText, bioEditText;
    private Button button;


    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);

        usernameEditText = findViewById(R.id.usernameText);
        //usernameEditText = findViewById(R.id.usernameText);

        nameEditText = findViewById(R.id.nameText);
        //nameEditText = findViewById(R.id.nameText);

        emailEditText = findViewById(R.id.emailText);
        //emailEditText = findViewById(R.id.emailText);

        bioEditText = findViewById(R.id.bioText);
        //bioEditText = findViewById(R.id.bioText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                root.setValue(username);
            }
        });


    }

//    public void SaveProfilPressed(View view){
//        //gemmer den text som vi har skrevet ind og erstatter den gamle.
////        usernameText.setText(usernameEditText.getText());
////        nameText.setText(nameEditText.getText());
////        emailText.setText(emailEditText.getText());
////        bioText.setText(bioEditText.getText());
////        ProfilRepo.profilRepo().profilInfo(usernameText, nameText, emailText, bioText);
//        System.out.println("færdig");
//    }

//    @Override
//    public void update(Object o) {
//    }
}