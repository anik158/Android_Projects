package com.domain.higherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int ranNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateRandomNumber();
    }

    private void generateRandomNumber() {
        double randomNumber = Math.random();
        ranNumber = (int)(randomNumber*20+1);
    }

    public void guessNumber(View view){

        EditText editText = findViewById(R.id.number);
        String strText = editText.getText().toString();

        if(strText.equals("")){
            Toast.makeText(this,"Enter a number",Toast.LENGTH_LONG).show();
            return;
        }



        int triedNumber = Integer.parseInt(strText);

        if(triedNumber>20){
            Toast.makeText(this,"Number must be less than 21",Toast.LENGTH_LONG).show();
            return;
        }

        if(ranNumber>triedNumber){
            Toast.makeText(this,triedNumber+ " bit lower",Toast.LENGTH_LONG).show();
        }else if(ranNumber<triedNumber){
            Toast.makeText(this,triedNumber+ " bit higher",Toast.LENGTH_LONG).show();
        } else if (ranNumber==triedNumber) {
            Toast.makeText(this,"Guess correctly "+triedNumber,Toast.LENGTH_LONG).show();
            generateRandomNumber();
        }
    }
}
