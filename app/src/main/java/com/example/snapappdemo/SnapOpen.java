package com.example.snapappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.snapappdemo.model.Snap;
import com.example.snapappdemo.repo.Repo;

public class SnapOpen extends AppCompatActivity implements TaskListener{

    ImageView imageViewSnap;
    Snap snap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_open);
        //her skal vi finde og dl vores image fra snap_open xml
        imageViewSnap = findViewById(R.id.imageViewSnap);
        Intent intent = getIntent();
        snap = new Snap(intent.getStringExtra("id"));
        System.out.println(snap.getId());
        Repo.repo().downloadBitmap(snap.getId(), this);
    }

    // skal "ødelægge" det billede(objekt) som vi åbner (snap)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.repo().deleteImage(snap);
    }

    @Override
    public void receive(byte[] bytes) {
        imageViewSnap.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }
    public void SnapEnd(View view){
        finish();
    }

}