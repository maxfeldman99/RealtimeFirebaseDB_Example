package com.example.maxfeldman.realtimefirebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "aristid";


    EditText editText;
    Button add_button;
    Spinner spinner;
    DatabaseReference databaseArtists;

    ListView listViewArtists;
    List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.main_et);
        add_button = findViewById(R.id.main_add_btn);
        spinner = findViewById(R.id.main_spinner);
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        listViewArtists = findViewById(R.id.list_view_artists);

        artistList = new ArrayList<>();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artistList.get(i);
                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot artistSnapeshot: dataSnapshot.getChildren()){

                    Artist artist = artistSnapeshot.getValue(Artist.class);
                    artistList.add(artist);
                }

               ArtistList adapter = new ArtistList(MainActivity.this,artistList);
                listViewArtists.setAdapter(adapter); // every time a new data will be added this method will be executed and it will fetch all data from firebase;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addArtist(){
        String name = editText.getText().toString().trim();
        String genre = spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){  // if the name is not empty

            String id = databaseArtists.push().getKey(); // every time it will generate a unique id
            Artist artist = new Artist(id,name,genre);
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this, "Artist added", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();

        }
    }


}
