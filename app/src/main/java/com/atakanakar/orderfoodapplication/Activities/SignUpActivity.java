package com.atakanakar.orderfoodapplication.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.atakanakar.orderfoodapplication.Model.User;
import com.atakanakar.orderfoodapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    private MaterialEditText editTextName;
    private MaterialEditText editTextPassword;
    private MaterialEditText editTextPhoneUp;

    private Button buttonSignUp;

    private FirebaseDatabase mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        editTextName = findViewById(R.id.editTexName);
        editTextPassword = findViewById(R.id.editTextPass);
        editTextPhoneUp  = findViewById(R.id.editTextPhone);

        buttonSignUp = findViewById(R.id.buttonSignUp2);

        mAuth = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = mAuth.getReference("user");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Please wait... ");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(editTextPhoneUp.getText().toString()).exists()){
                            mDialog.dismiss();
                            showToast("Phone number already register");
                        } else {
                            mDialog.dismiss();
                            User user = new User(editTextName.getText().toString(),editTextPassword.getText().toString());
                            table_user.child(editTextPhoneUp.getText().toString()).setValue(user);
                            showToast("Sign Up successfully");
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    private void showToast(String mesaj) {

        Toast.makeText(getApplicationContext(),mesaj,Toast.LENGTH_SHORT).show();

    }
}
