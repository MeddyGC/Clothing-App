/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the user to enter details on a form



 **/





package com.example.project1;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;


public class createAcntActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    // The text inputs TextView and buttons on the layout are declared
     EditText fullname;
     EditText password;
     EditText email;
     TextView age;
     RadioGroup radioGroup;
     RadioButton radioButton;
     SharedPreferences sharedPreferences;
     String size;
     Button retrieveButton;
     Button saveButton;
     static final String mypreferences = "mypref";
     static final String nameKey = "nameKey";
     static final String emailKey = "emailKey";
     static final String passwordKey = "passwordKey";
     static final String ageKey = "ageKey";
     static final String sizeKey = "sizeKey";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createacnt);
        //The IDs of the TextView, buttons and other widgets  are declared
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        email = findViewById(R.id.useremail);
        age = findViewById(R.id.dateText);
        radioGroup = findViewById(R.id.sizes);
        saveButton = findViewById(R.id.saveBtn);
        retrieveButton = findViewById(R.id.retrieveBtn);



        //Condition to prevent the user from leaving the form blank
        if(fullname.getText().toString().equals("")||password.getText().toString().equals("")|| email.getText().toString().equals("")){
            Toast toast= Toast. makeText(getApplicationContext(),"All fields must be filled",Toast. LENGTH_SHORT);
            toast.show();
        }



            Button nextPageBtn = (Button) findViewById(R.id.nextBtn);
            //Establishes the on click action for the button
            nextPageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fullname.getText().toString().equals("")||password.getText().toString().equals("")|| email.getText().toString().equals("")){ //This line checks if any fields are left empty
                        Toast toast= Toast. makeText(getApplicationContext(),"All fields must be filled",Toast. LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        int radioID = radioGroup.getCheckedRadioButtonId();
                        radioButton = findViewById(radioID);
                        Intent intent = new Intent(createAcntActivity.this, Main2Activity.class);
                        //The following lines gets the value of textviews
                        String name = fullname.getText().toString();
                        String pword = password.getText().toString();
                        String mail = email.getText().toString();
                        String theage = age.getText().toString();
                         size = radioButton.getText().toString();
                        //The following lines put the user infos in the intent to transfer them
                        intent.putExtra("fullname", name);
                        intent.putExtra("password", pword);
                        intent.putExtra("email", mail);
                        intent.putExtra("Age", theage);
                        intent.putExtra("size", size);

                        startActivity(intent);
                    }
                }
            });


        Button button = (Button) findViewById(R.id.button);
        //Establishes the on click action for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment(); // creates a datepicker
                datePicker.show(getSupportFragmentManager(),"date picker"); //shows datepicker
            }
        });
        //Establishes the on click action for the button
        Button clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fullname.getText().clear();
                password.getText().clear();
                email.getText().clear();
                age.setText("");

            }
        });
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
                if(sharedPreferences.contains(nameKey)){
                    fullname.setText(sharedPreferences.getString(nameKey,"ff"));
                }
                if(sharedPreferences.contains(passwordKey)){
                    password.setText(sharedPreferences.getString(passwordKey,"ff"));
                }
                if(sharedPreferences.contains(emailKey)){
                    email.setText(sharedPreferences.getString(emailKey,"ff"));
                }
                if(sharedPreferences.contains(ageKey)){
                    age.setText(sharedPreferences.getString(ageKey,"ff"));
                }
                if(sharedPreferences.contains(sizeKey)){
                    size=(sharedPreferences.getString(sizeKey,""));
                }

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
                String fname = fullname.getText().toString();
                String pword = password.getText().toString();
                String emailaddress = email.getText().toString();
                String ageStr = age.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(nameKey,fname);
                editor.putString(passwordKey,pword);
                editor.putString(emailKey,emailaddress);
                editor.putString(ageKey,ageStr);
                editor.putString(sizeKey,size);
                editor.commit();
                Toast.makeText(createAcntActivity.this,"Save successful",Toast.LENGTH_SHORT).show();
            }
        });






    }
    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
    }

//    public void save(View v){
//        String fname = fullname.getText().toString();
//        String pword = password.getText().toString();
//        String emailaddress = email.getText().toString();
//        String ageStr = age.getText().toString();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(nameKey,fname);
//        editor.putString(passwordKey,pword);
//        editor.putString(emailKey,emailaddress);
//        editor.putString(ageKey,ageStr);
//        editor.putString(sizeKey,size);
//        Toast.makeText(createAcntActivity.this,"Save successful",Toast.LENGTH_SHORT).show();
//
//
//    }
//
//    public void retrieve(View v) {
//        sharedPreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
//        if(sharedPreferences.contains(nameKey)){
//            fullname.setText(sharedPreferences.getString(nameKey,""));
//
//        }
//        if(sharedPreferences.contains(passwordKey)){
//            password.setText(sharedPreferences.getString(passwordKey,""));
//        }
//        if(sharedPreferences.contains(emailKey)){
//            email.setText(sharedPreferences.getString(emailKey,""));
//        }
//        if(sharedPreferences.contains(ageKey)){
//            age.setText(sharedPreferences.getString(ageKey,""));
//        }
//        if(sharedPreferences.contains(sizeKey)){
//            size=(sharedPreferences.getString(sizeKey,""));
//        }
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String curDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        int dyr = 2020;

        int age = dyr  - year;

        TextView datetext = (TextView) findViewById(R.id.dateText);
        //datetext.setText(curDate);
        datetext.setText(String.valueOf(age));
        /**
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DAY_OF_MONTH);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between (l1, now1);
        int age = diff1.getYears();
        Toast toast= Toast. makeText(getApplicationContext(),String.valueOf(age),Toast. LENGTH_SHORT);
        toast.show();
         **/




    }
}
