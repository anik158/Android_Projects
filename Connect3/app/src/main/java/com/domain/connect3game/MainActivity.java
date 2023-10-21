package com.domain.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //0 : Yellow; 1: Red; 2: empty
    int activePlayer = 0;

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8}, //Rows
            {0,3,6},{1,4,7},{2,5,8}, //Columns
            {0,4,8},{2,4,6} //Diagonal
    };

    boolean won = false;

    String msg = "";

    public void putIn(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        ImageView imgView = (ImageView) view;

        TextView txt = findViewById(R.id.txt1);

        int tappedCounter = Integer.parseInt(imgView.getTag().toString());

        if (gameState[tappedCounter] == 2 && !won) {

            gameState[tappedCounter] = activePlayer;

        imgView.setTranslationY(-screenHeight);

        if (activePlayer == 0) {
            imgView.setImageResource(R.drawable.yellow);
            if(!checkWinner(txt)){
                txt.setText("Red's Play");
            }
            activePlayer = 1;
        } else {
            imgView.setImageResource(R.drawable.red);
            if(!checkWinner(txt)){
                txt.setText("Yellow's Play");
            }
            activePlayer = 0;
        }

            MediaPlayer audioPlayer = MediaPlayer.create(this,R.raw.woosh);
            imgView.animate().translationYBy(screenHeight).rotation(720).setDuration(1500);
            audioPlayer.start();
        }else if(won){
            txt.setText(msg+" is the winner.");
        }else{
            txt.setText("Already filled");
        }
    }


    public boolean checkWinner(TextView txt){

        for(int[] winner:
        winningPositions){
            if(gameState[winner[0]] == gameState[winner[1]] && gameState[winner[1]]==gameState[winner[2]] && gameState[winner[0]] != 2){

                if(activePlayer==0){
                        msg = "Yellow";
                        txt.setText(msg+" is the winner.");
                        won = true;
                        return true;
                }else{
                        msg = "Red";
                        txt.setText(msg+" is the winner.");
                        won = true;
                        return true;
                }
            }
        }
        return false;
    }

    public void reset(View view) {

        won = false;
        String turn = "Yellow";
        activePlayer = 0;
        Arrays.fill(gameState, 2);

        for (int i = 0; i < gameState.length; i++) {
            int boxId = getResources().getIdentifier("box_" + (i + 1), "id", getPackageName());
            ImageView box = findViewById(boxId);
            box.setImageDrawable(null);
        }

        TextView textView = findViewById(R.id.txt1);
        textView.setText(turn+"'s Play");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}