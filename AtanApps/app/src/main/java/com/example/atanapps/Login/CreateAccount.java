package com.example.atanapps.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atanapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    EditText mname, memail, mpassword;
    TextView backtologin;
    CardView createbtn;
    FirebaseAuth fauth;
    ProgressBar progressBar;
    DatabaseReference dbuser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_login_account);
        mname=(EditText)findViewById(R.id.createname);
        mpassword=(EditText)findViewById(R.id.createpassword);
        memail=(EditText)findViewById(R.id.createemail);
        backtologin=(TextView)findViewById(R.id.createback);
        createbtn=(CardView)findViewById(R.id.createbtn);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);

        fauth=FirebaseAuth.getInstance();

        progressBar.setVisibility(View.INVISIBLE);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String username=mname.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(password.length() < 6){
                    mpassword.setError("Password must greater 6 character");
                    progressBar.setVisibility(View.INVISIBLE);
                }

                progressBar.setVisibility(View.VISIBLE);
                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccount.this,"User Created",Toast.LENGTH_SHORT).show();
                            userId=fauth.getCurrentUser().getUid();
                            dbuser= FirebaseDatabase.getInstance().getReference("User");
                        //    UserDatabaseClass userDatabaseClass=new UserDatabaseClass(username,userno,userId,email);
                        //    dbuser.child(userId).setValue(userDatabaseClass);
                            finish();
                        }else{
                            Toast.makeText(CreateAccount.this,"Error"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
