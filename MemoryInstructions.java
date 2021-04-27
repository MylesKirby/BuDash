package uk.co.myleskirby.UniProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemoryInstructions extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_instructions);
        Button backButton = findViewById(R.id.backButton);
        Button toGame = findViewById(R.id.toGame);
        backButton.setOnClickListener(this);
        toGame.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                startActivity(new Intent(this, UserProfile.class));
                break;
            case R.id.toGame:
                startActivity(new Intent(this, MemoryGame.class));
                break;

        }
    }


}