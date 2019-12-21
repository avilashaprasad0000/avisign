package com.example.avisign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button b1,b2;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        pd = new ProgressDialog(MainActivity.this);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(MainActivity.this,signup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=et1.getText().toString().trim();
                password=et2.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent log = new Intent(MainActivity.this,login.class);
                            log.putExtra("email",email);
                            startActivity(log);
                           // FirebaseAuth.getInstance().signOut();
                            //FirebaseUser user = firebaseAuth.getCurrentUser();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials, please try again ! ", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                    }
                });

            }
        });
    }

}

