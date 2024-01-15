package com.example.tiffinwala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button submitOTPButton;
    private String generatedOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        otpEditText = findViewById(R.id.otpEditText);
        submitOTPButton = findViewById(R.id.submitOTPButton);

        // Replace this with the logic to generate and store the OTP when sending it via email
        generatedOTP = "123456"; // For demonstration purposes; replace with actual OTP

        submitOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered OTP
                String enteredOTP = otpEditText.getText().toString().trim();

                // Validate the entered OTP
                if (enteredOTP.equals(generatedOTP)) {
                    // OTP is valid; allow the user to reset their password
                    // Implement password reset logic here

                    // For demonstration, show a success message
                    Toast.makeText(ForgotPasswordActivity.this, "OTP is valid", Toast.LENGTH_SHORT).show();

                    // Add code to navigate to the password reset screen or logic
                } else {
                    // Invalid OTP; show an error message
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
