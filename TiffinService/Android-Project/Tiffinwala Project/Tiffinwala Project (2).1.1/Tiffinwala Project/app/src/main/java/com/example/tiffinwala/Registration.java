package com.example.tiffinwala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, editArea,phoneEditText,address_Edittext;
    private Button registerButton;
    private TextView loginTextView;

    private Spinner areaSpinner;

    String selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText=findViewById(R.id.phoneEditText);
        address_Edittext=findViewById(R.id.address_Edittext);
        Spinner areaSpinner = findViewById(R.id.areaSpinner);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        String[] areas = {"PHASE1","PHASE2","PHASE3"};

        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, areas);

        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        areaSpinner.setAdapter(areaAdapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name=fullNameEditText.getText().toString();
                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String phone=phoneEditText.getText().toString();
                String Address=address_Edittext.getText().toString();
                selectedArea = areaSpinner.getSelectedItem().toString();

                areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        selectedArea = areas[position];
                        selectedArea = areaSpinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), "Selected Area: " + selectedArea, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Do nothing if nothing is selected
                    }
                });

                User user1=new User();
                user1.setName(name);
                user1.setEmail(email);
                user1.setPassword(password);
                user1.setPhone(phone);
                user1.setAddress(Address);
                user1.setArea(selectedArea);
                Log.e("",""+selectedArea);
                 Log.e("Check", ""+user1);
                RetroFitClient.getInstance().getApi().register(user1).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(Registration.this, "REGISTRATION DONE", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(Registration.this, "FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to login screen
                navigateToLogin();
            }
        });
    }



    private boolean isValidFullName(String fullName) {
        // Add your full name validation logic here
        return fullName != null && !fullName.isEmpty();
    }

    private boolean isValidEmail(String email) {
        // Add your email validation logic here
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Add your password validation logic here
        return password != null && password.length() >= 6;
    }

    private boolean isRegistrationSuccessful(String fullName, String email, String Area, String password) {
        // Add your registration logic here
        // You can save the user details to a database or perform any necessary operations
        // For demonstration purposes, let's assume the registration is always successful
        return true;
    }

    private void navigateToLogin() {
        // Implement navigation logic to the login screen
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
        finish(); // Optional: Call finish to prevent the user from going back to the registration screen
    }

    private void showErrorMessage(String message) {
        Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
    }


}
