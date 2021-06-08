package com.example.snapappdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.snapappdemo.adapter.MyAdapter;
import com.example.snapappdemo.model.Snap;
import com.example.snapappdemo.repo.Repo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity implements Updatable{

    // skal have billeder fra db ind i denne liste
    List<Snap> items = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;

    BottomNavigationView navigationView;
    TextView userNameAndName, userInfo;

    //forbindelse til Realtime DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference().child("Profil");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bruges til info på startside
        userNameAndName = findViewById(R.id.usernameText2);
        userInfo = findViewById(R.id.Text3);


        //vi skal lave en forbindelse til listView her.
        //R kompiler hele tiden, så f.eks hvergang der bliver lavet en knap rekompiler den klassen.
        //Hver gang vi laver noget nyt, bliver et nyt nr(id) klar
        listView = findViewById(R.id.listView1);
        // vi impotere adapteren og vælger listen som parameter og "this" er context vi vælger som er den mest alm.
        myAdapter = new MyAdapter(items, this);
        listView.setAdapter(myAdapter);
        navBar();
        Repo.repo().setup(this, items);
        setupListView();
        showProfilInfo();

    }

    //___________________ METODER______________________

//    //Denne knap skal fører dig til din tagbillede.
//    public void TakePicturePressed(View view){
//        // vi laver her en Intent med en action, så vi kan åbne cameraet og tage et billede som skal retunere det.
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try{
//            startActivityForResult(takePictureIntent, 1);
//        } catch (ActivityNotFoundException e){
//            System.out.println("error: du kan ikke tage billede pt");
//        }
//    }

    public void showProfilInfo() {
        //denne metode er til for at læse fra database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue(String.class);
                String name = snapshot.child("name").getValue(String.class);
                String biografi = snapshot.child("biografi").getValue(String.class);

                userNameAndName.setText(username + "\n" + name);
                userInfo.setText(biografi);
            }
            @Override
            //hvis noget går galt, kommer denne fejl
            public void onCancelled(DatabaseError error) {
                System.out.println("Kunne ikke læse data: " + error.getCode());

            }
        });
    }


    //navigation bar
    private void navBar(){
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.home);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.profil:
                    Intent intent = new Intent(MainActivity.this, MyProfil.class);
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

    //Ting til at capture picture og indsætte text
    // Skal måske flyttes til repo


    @Override
    // denne skal tjekke om der er en requestCode for en aktivitet som er startet
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //hvis requestCode er den samme som npr vi åbner kameraet, så kan vi få insertTest metoden på bitmap
        if (requestCode == 1) {
            insertText((Bitmap)data.getExtras().get("data"));
            System.out.println("Bitmap: " + (Bitmap)data.getExtras().get("data"));

        }
    }

    // denne metode er til for at lave tekst på et billede
    public void insertText(Bitmap image){
        //Laver pop op med en text
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Indsæt text");

        //her er det muligt at gøre så man kan skrive i teksten
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", ((dialog, which) -> insertTextToBitmap(image, input.getText().toString())));
        builder.setNegativeButton("Annuller", (dialog, which) -> dialog.cancel());

        // for alarmen
        builder.show();

    }

    // skal bruges i insertText
    public void insertTextToBitmap(Bitmap image, String gText) {
        Bitmap.Config bitmapConfig = image.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        image = image.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(image);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);// new antialised Paint
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((int) (20)); // text size in pixels
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE); // text shadow
        canvas.drawText(gText, 10, 100, paint);
        Repo.repo().uploadBitmap(image, gText);
    }

    //_________________________________________________

    //Når vi skal åbne et billede
    private void setupListView(){
        //når man klikker vil vi gerne have den tager et "item" med et id fra listviewet fra db
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Her opretter vi et opbjekt med postisiton fra items
                Snap tempSnap = items.get(position);

                // her bliver der oprettet et Intet, hvor vi siger vi er i Main og vil til SnapOpen
                Intent snapIntent = new Intent(MainActivity.this, SnapOpen.class);

                //Her tildeles et id, således vi kan "getID" når et billede åbnes
                //som bruges i snapOpen klassen
                snapIntent.putExtra("id", tempSnap.getId());

                startActivity(snapIntent);


            }
        });
    }


    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }

}





