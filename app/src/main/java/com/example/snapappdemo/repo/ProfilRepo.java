package com.example.snapappdemo.repo;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.snapappdemo.TaskListener;
import com.example.snapappdemo.Updatable;
import com.example.snapappdemo.model.Snap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilRepo {

//    //med til at implementere signleton, så der kun er en kopi af dette objekt.
//    private static ProfilRepo profilRepo = new ProfilRepo();
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    //constuctor til singleton
//    public static ProfilRepo profilRepo() {
//        return profilRepo; }
//
//
//    // denne metode skal kaldes fra myProfil
//    public void profilInfo(TextView usernameText, TextView nameText, TextView emailText, TextView bioText){
//        //indsæter info til din profil
//        DocumentReference reference = db.collection("profil").document();
//        Map<String, TextView> map = new HashMap<>();
//        map.put("brugernavn", usernameText);
//        reference.set(map);
//        map.put("navn", nameText);
//        reference.set(map);
//        map.put("email", emailText);
//        reference.set(map);
//        map.put("biografi", bioText);
//        reference.set(map);
//
//        System.out.println("done med bruger info" + reference.getId());
//    }

}