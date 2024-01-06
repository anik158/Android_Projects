package com.ahsan.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahsan.mycontacts.Contact;
import com.ahsan.mycontacts.ContactAdapter;
import com.ahsan.mycontacts.DatabaseHelper;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahsan.mycontacts.Contact;
import com.ahsan.mycontacts.ContactAdapter;
import com.ahsan.mycontacts.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.recyclerViewJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();

        contactAdapter = new ContactAdapter(contactList, this::onItemClick, this::onItemLongClick);
        recyclerView.setAdapter(contactAdapter);

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> logout());

        Button buttonAddContacts = findViewById(R.id.buttonAddJobs);
        buttonAddContacts.setOnClickListener(v -> navigateToAddContact());

    }

    private void onItemClick(Contact contact) {
        Intent intent = new Intent(ContactListActivity.this, ContactDetailActivity.class);
        intent.putExtra("contact_name", contact.getName());
        startActivity(intent);
    }

    private void onItemLongClick(Contact contact) {
        new AlertDialog.Builder(this)
                .setTitle("Contact Options")
                .setMessage("Choose an action for this contact:")
                .setPositiveButton("Delete", (dialog, which) -> deleteContact(contact))
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Update", (dialog, which) -> navigateToUpdateContactActivity(contact))
                .show();
    }

    private void navigateToUpdateContactActivity(Contact contact) {
        Intent intent = new Intent(ContactListActivity.this, UpdateContactActivity.class);
        intent.putExtra("contact_name", contact.getName());
        startActivity(intent);
    }

    private void deleteContact(Contact contact) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (dbHelper.deleteContact(contact.getName())) {
            loadContactsFromDatabase();
            Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToAddContact() {
        Intent intent = new Intent(this, ContactFormActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContactsFromDatabase();
    }

    private void loadContactsFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        contactList = dbHelper.getAllContacts();

        contactAdapter.setContactList(contactList);
        contactAdapter.notifyDataSetChanged();
    }
}
