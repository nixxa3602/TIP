package com.smartherd.assignment_tip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    private Button logout;
    private EditText weight;
    private Button Record;
    //private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        weight = (EditText)findViewById(R.id.editTextTextPersonName7);
        Record = (Button)findViewById(R.id.button4);
       // mAuth = FirebaseAuth.getInstance();

        Record.setOnClickListener(new View.OnClickListener() { // also firebase register here
            @Override
            public void onClick(View v) {
                RecordWeight();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        final TextView welcome = findViewById(R.id.textView10);
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                String name = userProfile.name;

                welcome.setText("Welcome, " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout = findViewById(R.id.button8);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity3.this, MainActivity.class));
            }
        });
    }

    private void RecordWeight() {

        String weight1 = weight.getText().toString().trim();
        if (weight1.isEmpty()) {
            weight.setError("Name is required!");
            weight.requestFocus();
            return;

        }


    }
}