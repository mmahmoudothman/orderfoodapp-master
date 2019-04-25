package com.atakanakar.orderfoodapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.atakanakar.orderfoodapplication.Common.Common;
import com.atakanakar.orderfoodapplication.Model.User;
import com.atakanakar.orderfoodapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    private MaterialEditText editPhone;
    private MaterialEditText editPass;

    Button btnSignIn;

    FirebaseDatabase mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        editPass  = (MaterialEditText)  findViewById(R.id.editTextPass);
        editPhone = (MaterialEditText)  findViewById(R.id.editTextPhone);

        btnSignIn = findViewById(R.id.buttonSignIn2);

        //initial firebase
        mAuth = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = mAuth.getReference("user");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please wait... ");
                mDialog.show();



                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get users information
                        String phoneNum = editPhone.getText().toString();

                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User users = dataSnapshot.child(phoneNum).getValue(User.class);
                            if (users.getPassword().equals(editPass.getText().toString())) {
                                showToast("Sign In successfully");

                                Intent homeIntent = new Intent(SignInActivity.this,HomeActivity.class);
                                Common.currentUser = users;
                                startActivity(homeIntent);
                                finish();

                            } else
                                showToast("Wrong password");

                        } else {
                            mDialog.dismiss();
                            showToast("User not exists in Database");

                        }
                     }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Log.d("error",String.valueOf(databaseError.toException()));
                    }
                });

            }
        });

    }

    public void showToast(String mesaj) {

        Toast.makeText(getApplicationContext(),mesaj,Toast.LENGTH_SHORT).show();
    }
}
