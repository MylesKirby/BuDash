package uk.co.myleskirby.UniProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {


    private Button logOutButton,toInstructions, toMemGame, toSetPassword;
    private TextView greetings;
    DatabaseReference reff;
    String name;
    User user;
    ArrayList<String> theList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        logOutButton = (Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(this);
        toInstructions = (Button) findViewById(R.id.toInstructions);
        toInstructions.setOnClickListener(this);
        toInstructions.setBackgroundResource(R.drawable.instructions_smaller);
        toSetPassword = (Button) findViewById(R.id.toPasswordSet);
        toSetPassword.setOnClickListener(this);
        toSetPassword.setBackgroundResource(R.drawable.set_passcode);
        toMemGame = (Button) findViewById(R.id.toMemGame);
        toMemGame.setOnClickListener(this);
        toMemGame.setBackgroundResource(R.drawable.memory_game);


        greetings = (TextView)findViewById(R.id.greetings);
        reff = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = String.valueOf(snapshot.child("name").getValue());
                System.out.println(name);
                greetings.setText("Hello "+name+"!");
                theList.clear();
                for (DataSnapshot ss : snapshot.getChildren()){
                    theList.add(ss.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOutButton:
                Intent intent = new Intent(UserProfile.this, Login.class);//https://stackoverflow.com/questions/7075349/android-clear-activity-stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // this way i cant hit back to log back in
                startActivity(intent);
                break;
            case R.id.toInstructions:
                startActivity(new Intent(this, Instructions.class));
                break;
            case R.id.toPasswordSet:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.toMemGame:
                startActivity(new Intent(this, MemoryInstructions.class));
                break;
        }
    }
}

