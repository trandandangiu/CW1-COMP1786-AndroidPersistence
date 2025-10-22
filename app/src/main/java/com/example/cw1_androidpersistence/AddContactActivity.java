package com.example.cw1_androidpersistence;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail;
    ImageView ivAvatar;
    Button btnSave, btnChoose;
    DatabaseHelper db;

    boolean isEditMode = false;
    int contactId = -1;
    String avatarUri = "";

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    avatarUri = uri.toString();
                    ivAvatar.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        ivAvatar = findViewById(R.id.ivAvatar);
        btnSave = findViewById(R.id.btnSave);
        btnChoose = findViewById(R.id.btnChooseImage);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode) {
            contactId = intent.getIntExtra("contactId", -1);
            Contact c = db.getContactById(contactId);
            if (c != null) {
                etName.setText(c.getName());
                etPhone.setText(c.getPhone());
                etEmail.setText(c.getEmail());
                avatarUri = c.getAvatar();
                if (avatarUri != null && avatarUri.startsWith("content://")) {
                    ivAvatar.setImageURI(Uri.parse(avatarUri));
                }
            }
            btnSave.setText("Update Contact");
        }

        btnChoose.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String email = etEmail.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                db.updateContact(contactId, name, phone, email, avatarUri);
                Toast.makeText(this, "Contact updated!", Toast.LENGTH_SHORT).show();
            } else {
                db.addContact(name, phone, email, avatarUri);
                Toast.makeText(this, "Contact added!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
