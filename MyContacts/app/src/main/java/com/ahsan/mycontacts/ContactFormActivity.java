package com.ahsan.mycontacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class ContactFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView profileImg;
    private EditText nameInfo, emailInfo, phoneHome, phoneOffice;
    private Button saveBtn, cancelBtn;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        dbHelper = new DatabaseHelper(this);

        profileImg = findViewById(R.id.profile_img);
        nameInfo = findViewById(R.id.nameInfo);
        emailInfo = findViewById(R.id.email_info);
        phoneHome = findViewById(R.id.phone_home);
        phoneOffice = findViewById(R.id.phone_office);
        saveBtn = findViewById(R.id.save_btn);
        cancelBtn = findViewById(R.id.cancel_btn);

        ImageView cameraButton = findViewById(R.id.camera);
        cameraButton.setOnClickListener(v -> openGallery());

        saveBtn.setOnClickListener(v -> saveContact());
        cancelBtn.setOnClickListener(v -> cancel());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            profileImg.setImageURI(data.getData());
        }
    }

    private void saveContact() {
        String name = nameInfo.getText().toString().trim();
        String email = emailInfo.getText().toString().trim();
        String phoneHomeText = phoneHome.getText().toString().trim();
        String phoneOfficeText = phoneOffice.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phoneHomeText.isEmpty() || phoneOfficeText.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Drawable drawable = profileImg.getDrawable();
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(this, R.drawable.person_icon_contact_list);
        }

        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            bitmap = getBitmapFromVectorDrawable((VectorDrawable) drawable);
        }

        String imageBase64 = "";
        if (bitmap != null) {
            imageBase64 = encodeToBase64(bitmap);
        }

        long id = dbHelper.saveContact(name, email, phoneHomeText, phoneOfficeText, imageBase64);
        if (id > 0) {
            Intent intent = new Intent(ContactFormActivity.this,ContactListActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving contact", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }


    private String encodeToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void cancel() {
        finish();
    }
}
