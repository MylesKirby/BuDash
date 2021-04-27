package uk.co.myleskirby.UniProject;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static uk.co.myleskirby.UniProject.R.drawable.circle;
import static uk.co.myleskirby.UniProject.R.drawable.cross;
import static uk.co.myleskirby.UniProject.R.drawable.dash;
import static uk.co.myleskirby.UniProject.R.drawable.square;
import static uk.co.myleskirby.UniProject.R.drawable.tri;
import static uk.co.myleskirby.UniProject.Demographics.user;

public class PrizeEnter extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    String order = Demographics.user.getFavPasscode();
    String obfusEmail;
    boolean toClose = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_enter);
        TextView Pos1 = findViewById(R.id.pos1);
        TextView Pos2 = findViewById(R.id.pos2);
        TextView Pos3 = findViewById(R.id.pos3);
        TextView Pos4 = findViewById(R.id.pos4);
        TextView Pos5 = findViewById(R.id.pos5);
        TextView Pos6 = findViewById(R.id.pos6);
        TextView Pos7 = findViewById(R.id.pos7);
        TextView Pos8 = findViewById(R.id.pos8);
        TextView Pos9 = findViewById(R.id.pos9);

        TextView positions[] = {Pos1,Pos2,Pos3,Pos4,Pos5,Pos6, Pos7, Pos8, Pos9};
        // 1 = Circle, 2 = Square, 3 = Dash, 4 - triangle, 5 - cross

        for (int i = 0; i < order.length();i++){
            switch (Integer.parseInt(String.valueOf(Character.getNumericValue(order.charAt(i))))){
                case 1:
                    positions[i].setBackgroundResource(circle);
                    break;
                case 2:
                    positions[i].setBackgroundResource(square);
                    break;
                case 3:
                    positions[i].setBackgroundResource(dash);
                    break;
                case 4:
                    positions[i].setBackgroundResource(tri);
                    break;
                case 5:
                    positions[i].setBackgroundResource(cross);
                    break;

            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view){
        if (toClose == true){
            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            if(am != null) {
                List<ActivityManager.AppTask> tasks = am.getAppTasks();
                if (tasks != null) {
                    tasks.get(0).finishAndRemoveTask();
                }
            }
        }else{
            EditText emailBox = findViewById(R.id.emailEnter);
            Button confirm = findViewById(R.id.button2);
            TextView details = findViewById(R.id.details);
            user.setEmail(emailBox.getText().toString());
            obfusEmail = user.getEmail();
            char[] myNameChars = obfusEmail.toCharArray();
            for (int i = 0;i<obfusEmail.length(); i++){
                if (i % 2 == 1){
                    myNameChars[i] = '*';
                    obfusEmail = String.valueOf(myNameChars);
                }
            }
            database = FirebaseDatabase.getInstance();
            reference = database.getReference().child("withEmail");
            reference.push().setValue(user);
            emailBox.setVisibility(View.INVISIBLE);
            details.setText("Thank you! You have been entered into the prizedraw! If you win you will get an email sent to "+obfusEmail+".");
            toClose = true;
            confirm.setText("EXIT");
        }

    }


}
