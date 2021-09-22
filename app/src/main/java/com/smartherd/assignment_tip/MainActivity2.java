package com.smartherd.assignment_tip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    private Button button_submit;
    private FirebaseAuth mAuth;
    private EditText email, password, name, pet_type,weight;
    //String[] ListOfWeights=new String[100];
    //int x;

//DatabaseReference databaseReference;
//ArrayList<Weights>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        //arrayList=new ArrayList<Weights>();

        mAuth= FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.editTextTextPersonName4);
        password= (EditText)findViewById(R.id.editTextTextPersonName5);
        name=(EditText)findViewById(R.id.editTextTextPersonName2);
        pet_type=(EditText) findViewById(R.id.editTextTextPersonName6);
        weight=(EditText)findViewById(R.id.editTextTextPersonName9);
        button_submit= (Button)findViewById(R.id.button3);
        button_submit.setOnClickListener(new View.OnClickListener() { // also firebase register here
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });




    }

    private void registerUser() {
        String email1 = email.getText().toString().trim();
        String password1= password.getText().toString().trim();
        String name1= name.getText().toString().trim();
        String pet_type1 = pet_type.getText().toString().trim();
        String weight1= weight.getText().toString().trim();

        //Weights weights= new Weights(x,weight1);
        //ListOfWeights[x]=weight1;
        //int i=0;
        //while(i<ListOfWeights.length){
         //arrayList.add(weights) ;
         //i++;
        //}



        if (email1.isEmpty()){
            email.setError("Name is required!");
            email.requestFocus();
            return;

        }

        if (password1.isEmpty()){
            password.setError("Name is required!");
            password.requestFocus();
            return;

        }

        if (name1.isEmpty()){
            name.setError("Name is required!");
            name.requestFocus();
            return;

        }

        if (pet_type1.isEmpty()){
            pet_type.setError("Name is required!");
            pet_type.requestFocus();
            return;

        }


        if (weight1.isEmpty()){
            weight.setError("Weight is required!");
            weight.requestFocus();
            return;

        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("please enter a valid email");
            email.requestFocus();
            return;
        }

        if (password1.length()<6){

            password.setError("The password should be larger than 6");
            password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(name1,email1,pet_type1,weight1);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                         openActivity1();
                                                }


                                    else{
                                        Toast.makeText(MainActivity2.this, "Failed", Toast.LENGTH_LONG);
                                    }

                                }
                            });
                           // databaseReference.child("Weights").child(ListOfWeights[x]);

                        }
                        else
                        {
                            Toast.makeText(MainActivity2.this, "Failed", Toast.LENGTH_LONG);
                        }


                    }
                });



    }



    private void openActivity1() {
        Intent intent= new Intent(this, MainActivity.class );
        startActivity(intent);
    }


}