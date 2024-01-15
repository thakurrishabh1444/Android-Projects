package com.example.tiffinwala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tiffinwala.entity.subscriped_user;

import java.io.Serializable;

public class PaymentActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent=getIntent();
        subscriped_user user1= (subscriped_user) intent.getSerializableExtra("subscriped_user");
        Toast.makeText(this, ""+user1, Toast.LENGTH_SHORT).show();

        // Initialize UI elements
        Button buttonMakePayment = findViewById(R.id.buttonMakePayment);

        // Set click listener for make payment button
        buttonMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Toast.makeText(PaymentActivity.this, "Payment successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
