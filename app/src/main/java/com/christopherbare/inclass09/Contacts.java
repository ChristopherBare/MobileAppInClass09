package com.christopherbare.inclass09;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {
    private static final String TAG = "demo";
    ListView listView;
    ContactAdapter contactAdapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<Contact> contacts;
    TextView displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contacts = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        displayName = findViewById(R.id.displayName);
        displayName.setText(mAuth.getCurrentUser().getDisplayName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(Contacts.this, NewContactActivity.class);
                startActivity(intent);
            }
        });




        mDatabase.child("contacts").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contacts.clear();
                for (DataSnapshot node : dataSnapshot.getChildren() ) {

                    Contact contact = node.getValue(Contact.class);
                    contact.key = node.getKey();
                    contacts.add(contact);

                }

                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });

        listView = findViewById(R.id.listView);
        contactAdapter = new ContactAdapter(this,R.layout.contact_item, contacts);
        listView.setAdapter(contactAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDatabase.child("contacts")
                        .child(mAuth.getCurrentUser().getUid())
                        .child(contacts.get(i).key)
                        .removeValue();
                return false;
            }
        });
    }
}
