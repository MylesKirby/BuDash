package uk.co.myleskirby.UniProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    public String name;
    public String gender;
    public String system;
    public String familiar;
    public String passUsed;
    public String passType;
    public String age;
    public String education;
    public String email;
    public String easyAttempts;
    public String easyCompleted ;
    public String mediumAttempts;
    public String mediumCompleted;
    public String hardAttempts;
    public String hardCompleted;
    public String instructionOrder;
    User user = new User();

    // 1 = Circle, 2 = Square, 3 = Dash, 4 - triangle, 5 - cross
    int shape1count = 0;
    int shape2count = 0;
    int shape3count = 0;
    int shape4count = 0;
    int shape5count = 0;
    Boolean entering = true;
    Boolean guessed = false;
    Boolean contact = false;
    String Pass = "";
    String Guess = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("Users");

    int canSee0[] = {1,3,4,5,7};//Declaring nodes and setting the other nodes that they can see, this is important to make it so you can access each other node in a straight line from the current one
    Node node0 = new Node(0, canSee0);
    int canSee1[] = {0,2,3,4,5,6,8};
    Node node1 = new Node(1,canSee1);
    int canSee2[] = {1,3,4,5,7};
    Node node2 = new Node(2,canSee2);
    int canSee3[] = {0,1,2,4,6,7,8};
    Node node3 = new Node(3,canSee3);
    int canSee4[] = {0,1,2,3,5,6,7,8};
    Node node4 = new Node(4,canSee4);
    int canSee5[] = {0,1,2,4,6,7,8};
    Node node5 = new Node(5,canSee5);
    int canSee6[] = {1,3,4,5,7};
    Node node6 = new Node(6,canSee6);
    int canSee7[] = {0,2,3,4,5,6,8};
    Node node7 = new Node(7,canSee7);
    int canSee8[] = {1,3,4,5,7};
    Node node8 = new Node(8,canSee8);
    Node[] Nodes = {node0, node1, node2, node3, node4, node5, node6, node7, node8};
    Boolean over0 = false;
    Boolean over1 = false;
    Boolean over2 = false;
    Boolean over3 = false;
    Boolean over4 = false;
    Boolean over5 = false;
    Boolean over6 = false;
    Boolean over7 = false;
    Boolean over8 = false;

    Boolean noShape1 = true;
    Boolean noShape2 = true;
    Boolean noShape3 = true;
    Boolean noShape4 = true;
    Boolean noShape5 = true;
    int shapes0Count = 0;




    public void resetShapeCount(){
        shape1count = 0;
        shape2count = 0;
        shape3count = 0;
        shape4count = 0;
        shape5count = 0;
        noShape1 = true;
        noShape2 = true;
        noShape3 = true;
        noShape4 = true;
        noShape5 = true;
        shapes0Count = 0;

    }
    public void num2Shape(int pos, int shape){
        TextView Pos0 = findViewById(R.id.Pos0);
        TextView Pos1 = findViewById(R.id.Pos1);
        TextView Pos2 = findViewById(R.id.Pos2);
        TextView Pos3 = findViewById(R.id.Pos3);
        TextView Pos4 = findViewById(R.id.Pos4);
        TextView Pos5 = findViewById(R.id.Pos5);
        TextView Pos6 = findViewById(R.id.Pos6);
        TextView Pos7 = findViewById(R.id.Pos7);
        TextView Pos8 = findViewById(R.id.Pos8);
        TextView[] buttons = {Pos0, Pos1, Pos2, Pos3, Pos4, Pos5, Pos6, Pos7, Pos8};
        // 1 = Circle, 2 = Square, 3 = Dash, 4 - triangle, 5 - cross
        switch(shape){
            case 1:
                buttons[pos].setBackgroundResource(R.drawable.circle);
                break;
            case 2:
                buttons[pos].setBackgroundResource(R.drawable.square2);
                break;
            case 3:
                buttons[pos].setBackgroundResource(R.drawable.dash2);
                break;
            case 4:
                buttons[pos].setBackgroundResource(R.drawable.tri2);
                break;
            case 5:
                buttons[pos].setBackgroundResource(R.drawable.cross2);
                break;
        }


    }
    public void randomiseShapes(int position){
        if (guessed == false){
            TextView Pos0 = findViewById(R.id.Pos0);
            TextView Pos1 = findViewById(R.id.Pos1);
            TextView Pos2 = findViewById(R.id.Pos2);
            TextView Pos3 = findViewById(R.id.Pos3);
            TextView Pos4 = findViewById(R.id.Pos4);
            TextView Pos5 = findViewById(R.id.Pos5);
            TextView Pos6 = findViewById(R.id.Pos6);
            TextView Pos7 = findViewById(R.id.Pos7);
            TextView Pos8 = findViewById(R.id.Pos8);
            TextView help = findViewById(R.id.help);

            TextView pass = findViewById(R.id.Password);
            TextView reset = findViewById(R.id.Clear);
            Button confirm = (Button)findViewById(R.id.confirm);

            TextView[] buttons = {Pos0, Pos1, Pos2, Pos3, Pos4, Pos5, Pos6, Pos7, Pos8};



            resetShapeCount();
            if (position != 1000){//the value to randomise shapes without changing the stored password
                if (entering){//initially setting the password
                    Pass = Pass+=Integer.toString(Nodes[position].shape);
                    pass.setText(Pass);
                    reset.setVisibility(View.VISIBLE);

                }else{//entering the confirmed password
                    Guess = Guess+=Integer.toString(Nodes[position].shape);
                    pass.setText(Guess);
                    reset.setVisibility(View.VISIBLE);
                    help.setText("Please re-enter passcode");
                    if (Guess.equals(Pass)){
                        help.setText("Correct!");
                        confirm.setText("FINISH");
                        confirm.setVisibility(View.VISIBLE);
                        guessed = true;

                    }
                }

            }

            while (shape1count == 0 || shape2count == 0 || shape3count == 0|| shape4count == 0 || shape5count==0){
                String num="";
                resetShapeCount();
                for (int i=0; i<Nodes.length ;i++){
                    if (i != position){
                        Nodes[i].RandomiseShape();
                        num = Integer.toString(Nodes[i].shape);
                        num2Shape(i,Nodes[i].shape);
                        if (position != 1000){//so a specific node is selected
                            for (int x=0; x < Nodes[position].canSee.length; x++){
                                if (Nodes[i].getPos() == (Nodes[position].canSee[x])){
                                    switch (Nodes[i].shape){
                                        case 1:
                                            shape1count++;
                                            break;
                                        case 2:
                                            shape2count++;
                                            break;
                                        case 3:
                                            shape3count++;
                                            break;
                                        case 4:
                                            shape4count++;
                                            break;
                                        case 5:
                                            shape5count++;
                                            break;
                                    }
                                }
                            }
                        }else{
                            switch (Nodes[i].shape){
                                case 1:
                                    shape1count++;
                                    break;
                                case 2:
                                    shape2count++;
                                    break;
                                case 3:
                                    shape3count++;
                                    break;
                                case 4:
                                    shape4count++;
                                    break;
                                case 5:
                                    shape5count++;
                                    break;
                            }
                        }


                    }



                }

            }

        }


    }
    public void clicked8(){
        randomiseShapes(8);
    }
    public void clicked7(){randomiseShapes(7);}
    public void clicked6(){randomiseShapes(6);}
    public void clicked5(){randomiseShapes(5);}
    public void clicked4(){randomiseShapes(4);}
    public void clicked3(){randomiseShapes(3);}
    public void clicked2(){randomiseShapes(2);}
    public void clicked1(){randomiseShapes(1);}
    public void clicked0(){randomiseShapes(0);}
    public void clearPass(View view){
        TextView pass = findViewById(R.id.Password);
        TextView help = findViewById(R.id.help);
        Button reset = findViewById(R.id.Clear);
        Button confirmButton = findViewById(R.id.confirm);
        if (guessed == false){
            reset.setVisibility(View.INVISIBLE);
            if (entering == true){
                Pass = "";
                pass.setText(Pass);
                help.setText("Please enter a passcode");
            }else{
                Guess = "";
                pass.setText(Guess);
            }

        }else{
            guessed = false;
            entering = true;
            pass.setText("");
            help.setText("Please enter a passcode");
            confirmButton.setText("CONFIRM");
            Guess = "";
            Pass = "";
            reset.setText("CLEAR");
            reset.setVisibility(View.INVISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
        }

    }
    public void confirm(View view) {
        TextView pass = findViewById(R.id.Password);
        Button confirmButton = findViewById(R.id.confirm);
        TextView help = findViewById(R.id.help);
        System.out.println("shapes0Count:"+shapes0Count);
        shapes0Count = 0;
        for(int i = 0; i < Pass.length(); i++){
            switch(Pass.charAt(i)){
                case'1':
                    noShape1 = false;
                    break;
                case'2':
                    noShape2 = false;
                    break;
                case'3':
                    noShape3 = false;
                    break;
                case'4':
                    noShape4 = false;
                    break;
                case'5':
                    noShape5 = false;
                    break;
            }



        }
        if (noShape1 == true) {
            shapes0Count++;
        }
        if (noShape2 == true) {
            shapes0Count++;
        }
        if (noShape3 == true) {
            shapes0Count++;
        }
        if (noShape4 == true) {
            shapes0Count++;
        }
        if (noShape5 == true) {
            shapes0Count++;
        }
        System.out.println("shapes0Count post adding:"+shapes0Count);
        if (guessed == false) {
            if (Pass.length() < 4) {
                pass.setText("Password is too short");
            } else if (Pass.length() > 9) {
                pass.setText("Password is too long");
            } else if (shapes0Count == 4) {
                System.out.println("too many 0s");
                pass.setText("Please use more than 1 shape");
            } else {
                randomiseShapes(1000);
                entering = false;
                help.setText("Please re-enter passcode");

                pass.setText("");

                confirmButton.setVisibility(View.INVISIBLE);
            }

        } else {
            user.setFavPasscode(Pass);


            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "User has been successfully updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to create user!", Toast.LENGTH_LONG).show();
                    }
                }
            });


            startActivity(new Intent(MainActivity.this, UserProfile.class));


        }
    }
    public void backToInstructions(View view){
        //used to go back to instructions but now goes back to main page, instructions is no longer the page before this one
        guessed= false;
        entering = true;
        Pass = "";
        Guess = "";
        startActivity(new Intent(MainActivity.this, UserProfile.class));


    }
    public void resetOvers(){ //so another node can be selected and another number can be added
        over0 = false;
        over1 = false;
        over2 = false;
        over3 = false;
        over4 = false;
        over5 = false;
        over6 = false;
        over7 = false;
        over8 = false;
    }

    public boolean onTouchEvent(MotionEvent e){
        float x = e.getX();
        float y = e.getY();

        TextView Pos0 = findViewById(R.id.Pos0);
        TextView Pos1 = findViewById(R.id.Pos1);
        TextView Pos2 = findViewById(R.id.Pos2);
        TextView Pos3 = findViewById(R.id.Pos3);
        TextView Pos4 = findViewById(R.id.Pos4);
        TextView Pos5 = findViewById(R.id.Pos5);
        TextView Pos6 = findViewById(R.id.Pos6);
        TextView Pos7 = findViewById(R.id.Pos7);
        TextView Pos8 = findViewById(R.id.Pos8);
        TextView reset = findViewById(R.id.Clear);

        TextView pass = findViewById(R.id.Password);
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:

                //code taken from randomiseShapes(), if  the finger is lifted off the screen and placed back on, it should be interpreted as a new attempt at the password
                if (entering){//initially setting the password
                    Pass = "";
                    pass.setText(Pass);
                }else {//entering the confirmed password
                    Guess = "";
                    pass.setText(Guess);
                }
                contact = true;
                break;
            case MotionEvent.ACTION_UP:
                contact=false;

                over0 = false;
                over1 = false;
                over2 = false;
                over3 = false;
                over4 = false;
                over5 = false;
                over6 = false;
                over7 = false;
                over8 = false;
                if (guessed == true){
                    pass.setText("CORRECT");
                    reset.setText("TRY AGAIN");
                }

                break;
        }
        if (e.getAction() != MotionEvent.ACTION_UP){
            int[] location = new int[2];
            Pos0.getLocationOnScreen(location);
            //min and max x and y co-ords for pressing each 'button'
            if (y >= location[1] && y <= location[1]+Pos0.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over0) {
                        clicked0();
                        resetOvers();
                        over0 = true;
                    }
                }
            }
            Pos1.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over1) {
                        clicked1();
                        resetOvers();
                        over1 = true;
                    }
                }
            }
            Pos2.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over2) {
                        clicked2();
                        resetOvers();
                        over2 = true;
                    }
                }
            }

            Pos3.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over3) {
                        clicked3();
                        resetOvers();
                        over3 = true;
                    }
                }
            }
            Pos4.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over4) {
                        clicked4();
                        resetOvers();
                        over4 = true;
                    }
                }
            }
            Pos5.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over5){
                        clicked5();
                        resetOvers();
                        over5 = true;
                    }
                }
            }
            Pos6.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over6) {
                        clicked6();
                        resetOvers();
                        over6 = true;
                    }
                }
            }
            Pos7.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over7) {
                        clicked7();
                        resetOvers();
                        over7 = true;
                    }
                }
            }
            Pos8.getLocationOnScreen(location);
            if (y >= location[1] && y <= location[1]+Pos1.getHeight()) {
                if (x >= location[0] && x <= location[0] + Pos0.getWidth()) {
                    if (!over8){
                        clicked8();
                        resetOvers();
                        over8 = true;
                    }
                }
            }
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Pos0 = findViewById(R.id.Pos0);
        TextView Pos1 = findViewById(R.id.Pos1);
        TextView Pos2 = findViewById(R.id.Pos2);
        TextView Pos3 = findViewById(R.id.Pos3);
        TextView Pos4 = findViewById(R.id.Pos4);
        TextView Pos5 = findViewById(R.id.Pos5);
        TextView Pos6 = findViewById(R.id.Pos6);
        TextView Pos7 = findViewById(R.id.Pos7);
        TextView Pos8 = findViewById(R.id.Pos8);
        Button reset = findViewById(R.id.Clear);
        reset.setVisibility(View.INVISIBLE);
        TextView[] buttons = {Pos0, Pos1, Pos2, Pos3, Pos4, Pos5, Pos6, Pos7, Pos8};


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
                instructionOrder=String.valueOf(snapshot.child("instructionOrder").getValue());

                easyAttempts= String.valueOf(snapshot.child("easyAttempts").getValue());
                easyCompleted= String.valueOf(snapshot.child("easyCompleted").getValue());
                mediumAttempts= String.valueOf(snapshot.child("mediumAttempts").getValue());
                mediumCompleted= String.valueOf(snapshot.child("mediumCompleted").getValue());
                hardAttempts= String.valueOf(snapshot.child("hardAttempts").getValue());
                hardCompleted= String.valueOf(snapshot.child("hardCompleted").getValue());
                user.setName(name);
                user.setGender(gender);
                user.setSystem(system);
                user.setFamiliar(familiar);
                user.setPassUsed(passUsed);
                user.setAge(age);
                user.setEducation(education);
                user.setPassType(passType);
                user.setEmail(email);
                user.setInstructionOrder(instructionOrder);

                user.setEasyAttempts(easyAttempts);
                user.setEasyCompleted(easyCompleted);
                user.setMediumAttempts(mediumAttempts);
                user.setMediumCompleted(mediumCompleted);
                user.setHardAttempts(hardAttempts);
                user.setHardCompleted(hardCompleted);

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });



        while (shape1count == 0 || shape2count == 0 || shape3count == 0|| shape4count == 0 || shape5count==0){
            resetShapeCount();
            for (int i=0; i<Nodes.length ;i++){
                Nodes[i].RandomiseShape();
                num2Shape(i,Nodes[i].shape);
                switch (Nodes[i].shape){
                    case 1:
                        shape1count++;
                        break;
                    case 2:
                        shape2count++;
                        break;
                    case 3:
                        shape3count++;
                        break;
                    case 4:
                        shape4count++;
                        break;
                    case 5:
                        shape5count++;
                        break;
                }
            }
        }
    }



}