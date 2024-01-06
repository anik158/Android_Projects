package com.ahsan.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;



import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class UpdateContactActivity extends AppCompatActivity {

    private EditText nameInfo, emailInfo, phoneHome, phoneOffice;
    private ImageView profileImg;
    private Button saveBtn, cancelBtn;
    private DatabaseHelper dbHelper;
    private String originalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        dbHelper = new DatabaseHelper(this);

        nameInfo = findViewById(R.id.nameInfo);
        emailInfo = findViewById(R.id.email_info);
        phoneHome = findViewById(R.id.phone_home);
        phoneOffice = findViewById(R.id.phone_office);
        profileImg = findViewById(R.id.up_profile_img);
        saveBtn = findViewById(R.id.save_update_btn);
        cancelBtn = findViewById(R.id.cancel_update_btn);

        originalName = getIntent().getStringExtra("contact_name");
        loadContactData(originalName);

        saveBtn.setOnClickListener(v -> updateContact());
        cancelBtn.setOnClickListener(v -> finish());
    }

    private void loadContactData(String contactName) {
        Contact contact = dbHelper.getContact(contactName);
        if (contact != null) {
            nameInfo.setText(contact.getName());
            emailInfo.setText(contact.getEmail());
            phoneHome.setText(contact.getPhoneHome());
            phoneOffice.setText(contact.getPhoneOffice());
            if (contact.getPhoto() != null && !contact.getPhoto().isEmpty()) {
                Bitmap bitmap = decodeBase64ToBitmap(contact.getPhoto());
                profileImg.setImageBitmap(bitmap);
            } else {
                profileImg.setImageResource(R.drawable.person_icon_contact_list);
            }
        }
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] imageBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private void updateContact() {
        String name = nameInfo.getText().toString().trim();
        String email = emailInfo.getText().toString().trim();
        String phoneHomeText = phoneHome.getText().toString().trim();
        String phoneOfficeText = phoneOffice.getText().toString().trim();

        Drawable drawable = profileImg.getDrawable();
        String imageBase64 = "";
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            imageBase64 = encodeToBase64(bitmap);
        }

        boolean isUpdated = dbHelper.updateContact(originalName, name, email, phoneHomeText, phoneOfficeText, imageBase64);
        if (isUpdated) {
            Toast.makeText(this, "Contact updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error updating contact", Toast.LENGTH_SHORT).show();
        }
    }


    private String encodeToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
