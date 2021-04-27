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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //https://www.youtube.com/watch?v=Z-RE1QuUWPg

    private Button next;
    private Button back;
    private EditText nameEnter, emailEnter, passwordEnter, passwordReEnter;
    private ProgressBar progressBar;
    private String email,name = "", password, checkpass;
    private FirebaseAuth mAuth;
    DatabaseReference loginReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        mAuth = FirebaseAuth.getInstance();

        back = (Button) findViewById(R.id.backButton);
        next = (Button) findViewById(R.id.nextButton);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        nameEnter = (EditText) findViewById(R.id.nameEnter);
        emailEnter = (EditText) findViewById(R.id.emailEnter);
        passwordEnter = (EditText) findViewById(R.id.passwordEnter);
        passwordReEnter = (EditText) findViewById(R.id.passwordReEnter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nextButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        name = nameEnter.getText().toString().trim();
        email = emailEnter.getText().toString().trim();
        password = passwordEnter.getText().toString().trim();
        checkpass = passwordReEnter.getText().toString().trim();
        if (email.isEmpty()){
            emailEnter.setError("Please enter an email.");
            emailEnter.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEnter.setError("Please enter a password.");
            passwordEnter.requestFocus();
            return;
        }if (checkpass.isEmpty()){
            passwordReEnter.setError("Please reenter your password.");
            passwordReEnter.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEnter.setError("Please enter a valid email");
            emailEnter.requestFocus();
            return;
        }
        if (password.length() < 8){
            passwordEnter.setError("Please enter a password with at least 8 characters.");
            passwordEnter.requestFocus();
            return;
        }
        if (!checkpass.equals(password)){
            passwordReEnter.setError("Please ensure the passwords match.");
            passwordReEnter.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    User user = new User(name, email);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"User has been successfully created!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(Register.this, Demographics.class));
                            }
                            else{
                                Toast.makeText(Register.this,"Failed to create user! Error 1",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"Failed to create user! Error 2",Toast.LENGTH_LONG).show();
                    Toast.makeText(Register.this,"Password, email:"+password+email,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}