package uk.co.myleskirby.UniProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class Demographics extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String name;
    public String gender;
    public String system;
    public String familiar;
    public String passUsed;
    public String passType;
    public String age;
    public String education;
    public String email;
    public int emptyCount = 7;

    public static User user = new User();

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics);


        reff = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name= String.valueOf(snapshot.child("name").getValue());
                user.setName(name);
                gender= String.valueOf(snapshot.child("gender").getValue());
                system= String.valueOf(snapshot.child("system").getValue());
                familiar= String.valueOf(snapshot.child("familiar").getValue());
                passUsed= String.valueOf(snapshot.child("passUsed").getValue());
                age= String.valueOf(snapshot.child("age").getValue());
                education= String.valueOf(snapshot.child("education").getValue());
                email= String.valueOf(snapshot.child("email").getValue());
                user.setEmail(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        TextView passwordTypeText = findViewById(R.id.passwordTypeText);

        //Gender Select dropdown
        final Spinner genderSelect = findViewById(R.id.genderSelect);
        String[] items = new String[]{"--Please Select One--","Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        genderSelect.setAdapter(adapter);
        genderSelect.setOnItemSelectedListener(this);

        //Age Select dropdown
        final Spinner ageSelect = findViewById(R.id.ageSelect);
        String[] items5 = new String[]{"--Please Select One--","18 or Under", "19-21", "22-25","26-29","30 or Above"};
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items5);
        ageSelect.setAdapter(adapter5);
        ageSelect.setOnItemSelectedListener(this);

        //Education Select dropdown
        final Spinner educationSelect = findViewById(R.id.educationSelect);
        String[] items6 = new String[]{"--Please Select One--","No Qualifications","GCSEs", "A levels", "University Degree","Doctorate","Other"};
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items6);
        educationSelect.setAdapter(adapter6);
        educationSelect.setOnItemSelectedListener(this);

        //Phone System Select dropdown
        final Spinner systemSelect = findViewById(R.id.systemSelect);
        String[] items1 = new String[]{"--Please Select One--","Android", "IOS", "Other"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        systemSelect.setAdapter(adapter1);
        systemSelect.setOnItemSelectedListener(this);

        //Familiar Select dropdown
        final Spinner familiarSelect = findViewById(R.id.familiarSelect);
        String[] items2 = new String[]{"--Please Select One--","Yes", "No"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        familiarSelect.setAdapter(adapter2);
        familiarSelect.setOnItemSelectedListener(this);

        //Password use dropdown
        final Spinner usePasswordSelect = findViewById(R.id.usePasswordSelect);
        String[] items3 = new String[]{"--Please Select One--","Yes", "No"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        usePasswordSelect.setAdapter(adapter3);
        usePasswordSelect.setOnItemSelectedListener(this);

        //Password type dropdown
        final Spinner passwordTypeSelect = findViewById(R.id.passwordTypeSelect);
        String[] items4 = new String[]{"--Please Select One--","Fingerprint","PIN", "Password", "Pattern Lock", "Picture Lock","Facial Recognition","Other"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        passwordTypeSelect.setAdapter(adapter4);
        passwordTypeSelect.setOnItemSelectedListener(this);

        passwordTypeSelect.setVisibility(View.INVISIBLE);
        passwordTypeText.setVisibility(View.INVISIBLE);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  startActivity(new Intent(Demographics.this, Register.class));
                                              }
                                          });

        Button continueButton = (Button)findViewById(R.id.toInstructions); //Initially went to instructions, now goes to consent page
        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){



                emptyCount = 7;

                CharSequence text = "Please Enter all the fields";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                if (genderSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                }
                if (systemSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                }
                if (familiarSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                }
                if (usePasswordSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                    if (usePasswordSelect.getSelectedItemPosition() == 1){
                        if (passwordTypeSelect.getSelectedItemPosition() != 0){
                            emptyCount -=1;
                        }
                    }else{
                        emptyCount-=1;
                    }
                }
                if (ageSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                }
                if (educationSelect.getSelectedItemPosition() != 0){
                    emptyCount -=1;
                }
                if (emptyCount == 0){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    user.setName(name);
                    user.setEmail(email);
                    System.out.println("DEMOGRAPHICS, NAME = "+name+" AND EMAIL = "+email);
                    System.out.println("DEMOGRAPHICS, user.NAME = "+user.getName()+" AND user.EMAIL = "+user.getEmail());
                    user.setAge(age);
                    user.setEducation(education);
                    user.setGender(gender);
                    user.setFamiliar(familiar);
                    user.setPassType(passType);
                    user.setPassUsed(passUsed);
                    user.setSystem(system);


                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Demographics.this,"User has been successfully updated!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Demographics.this,"Failed to create user!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                    toast.show();

                    startActivity(new Intent(Demographics.this, Login.class));
                }else{

                    toast.show();
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView passwordTypeText = findViewById(R.id.passwordTypeText);


        Spinner genderSelect = findViewById(R.id.genderSelect);
        Spinner ageSelect = findViewById(R.id.ageSelect);
        Spinner educationSelect = findViewById(R.id.educationSelect);
        Spinner systemSelect = findViewById(R.id.systemSelect);
        Spinner familiarSelect = findViewById(R.id.familiarSelect);
        Spinner usePasswordSelect = findViewById(R.id.usePasswordSelect);
        Spinner passwordTypeSelect = findViewById(R.id.passwordTypeSelect);


        switch(adapterView.getId()){

            case R.id.genderSelect:
                switch(genderSelect.getSelectedItemPosition()){
                    case 1:
                        gender = "Male";
                        break;
                    case 2:
                        gender = "Female";
                        break;
                    case 3:
                        gender = "Other";
                        break;
                }
                break;

            case R.id.ageSelect:
                switch(ageSelect.getSelectedItemPosition()){
                    case 1:
                        age = "18 or Under";
                        break;
                    case 2:
                        age = "19-21";
                        break;
                    case 3:
                        age = "22-25";
                        break;
                    case 4:
                        age = "26-29";
                        break;
                    case 5:
                        age = "30 or Above";
                        break;

                }
                break;

            case R.id.educationSelect:
                switch(educationSelect.getSelectedItemPosition()){
                    case 1:
                        education = "No Qualifications";
                        break;
                    case 2:
                        education = "GCSEs";
                        break;
                    case 3:
                        education = "A levels";
                        break;
                    case 4:
                        education = "University Degree";
                        break;
                    case 5:
                        education = "Doctorate";
                        break;
                    case 6:
                        education = "Other";
                        break;

                }
                break;

            case R.id.systemSelect:
                switch(systemSelect.getSelectedItemPosition()){
                    case 1:
                        system = "Android";
                        break;
                    case 2:
                        system = "IOS";
                        break;
                    case 3:
                        system = "Other";
                        break;
                }
                break;

            case R.id.familiarSelect:
                switch(familiarSelect.getSelectedItemPosition()){
                    case 1:
                        familiar = "Yes";
                        break;
                    case 2:
                        familiar = "No";
                        break;
                }
                break;

            case R.id.usePasswordSelect:
                switch(usePasswordSelect.getSelectedItemPosition()){
                    case 1:
                        passUsed = "Yes";
                        passwordTypeSelect.setVisibility(View.VISIBLE);
                        passwordTypeText.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        passUsed = "No";
                        passwordTypeSelect.setVisibility(View.INVISIBLE);
                        passwordTypeText.setVisibility(View.INVISIBLE);
                        break;
                }
                break;

            case R.id.passwordTypeSelect:
                switch(passwordTypeSelect.getSelectedItemPosition()){
                    case 1:
                        passType = "Fingerprint";
                        break;
                    case 2:
                        passType = "PIN";
                        break;
                    case 3:
                        passType = "Password";
                        break;
                    case 4:
                        passType = "Pattern Lock";
                        break;
                    case 5:
                        passType = "Picture Lock";
                        break;
                    case 6:
                        passType = "Facial Recognition";
                        break;
                    case 7:
                        passType = "Other";
                        break;
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}