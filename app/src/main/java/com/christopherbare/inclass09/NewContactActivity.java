package com.christopherbare.inclass09;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class NewContactActivity extends AppCompatActivity {
    private final int PICK_IMAGE_CAMERA = 1;
    ImageView imageViewSelect;
    EditText editTextName, editTextPhone, editTextEmail;
    RadioGroup radioGroup;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    int id;

    private FirebaseStorage storage;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        imageViewSelect = findViewById(R.id.contactImage);
        editTextName = findViewById(R.id.contactFirstName);
        editTextPhone = findViewById(R.id.contactLastName);
        editTextEmail = findViewById(R.id.contactEmail);



        findViewById(R.id.contactAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK_IMAGE_CAMERA);
            }
        });

        findViewById(R.id.buttonAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();



                if(name.equals("")){
                    Toast.makeText(NewContactActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if(email.equals("")){
                    Toast.makeText(NewContactActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                } else if(phone.equals("")){
                    Toast.makeText(NewContactActivity.this, "Enter phone", Toast.LENGTH_SHORT).show();
                } else{
                    Contact person = new Contact(name, phone, email);
                    person.picID = id;
                    mDatabase.child("contacts")
                            .child(mAuth.getCurrentUser().getUid())
                            .push()
                            .setValue(person);
                    finish();
                }
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            int iconId = data.getExtras().getInt("ICON");
            id = iconId;

            imageViewSelect.setImageResource(iconId);

            /*
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), iconId);
            //imageViewSelect.setImageBitmap(bitmap);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
*/


        }
    }
}
