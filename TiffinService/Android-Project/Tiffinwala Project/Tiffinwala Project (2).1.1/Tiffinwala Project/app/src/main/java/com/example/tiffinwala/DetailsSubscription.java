package com.example.tiffinwala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.Order;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsSubscription extends AppCompatActivity {

    TextView EditTextName1;
    TextView EditTextName2;
    TextView EditTextStartDate1;
    TextView EditTextStartDate2;
    TextView EditTextEndDate1;
    TextView EditTextEndDate2;
    TextView EditTextRemainingDays1;
    TextView EditTextRemainingDays2;
    TextView EditTextAmountPaid1;
    TextView EditTextAmountPaid2;

    private boolean isSubscriptionPaused = false; // Track subscription state
    private Button subscriptionSwitch;


    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_subscription);

        EditTextName1=findViewById(R.id.EditTextName1);
        EditTextName2=findViewById(R.id.EditTextName2);
        EditTextStartDate1=findViewById(R.id.EditTextStartDate1);
        EditTextStartDate2=findViewById(R.id.EditTextStartDate2);
        EditTextEndDate1=findViewById(R.id.EditTextEndDate1);
        EditTextEndDate2=findViewById(R.id.EditTextEndDate2);
        EditTextRemainingDays1=findViewById(R.id.EditTextRemainingDays1);
        EditTextRemainingDays2=findViewById(R.id.EditTextRemainingDays2);
        EditTextAmountPaid1=findViewById(R.id.EditTextAmountPaid1);
        EditTextAmountPaid2=findViewById(R.id.EditTextAmountPaid2);
        subscriptionSwitch = findViewById(R.id.subscriptionSwitch);

        SharedPreferences sharedPreferences = getSharedPreferences("shop", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);


        RetroFitClient.getInstance().getApi().DetailsOfUser(userId).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                Log.e("",""+array);
                if (array.size() == 0) {

                    Toast.makeText(DetailsSubscription.this, "THERE IS NO ANY DATA", Toast.LENGTH_SHORT).show();
                } else
                {
                    JsonObject object = array.get(0).getAsJsonObject();
                    Log.e("",""+object);
                    String name=object.get("name").getAsString();
                    Log.e("",""+name);
                    EditTextName1.setText(name);
                    String start_date=object.get("start_date").getAsString();
                    EditTextStartDate2.setText(start_date);
                    String end_date=object.get("end_date").getAsString();
                    EditTextEndDate2.setText(end_date);
                    double totalAmount = object.get("totalAmount").getAsDouble();
                    String formattedTotalAmount = String.valueOf(totalAmount);
                    EditTextAmountPaid2.setText(formattedTotalAmount);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                    try {
                        Date startDate = sdf.parse(start_date);
                        Date endDate = sdf.parse(end_date);

                        Date currentDate = new Date();

                        if (currentDate.before(startDate)) {
                            EditTextRemainingDays2.setText("Your subscription has not started yet");
                        } else if (currentDate.equals(startDate)) {
                            // Calculate the difference between start date and current date
                            long remainingMillis = endDate.getTime() - currentDate.getTime();
                            long remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis);

                            // Display remaining days in the TextView
                            EditTextRemainingDays2.setText(String.valueOf(remainingDays));
                        } else {
                            // Subscription has already started, display remaining days as before
                            long remainingMillis = endDate.getTime() - currentDate.getTime();
                            long remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis);

                            // Display remaining days in the TextView
                            EditTextRemainingDays2.setText(String.valueOf(remainingDays));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsSubscription.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
        


    }


    }
