package com.domain.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convertToBDT(View view) {
        EditText amn = findViewById(R.id.amount);
        String amnText = amn.getText().toString();

        double amount = Double.parseDouble(amnText);

        double convertedAmount = amount*110;

        TextView textView = findViewById(R.id.showAmount);

        textView.setText("$"+amount+" is ৳"+String.format("%.2f",convertedAmount) +" Bangladeshi taka.");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}