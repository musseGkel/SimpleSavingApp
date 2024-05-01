package com.example.simplesavingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText username, location;

    String usernameText, locationText;

    Switch graySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.userNameInput);
        location = findViewById(R.id.locationInput);
        graySwitch= findViewById(R.id.graySwitch);
        sharedPreferences = getSharedPreferences("com.example.simplesavingapp", Context.MODE_PRIVATE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.clearButton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadInfo();
    }

    public void saveInfo(View view){
        usernameText = String.valueOf(username.getText());
        locationText= String.valueOf(location.getText());
        SharedPreferences.Editor preferencesEditor= sharedPreferences.edit();
        preferencesEditor.putString("username", usernameText);
        preferencesEditor.putString("location", locationText);
        preferencesEditor.apply();
    }
    public void clearInfo(View view){
        SharedPreferences.Editor preferenceEditor= sharedPreferences.edit();
        preferenceEditor.clear();
        preferenceEditor.apply();
        username.setText("");
        location.setText("");
    }
    public void loadInfo(){
        String savedUsername= sharedPreferences.getString("username","");
        String savedLocation= sharedPreferences.getString("location","");
        username.setText(savedUsername);
        location.setText(savedLocation);

        boolean grayBackground=sharedPreferences.getBoolean("grayBackground",false);
        if(grayBackground){
            graySwitch.setChecked(true);
            getWindow().getDecorView().setBackgroundColor(Color.GRAY);
        } else{
            graySwitch.setChecked(false);
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
    }
    public void graySwitch(View view){
        SharedPreferences.Editor preferenceEditor= sharedPreferences.edit();
        boolean isChecked = graySwitch.isChecked();
        System.out.println("isChecked: "+isChecked);
        if(isChecked){
            getWindow().getDecorView().setBackgroundColor(Color.GRAY);
        } else{
            System.out.println("else isChecked: " + graySwitch.isChecked());
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
        preferenceEditor.putBoolean("grayBackground",isChecked);
        preferenceEditor.apply();
    }

    public void closeApp(View v){
        finishAndRemoveTask();
    }
}