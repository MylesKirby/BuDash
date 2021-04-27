package uk.co.myleskirby.UniProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class consentPage extends AppCompatActivity {
    int duration = Toast.LENGTH_SHORT;

    public void onCheckboxClicked(View view){

    }



    public void giveConsent(View view){
        CheckBox confirmConsent = findViewById(R.id.confirmConsent);

        if(confirmConsent.isChecked()){
            CharSequence text = "Consent Confirmed";
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
            startActivity(new Intent(this, Register.class));
        }else{
            CharSequence text = "Please give consent to continue";
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_page);
    }
}