package com.example.tiffinwala.fragments;
import static android.content.Context.MODE_PRIVATE;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tiffinwala.DetailsSubscription;


import com.example.tiffinwala.Login;
import com.example.tiffinwala.MainActivity;
import com.example.tiffinwala.R;
import com.example.tiffinwala.Subscription;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.Order;
import com.example.tiffinwala.entity.subscriped_user;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class subscriptions<Subscriptions> extends Fragment {

    private RadioGroup radioGroupSubscriptionOptions;
    private RadioButton radioButtonVeg;
    private RadioButton radioButtonNonVeg;

    private RadioButton radioButtonWeek;

    private RadioButton radioButtonMonth;

    private RadioButton radioButtonQuaterly;

    private  RadioButton radioButtonLunch;

    private  RadioButton radioButtonDinner;

    private  RadioButton radioButtonBoth;



    private Button buttonProceedToPayment;

    TextView Start_date;

    TextView End_date;

    TextView totalAmount;

    String name=null;
    String meal_selected=null;
    String selectedSubscription=null;

    String start_date;

    String end_date;
    String selectedMenu=null;
    int isPause=0;

    int AmountOfMenu;

    int AmountOfDuration;


    double totalAmount1;

    int subscriptionId;

    int userId;


    //************************************************************************


    private Button selectDateButton;
    private Calendar selectedDateCalendar;
    private SimpleDateFormat sdf;

    double Temp;

    SharedPreferences sharedPreferences;

    private static final int SMS_PERMISSION_CODE = 101;


    subscriped_user user1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //*****************************************************************


//        SharedPreferences sharedPreferences = getSharedPreferences("shop", MODE_PRIVATE);
//        userId = sharedPreferences.getInt("userId",0);


        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("shop", MODE_PRIVATE);

        // Retrieve the userId from shared preferences
        userId = sharedPreferences.getInt("userId", 0);

        // Now you can use the userId as needed within the fragment

        // Initialize UI elements
        radioGroupSubscriptionOptions =view.findViewById(R.id.radioGroupSubscriptionOptions);
        radioButtonVeg = view.findViewById(R.id.radioButtonVeg);
        radioButtonNonVeg = view.findViewById(R.id.radioButtonNonVeg);
        radioButtonWeek = view.findViewById(R.id.radioButtonWeek);
        radioButtonMonth =view.findViewById(R.id.radioButtonMonth);
        radioButtonQuaterly =view.findViewById(R.id.radioButtonQuaterly);
        radioButtonLunch=view.findViewById(R.id.radioButtonLunch);
        radioButtonDinner=view.findViewById(R.id.radioButtonDinner);
        radioButtonBoth=view.findViewById(R.id.radioButtonBoth);

        totalAmount=view.findViewById(R.id.totalAmount);
        buttonProceedToPayment =view.findViewById(R.id.buttonProceedToPayment);


        disableSubscriptionRadioButtons();

        selectDateButton =view.findViewById(R.id.selectDateButton);

        Start_date=view.findViewById(R.id.Start_date);
        End_date=view.findViewById(R.id.End_date);

        selectedDateCalendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        radioButtonVeg.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked)
            {
                if(totalAmount1>400)
                {
                    AmountOfMenu = 60;
                    if(radioButtonWeek.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*7;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }

                    if(radioButtonMonth.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*30;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }

                    if(radioButtonQuaterly.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*90;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }

                }

               else {
                    Start_date.setText("");
                    End_date.setText("");
                    AmountOfMenu=0;
                    AmountOfMenu=60;
                    totalAmount.setText("TOTAL AMOUNT :"+ AmountOfMenu);

                    selectedMenu="VEG";

                    enableSubscriptionRadioButtons();
                }

            }
        });

        radioButtonNonVeg.setOnCheckedChangeListener((buttonView, isChecked)->
        {

            if(isChecked)
            {
                //************
                if(totalAmount1>400)
                {
                    AmountOfMenu = 90;
                    if(radioButtonWeek.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*7;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }
                    if(radioButtonMonth.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*30;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }

                    if(radioButtonQuaterly.isChecked())
                    {
                        if (isChecked)
                        {
                            totalAmount1=AmountOfMenu*90;
                            totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
                        }
                    }

                }
                else {
                    //*********
                    Start_date.setText("");
                    End_date.setText("");
                    AmountOfMenu = 0;
                    AmountOfMenu = 90;
                    totalAmount.setText("TOTAL AMOUNT :" + AmountOfMenu);

                    selectedMenu = "NONVEG";

                    enableSubscriptionRadioButtons();
                }
            }
        });




        radioButtonWeek.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(radioButtonVeg.isChecked()||radioButtonNonVeg.isChecked())
            {
                if(isChecked) {
                    Start_date.setText("");
                    End_date.setText("");
                    AmountOfDuration = 0;

                    AmountOfDuration = AmountOfMenu * 7;
                    totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);

                    name="WEEKLY KIT";
                    selectedSubscription="7 DAYS";

                    totalAmount1=AmountOfDuration;
                    Temp=totalAmount1;

                    enableSubscriptionMeal_Selected();
                }
            }
            else {
                Toast.makeText(getActivity(), "FIRST SELECT VEG OR NON VEG SECTION", Toast.LENGTH_SHORT).show();
            }

        });


        radioButtonMonth.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked) {
                Start_date.setText("");
                End_date.setText("");
                AmountOfDuration = 0;
                AmountOfDuration = AmountOfMenu * 30;
                totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);
                name="MONTHLY KIT";
                selectedSubscription="30 DAYS";
                totalAmount1=AmountOfDuration;
                Temp=totalAmount1;
                enableSubscriptionMeal_Selected();
            }
        });

        radioButtonQuaterly.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked) {
                Start_date.setText("");
                End_date.setText("");
                AmountOfDuration = 0;
                AmountOfDuration = AmountOfMenu * 90;
                totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);
                name="QUATRLY KIT";
                selectedSubscription="90 DAYS";
                totalAmount1=AmountOfDuration;
                Temp=totalAmount1;
                enableSubscriptionMeal_Selected();
            }
        });

        radioButtonLunch.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked) {
                Start_date.setText("");
                End_date.setText("");
                //AmountOfDuration = 0;

                totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);
                totalAmount1=Temp;
                totalAmount1=AmountOfDuration;

                totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
            }
        });

        radioButtonDinner.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked) {
                Start_date.setText("");
                End_date.setText("");
                meal_selected="DINNER";
                totalAmount1=Temp;
                totalAmount1=AmountOfDuration;

                totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
            }
        });

        radioButtonBoth.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked) {
                Start_date.setText("");
                End_date.setText("");
                meal_selected="BOTH";
                totalAmount1=Temp*2;
                totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
            }
        });



        buttonProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "" + userId, Toast.LENGTH_SHORT).show();
                 user1 = new subscriped_user(name, meal_selected, selectedSubscription, start_date, end_date, selectedMenu, isPause, userId);

                if((radioButtonVeg.isChecked()||radioButtonNonVeg.isChecked()) && (radioButtonWeek.isChecked()|| radioButtonMonth.isChecked()||radioButtonQuaterly.isChecked())&&(start_date!=null)&&(end_date!=null)){

                    //check already subscribe or not..if subscribed not allowed to subscribe

                    RetroFitClient.getInstance().getApi().checkAlreadySubscribeOrNot(userId).enqueue(new Callback<JsonObject>() {

                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
//                            Toast.makeText(getActivity(), ""+array.size(), Toast.LENGTH_SHORT).show();
                            if (array.size() == 0) {
                                RetroFitClient.getInstance().getApi().SubscribedUser(user1).enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                         Log.e("","USER 1 :"+user1);

//                                        String phoneNumber = "9321074722"; // Replace with the user's phone number
//                                        String message = "Thank you for subscribing to our service!";
//                                        sendSMS(phoneNumber, message);
//                                        Toast.makeText(getActivity(), "SUBSCRIBE USER INSERTED", Toast.LENGTH_SHORT).show();
////                                      finish();

                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                RetroFitClient.getInstance().getApi().getSubscriptionId(userId).enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                                        if (array.size() != 0) {
                                            JsonObject object = array.get(0).getAsJsonObject();
                                            subscriptionId=object.get("subscriptionId").getAsInt();
                                            if(subscriptionId!=0)
                                            {
                                                Order order1=new Order(totalAmount1,subscriptionId,userId);
                                                RetroFitClient.getInstance().getApi().insert_order(order1).enqueue(new Callback<JsonObject>() {
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                        requestPermission();
                                                        Toast.makeText(getActivity(), "ORDER DONE", Toast.LENGTH_SHORT).show();

                                                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                                        // Replace the current fragment with the home fragment
                                                        Fragment homeFragment = new homefragment(); // Replace HomeFragment with the actual name of your home fragment
                                                        fragmentTransaction.replace(R.id.recyclerView, homeFragment);

                                                        // Add the transaction to the back stack (optional)
                                                        fragmentTransaction.addToBackStack(null);

                                                        // Commit the transaction
                                                        fragmentTransaction.commit();



                                                    }
                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
//                                finish();
                            } else
                            {
                                Toast.makeText(getActivity(), "USER ALREADY SUBSCRIBE", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), DetailsSubscription.class));
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else {
                    Toast.makeText(getActivity(), "SELECT VALID SUBSCRIPTION", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //*****************************************************************

    }

    private void sendSMS(String phoneNumber, subscriped_user user) {
        Log.e("",""+user);
        String smsText = "SELECTED MENU: " + user.getSelectedMenu() + "\n" +
                "DURATION OF SUBSCRIPTION: " + user.getSelectedSubscription() + "\n" +
                "START DATE: " + user.getStartdate() + "\n" +
                "END DATE: " + user.getLastdate();

        String serializedUser = serializeUserToJson(user);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsText, null, null);
            Toast.makeText(getActivity(), "SMS sent successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }




    private void requestPermission() {
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            // Permission is already granted, so you can send SMS here
            RetroFitClient.getInstance().getApi().GetMobileNumber(userId).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Toast.makeText(getActivity(), "HERE", Toast.LENGTH_SHORT).show();
                    JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    if (array.size() != 0) {
                        JsonObject object = array.get(0).getAsJsonObject();
                        Log.e("", "" + object);
                        String phone = object.get("phone").getAsString();
                        Log.e("", "String  number" + phone);
                        sendSMS("" + phone, user1);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getActivity(), "FAILURE", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String serializeUserToJson(subscriped_user user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    private subscriped_user deserializeUserFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, subscriped_user.class);
    }


    private boolean checkPermission() {
     int permission = ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.SEND_SMS);
        return permission == PackageManager.PERMISSION_GRANTED;
    }



    private void disableSubscriptionRadioButtons() {
        radioButtonWeek.setEnabled(false);
        radioButtonMonth.setEnabled(false);
        radioButtonQuaterly.setEnabled(false);
        radioButtonLunch.setEnabled(false);
        radioButtonDinner.setEnabled(false);
        radioButtonBoth.setEnabled(false);

    }

    private void enableSubscriptionRadioButtons() {

        radioButtonWeek.setEnabled(true);
        radioButtonMonth.setEnabled(true);
        radioButtonQuaterly.setEnabled(true);
    }

    private void enableSubscriptionMeal_Selected()
    {
        radioButtonLunch.setEnabled(true);
        radioButtonDinner.setEnabled(true);
        radioButtonBoth.setEnabled(true);
    }

    private void disableSubscriptionMeal_Selected()
    {
        radioButtonLunch.setEnabled(false);
        radioButtonDinner.setEnabled(false);
        radioButtonBoth.setEnabled(false);
    }
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDateCalendar.set(Calendar.YEAR, year);
                selectedDateCalendar.set(Calendar.MONTH, monthOfYear);
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date selectedDate = selectedDateCalendar.getTime();

                start_date = sdf.format(selectedDate);
                Start_date.setText(start_date);


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("selected_date", start_date);

//                Toast.makeText(getActivity(), ""+start_date, Toast.LENGTH_SHORT).show();

                if(radioButtonWeek.isChecked())
                {
                    //WHOLE INFO ABOUT CURRENT DATE ,LAST DATE AND ALL
                    selectedDateCalendar.add(Calendar.WEEK_OF_YEAR, 1);

                    //START DATE IN STRING FORMAT
                    Log.e("","SELECTED DATE  :"+start_date);

                    //YEAR IS HERE
                    int current_year=selectedDateCalendar.get(Calendar.YEAR);
                    String y= String.valueOf(current_year);
                    //Log.e("","current year :"+current_year);

                    //SELECTED MONTH
                    int Monthfromcalender = selectedDateCalendar.get(Calendar.MONTH);
                    Monthfromcalender= Monthfromcalender+1;
                    String month= String.valueOf(Monthfromcalender);
                    if(month.length()==1)
                    {
                        month="0"+month;
                        //Log.e("MONTH OF CALENDER :",""+month);
                    }
                    else if(month.length()==2)
                    {
                        month=month;
                    }

                    //END DATE IS HERE
                    int End_date=selectedDateCalendar.get(Calendar.DAY_OF_MONTH);
                    String lastdate= String.valueOf(End_date);
                    if(lastdate.length()==1)
                    {
                        lastdate="0"+lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }
                    else if(lastdate.length()==2)
                    {
                        lastdate=lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }

                    String s = "-";
                    end_date=y+s+month+s+lastdate;

                    Log.e("","END DATE :"+end_date);
                }

                else if (radioButtonMonth.isChecked())
                {
                    selectedDateCalendar.add(Calendar.MONTH, 1);

                    //START DATE IN STRING FORMAT
                    Log.e("","SELECTED DATE  :"+start_date);

                    //YEAR IS HERE
                    int current_year=selectedDateCalendar.get(Calendar.YEAR);
                    String y= String.valueOf(current_year);
                    //Log.e("","current year :"+current_year);

                    //SELECTED MONTH
                    int Monthfromcalender = selectedDateCalendar.get(Calendar.MONTH);
                    Monthfromcalender= Monthfromcalender+1;
                    String month= String.valueOf(Monthfromcalender);
                    if(month.length()==1)
                    {
                        month="0"+month;
                        //Log.e("MONTH OF CALENDER :",""+month);
                    }
                    else if(month.length()==2)
                    {
                        month=month;
                    }

                    //END DATE IS HERE
                    int End_date=selectedDateCalendar.get(Calendar.DAY_OF_MONTH);
                    String lastdate= String.valueOf(End_date);
                    if(lastdate.length()==1)
                    {
                        lastdate="0"+lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }
                    else if(lastdate.length()==2)
                    {
                        lastdate=lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }

                    String s = "-";
                    end_date=y+s+month+s+lastdate;

                    Log.e("","END DATE :"+end_date);

                }
                else if (radioButtonQuaterly.isChecked())
                {
                    selectedDateCalendar.add(Calendar.MONTH, 3);

                    //START DATE IN STRING FORMAT
                    Log.e("","SELECTED DATE  :"+start_date);

                    //YEAR IS HERE
                    int current_year=selectedDateCalendar.get(Calendar.YEAR);
                    String y= String.valueOf(current_year);
                    //Log.e("","current year :"+current_year);

                    //SELECTED MONTH
                    int Monthfromcalender = selectedDateCalendar.get(Calendar.MONTH);
                    Monthfromcalender= Monthfromcalender+1;
                    String month= String.valueOf(Monthfromcalender);
                    if(month.length()==1)
                    {
                        month="0"+month;
                        //Log.e("MONTH OF CALENDER :",""+month);
                    }
                    else if(month.length()==2)
                    {
                        month=month;
                    }

                    //END DATE IS HERE
                    int End_date=selectedDateCalendar.get(Calendar.DAY_OF_MONTH);
                    String lastdate= String.valueOf(End_date);
                    if(lastdate.length()==1)
                    {
                        lastdate="0"+lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }
                    else if(lastdate.length()==2)
                    {
                        lastdate=lastdate;
                        //Log.e("","END DATE IS HERE :"+End_date);
                    }

                    String s = "-";
                    end_date=y+s+month+s+lastdate;

                    Log.e("","END DATE :"+end_date);
                }
                End_date.setText(end_date);
            }
        };

        int year = selectedDateCalendar.get(Calendar.YEAR);
        int month = selectedDateCalendar.get(Calendar.MONTH);
        int day = selectedDateCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(selectedDateCalendar.getTimeInMillis()); // Set minimum date

        datePickerDialog.show();
    }
}