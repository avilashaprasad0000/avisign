package com.example.avisign;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login extends AppCompatActivity {
    Button b1, b2;
    EditText et1, et2;
    String email, password;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(login.this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent= new Intent(login.this,signup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et1.getText().toString().trim();
                password = et2.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, " Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, " Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Intent avi = new Intent(login.this, profile.class);
                            avi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(avi);
                            //FirebaseUser user = firebaseAuth.getCurrentUser();

                        } else {
                            Toast.makeText(login.this, "Invalid Credentials, please try again ! ", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                    }
                });
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, profile.class));
        }
    }
}
