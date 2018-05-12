package com.example.maxfeldman.realtimefirebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    ListView listViewTracks;
    Button addTrackBtn;
    DatabaseReference databaseTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = findViewById(R.id.tv_tracks);
        editTextTrackName = findViewById(R.id.et_track_name);
        seekBarRating = findViewById(R.id.seek_bar_rating);
        listViewTracks = findViewById(R.id.list_view_tracks);
        addTrackBtn = findViewById(R.id.track_add_btn);

        Intent intent = getIntent();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
        // will create a new node in firebase that matches the id of the artist

        addTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });

    }
    private void saveTrack(){
        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();
        
        if(!TextUtils.isEmpty(trackName)){
            String id = databaseTracks.push().getKey();

            Track track = new Track(id,trackName,rating);

            databaseTracks.child(id).setValue(track);
            Toast.makeText(this, "Track saved Successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Track name cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
