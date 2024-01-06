package com.ahsan.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    private ImageView contactImage;
    private TextView contactNameTextview, contactEmail, contactPhoneHome, contactPhoneOffice;
    private DatabaseHelper dbHelper;
    private String contactNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        contactImage = findViewById(R.id.contact_image);
        contactNameTextview = findViewById(R.id.contact_name);
        contactEmail = findViewById(R.id.contact_email);
        contactPhoneHome = findViewById(R.id.contact_phone_home);
        contactPhoneOffice = findViewById(R.id.contact_phone_office);

        dbHelper = new DatabaseHelper(this);

        contactNameString = getIntent().getStringExtra("contact_name");
        loadContactData(contactNameString);
    }

    private void loadContactData(String contactName) {
        Contact contact = dbHelper.getContact(contactName);
        if (contact != null) {
            contactNameTextview.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
            contactPhoneHome.setText(contact.getPhoneHome());
            contactPhoneOffice.setText(contact.getPhoneOffice());

            if (contact.getPhoto() != null && !contact.getPhoto().isEmpty()) {
                Bitmap bitmap = decodeBase64ToBitmap(contact.getPhoto());
                contactImage.setImageBitmap(bitmap);
            } else {
                contactImage.setImageResource(R.drawable.person_icon_contact_list); // Replace with your placeholder image
            }
        }
    }
    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] imageBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public void goBack(View view){
        finish();
    }
}
