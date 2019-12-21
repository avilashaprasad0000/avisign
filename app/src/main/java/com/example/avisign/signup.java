package com.example.avisign;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    EditText et_Name, et_EmailAddress, et_PhoneNumber, et_Address, et_password;
    RadioGroup radioGroup;
    RadioButton rb;
    String email, password, type, phone_number, address, name;
    Button createaccount;
    ProgressBar pd;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CollectionReference dbDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();
        et_Name = findViewById(R.id.editText_Name);
        et_password = findViewById(R.id.editText_password);
        et_EmailAddress = findViewById(R.id.editText_email);
        et_PhoneNumber = findViewById(R.id.editText_PhoneNumber);
        et_Address = findViewById(R.id.editText_Address);
        createaccount = findViewById(R.id.button_createAccount);
        pd = findViewById(R.id.pbar);
        //radioGroup=findViewById(R.id.radioGroup);
        //rb=findViewById(radioGroup.getCheckedRadioButtonId());
        dbDetails = db.collection("User Details");

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = et_PhoneNumber.getText().toString().trim();
                address = et_Address.getText().toString().trim();
                name = et_Name.getText().toString().trim();
                //type=rb.getText().toString();
                email = et_EmailAddress.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signup.this, " Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signup.this, " Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    User_Details user_details = new User_Details(name, address, email, phone_number);
                                    //db.collection("User_Details").child(FirebaseAuth.getInstance().getCurrentUser.getUid().setValue(user_details).addOnSu);
                                    dbDetails.add(user_details).addOnSuccessListener(signup.this,new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            Toast.makeText(signup.this, "Details Added Successfully ", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    Toast.makeText(signup.this, "Registration is successful ! ", Toast.LENGTH_SHORT).show();
                                    Intent avi = new Intent(signup.this, profile.class);
                                    avi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(avi);

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(signup.this, "You are already registered", Toast.LENGTH_SHORT).show();

                                    } else {
                                        task.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }

                                }
                            }
                        });
            }
        });
    }
}
