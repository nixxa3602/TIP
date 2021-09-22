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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {
    private Button button_register;
    private Button button_tohome;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;

   private String userID;
    //private String x;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_register= (Button)findViewById(R.id.button2);
        button_tohome=(Button)findViewById(R.id.button);
        email= findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.editTextTextPersonName3);
        mAuth= FirebaseAuth.getInstance();




        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity2();
            }




        });
        button_tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();


            }
        });
    }


    private void userLogin() {
        String email1= email.getText().toString().trim();
        String password1= password.getText().toString().trim();

        if (email1.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;

        }
        if (password1.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
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


       mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){

                    //FirebaseUser user;
                    //DatabaseReference reference;

                   //String userID;

                   user=FirebaseAuth.getInstance().getCurrentUser();
                  reference= FirebaseDatabase.getInstance().getReference("Users");
                   userID=user.getUid();



                   reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           User userProfile= snapshot.getValue(User.class);
                           String x= userProfile.pet_type;

                           String str1="dog";
                           String str2="cat";

                           if (x.equalsIgnoreCase(str1)){
                               openActivity3();
                           }
                           else if (x.equalsIgnoreCase(str2)){
                               openActivity4();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });




               }
               else
                   Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG);

           }
       });

    }

    private void openActivity4() {
        Intent intent= new Intent(this, MainActivity4.class );
        startActivity(intent);
    }

    private void openActivity3() {
        Intent intent= new Intent(this, MainActivity3.class );
        startActivity(intent);
    }


    private void openActivity2() {
        Intent intent= new Intent(this, MainActivity2.class );
        startActivity(intent);
    }


}