package uk.co.myleskirby.UniProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button login;
    private EditText emailEnter;
    private EditText passwordEnter;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        emailEnter = (EditText) findViewById(R.id.emailEnter);
        passwordEnter = (EditText) findViewById(R.id.passwordEnter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                startActivity(new Intent(this,consentPage.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email, password;
        email = emailEnter.getText().toString().trim();
        password = passwordEnter.getText().toString().trim();
        if (email.isEmpty()){
            emailEnter.setError("Please enter an email.");
            emailEnter.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEnter.setError("Please enter a password.");
            passwordEnter.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEnter.setError("Please enter a valid email");
            emailEnter.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //go to profile
                    startActivity(new Intent(Login.this, UserProfile.class));
                }else{
                    Toast.makeText(Login.this,"Invalid username / password.",Toast.LENGTH_LONG).show();
                }

            }
        });
        progressBar.setVisibility(View.GONE);
    }
}