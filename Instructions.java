package uk.co.myleskirby.UniProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Instructions extends AppCompatActivity {
    DatabaseReference reff;
    String name;
    String gender;
    String system;
    String familiar;
    String passUsed;
    String passType;
    String age;
    String education;
    String email;
    String easyAttempts;
    String easyCompleted ;
    String mediumAttempts;
    String mediumCompleted;
    String hardAttempts;
    String hardCompleted;
    String favPasscode;
    User user = new User();


    int shape1;
    int shape2;
    int shape3;
    int shape4;
    int shape5;
    String order = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        reff = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name= String.valueOf(snapshot.child("name").getValue());
                gender= String.valueOf(snapshot.child("gender").getValue());
                system= String.valueOf(snapshot.child("system").getValue());
                familiar= String.valueOf(snapshot.child("familiar").getValue());
                passUsed= String.valueOf(snapshot.child("passUsed").getValue());
                age= String.valueOf(snapshot.child("age").getValue());
                education= String.valueOf(snapshot.child("education").getValue());
                email= String.valueOf(snapshot.child("email").getValue());
                passType= String.valueOf(snapshot.child("passType").getValue());

                easyAttempts= String.valueOf(snapshot.child("easyAttempts").getValue());
                easyCompleted= String.valueOf(snapshot.child("easyCompleted").getValue());
                mediumAttempts= String.valueOf(snapshot.child("mediumAttempts").getValue());
                mediumCompleted= String.valueOf(snapshot.child("mediumCompleted").getValue());
                hardAttempts= String.valueOf(snapshot.child("hardAttempts").getValue());
                hardCompleted= String.valueOf(snapshot.child("hardCompleted").getValue());
                favPasscode = String.valueOf(snapshot.child("favPasscode").getValue());
                user.setName(name);
                user.setGender(gender);
                user.setSystem(system);
                user.setFamiliar(familiar);
                user.setPassUsed(passUsed);
                user.setAge(age);
                user.setEducation(education);
                user.setPassType(passType);
                user.setEmail(email);
                user.setFavPasscode(favPasscode);

                user.setEasyAttempts(easyAttempts);
                user.setEasyCompleted(easyCompleted);
                user.setMediumAttempts(mediumAttempts);
                user.setMediumCompleted(mediumCompleted);
                user.setHardAttempts(hardAttempts);
                user.setHardCompleted(hardCompleted);
                System.out.println("YOOOOOO THIS IS RUNNING" + user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


        // 1 = Circle, 2 = Square, 3 = Dash, 4 - triangle, 5 - cross
        boolean taken[] = {false, false, false, false, false};
        int shapes[] = {shape1,shape2,shape3,shape4,shape5};

        ImageView Pos1 = findViewById(R.id.shape1);
        ImageView Pos2 = findViewById(R.id.shape2);
        ImageView Pos3 = findViewById(R.id.shape3);
        ImageView Pos4 = findViewById(R.id.shape4);
        ImageView Pos5 = findViewById(R.id.shape5);
        ImageView positions[] = {Pos1,Pos2,Pos3,Pos4,Pos5};
        while(taken[0] == false || taken[1] == false || taken[2] == false ||taken[3] == false || taken[4] == false){
            for (int i = 0; i <5; i++){
                taken[i] = false;
                shapes[i]  = randomise();
            }

            for (int i = 0; i <5; i++){
                switch (shapes[i]){
                    case 1:
                        taken[0] = true;
                        break;
                    case 2:
                        taken[1] = true;
                        break;
                    case 3:
                        taken[2] = true;
                        break;
                    case 4:
                        taken[3] = true;
                        break;
                    case 5:
                        taken[4] = true;
                        break;
                }
            }
        }
        for (int i = 0; i <5; i++){
            switch(shapes[i]){
                case 1:
                    positions[i].setBackgroundResource(R.drawable.circle);
                    order = order+Integer.toString(shapes[i]);
                    break;
                case 2:
                    positions[i].setBackgroundResource(R.drawable.square2);
                    order = order+Integer.toString(shapes[i]);
                    break;
                case 3:
                    positions[i].setBackgroundResource(R.drawable.dash2);
                    order = order+Integer.toString(shapes[i]);
                    break;
                case 4:
                    positions[i].setBackgroundResource(R.drawable.tri2);
                    order = order+Integer.toString(shapes[i]);
                    break;
                case 5:
                    positions[i].setBackgroundResource(R.drawable.cross2);
                    order = order+Integer.toString(shapes[i]);
                    break;
            }
        }

        user.setInstructionOrder(order);

    }

    public void toMainActivity(View view){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                }
            }
        });
        startActivity(new Intent(Instructions.this, UserProfile.class));

    }
    public int randomise(){
        double min =1;
        double max =5;
        double x = (int)(Math.random()*((max-min)+1))+min;
        return (int)x;
    }
}