package com.example.tiffinwala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        if(getSharedPreferences("shop",MODE_PRIVATE).getBoolean("status",false))
            startActivity(new Intent(Login.this, MainActivity.class));


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (isValidEmail(email) && isValidPassword(password)) {

                    User user1=new User();
                    user1.setEmail(email);
                    user1.setPassword(password);

                    if (checkBoxRememberMe.isChecked())
                        getSharedPreferences("shop",MODE_PRIVATE).edit().putBoolean("status",true).apply();

                    RetroFitClient.getInstance().getApi().ValidateUser(user1).enqueue(new Callback<JsonObject>(){
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                            if (array.size() != 0) {
//                                Toast.makeText(Login.this, "USER GOT", Toast.LENGTH_SHORT).show();
                                  JsonObject object = array.get(0).getAsJsonObject();
                                Log.e("",""+object);
                                getSharedPreferences("shop", MODE_PRIVATE).edit()
                                        .putInt("userId", object.get("userId").getAsInt()).apply();
//                                Toast.makeText(Login.this, ""+object.get("userId"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else
                                Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(Login.this, "failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Invalid email or password, show error message
                    showErrorMessage("Please enter a valid email and password.");
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to registration screen
                navigateToRegistration();
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Add your email validation logic here
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Add your password validation logic here
        return password != null && password.length() >= 6;
    }



    private void navigateToRegistration() {
        // Implement navigation logic to the registration screen
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
    }
    public void forgotPasswordClicked(View v)
    {



        Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}
