package com.ahsan.soundplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SoundActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        ArrayList<SoundItem> soundItems = new ArrayList<>();

        soundItems.add(new SoundItem(R.drawable.music,"Spring","Pixa Bay"));
        soundItems.add(new SoundItem(R.drawable.music,"Relaxing","Pixa Bay"));
        soundItems.add(new SoundItem(R.drawable.music,"Science Documentary","Pixa Bay"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SoundAdapter(soundItems,this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void exitFromSound(View view){
        finishAffinity();
    }
}