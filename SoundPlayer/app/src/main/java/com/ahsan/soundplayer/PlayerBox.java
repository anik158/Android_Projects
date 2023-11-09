package com.ahsan.soundplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayerBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_box);
    }

    public void exitFromPlayBox(View v){
        Intent ints = new Intent(this, SoundActivity.class);
        startActivity(ints);
    }
}