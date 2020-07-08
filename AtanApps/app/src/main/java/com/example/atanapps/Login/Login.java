package com.example.atanapps.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atanapps.MainActivity;
import com.example.atanapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    CardView btnlogin;
    TextView createaccount;
    ProgressBar progressBar;
    EditText mpassword,memail;
    FirebaseAuth fauth;
    private long backpresstime;
    private Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mpassword=(EditText)findViewById(R.id.loginpassword);
        memail=(EditText)findViewById(R.id.loginemail);
        btnlogin=(CardView)findViewById(R.id.loginbtn);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        createaccount=(TextView)findViewById(R.id.logincreateacc);
        fauth=FirebaseAuth.getInstance();

        progressBar.setVisibility(View.INVISIBLE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //login apps
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Login.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enter create account
                Intent intent = new Intent(Login.this,CreateAccount.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        if(backpresstime + 2000 > System.currentTimeMillis()){
            backtoast.cancel();
            super.onBackPressed();
            return;
        }else{
            backtoast=Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backtoast.show();
        }
        backpresstime=System.currentTimeMillis();
    }


}
