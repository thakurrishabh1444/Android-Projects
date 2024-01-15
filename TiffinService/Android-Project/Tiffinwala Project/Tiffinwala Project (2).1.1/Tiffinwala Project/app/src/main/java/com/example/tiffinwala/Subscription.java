package com.example.tiffinwala;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.Order;
import com.example.tiffinwala.entity.subscriped_user;


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

//subscription and order inserted successfully

public class Subscription extends AppCompatActivity implements Serializable {

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



    //************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        SharedPreferences sharedPreferences = getSharedPreferences("shop", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);

        // Initialize UI elements
        radioGroupSubscriptionOptions = findViewById(R.id.radioGroupSubscriptionOptions);
        radioButtonVeg = findViewById(R.id.radioButtonVeg);
        radioButtonNonVeg = findViewById(R.id.radioButtonNonVeg);
        radioButtonWeek = findViewById(R.id.radioButtonWeek);
        radioButtonMonth = findViewById(R.id.radioButtonMonth);
        radioButtonQuaterly = findViewById(R.id.radioButtonQuaterly);
        radioButtonLunch=findViewById(R.id.radioButtonLunch);
        radioButtonDinner=findViewById(R.id.radioButtonDinner);
        radioButtonBoth=findViewById(R.id.radioButtonBoth);

        totalAmount=findViewById(R.id.totalAmount);
        buttonProceedToPayment = findViewById(R.id.buttonProceedToPayment);


        //*********************************************************************
        disableSubscriptionRadioButtons();

        selectDateButton = findViewById(R.id.selectDateButton);

        Start_date=findViewById(R.id.Start_date);
        End_date=findViewById(R.id.End_date);

        selectedDateCalendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        //*********************************************************************



        radioButtonVeg.setOnCheckedChangeListener((buttonView, isChecked)->
        {
            if(isChecked)
            {
                Start_date.setText("");
                End_date.setText("");
                AmountOfMenu=0;
                AmountOfMenu=60;
                totalAmount.setText("TOTAL AMOUNT :"+ AmountOfMenu);

                selectedMenu="VEG";

                enableSubscriptionRadioButtons();
            }
        });

        radioButtonNonVeg.setOnCheckedChangeListener((buttonView, isChecked)->
        {

            if(isChecked)
            {
                Start_date.setText("");
                End_date.setText("");
                AmountOfMenu=0;
                AmountOfMenu=90;
                totalAmount.setText("TOTAL AMOUNT :"+ AmountOfMenu);

                selectedMenu="NONVEG";

                enableSubscriptionRadioButtons();
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
                Toast.makeText(this, "FIRST SELECT VEG OR NON VEG SECTION", Toast.LENGTH_SHORT).show();
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
                Temp=totalAmount1;;

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
                name="QUATRLY KIT";
                meal_selected="LUNCH";
                selectedSubscription="90 DAYS";

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
//                AmountOfDuration = 0;
//                prize = AmountOfDuration * 2;
//                totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);
                name="QUATRLY KIT";
                meal_selected="DINNER";
                selectedSubscription="90 DAYS";

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
                //AmountOfDuration = 0;
//                totalAmount.setText("TOTAL AMOUNT :" + AmountOfDuration);
                name="QUATRLY KIT";
                meal_selected="BOTH";
                selectedSubscription="90 DAYS";
                totalAmount1=Temp*2;
                totalAmount.setText("TOTAL AMOUNT :" + totalAmount1);
            }
        });

        buttonProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Subscription.this, "" + userId, Toast.LENGTH_SHORT).show();
                subscriped_user user1 = new subscriped_user(name, meal_selected, selectedSubscription, start_date, end_date, selectedMenu, isPause, userId);

         if((radioButtonVeg.isChecked()||radioButtonNonVeg.isChecked()) && (radioButtonWeek.isChecked()|| radioButtonMonth.isChecked()||radioButtonQuaterly.isChecked())){

             //check already subscribe or not..if subscribed not allowed to subscribe

             RetroFitClient.getInstance().getApi().checkAlreadySubscribeOrNot(userId).enqueue(new Callback<JsonObject>() {

                 @Override
                 public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                     JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                     Toast.makeText(Subscription.this, ""+array.size(), Toast.LENGTH_SHORT).show();
                     if (array.size() == 0) {
                         RetroFitClient.getInstance().getApi().SubscribedUser(user1).enqueue(new Callback<JsonObject>() {
                             @Override
                             public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                 Toast.makeText(Subscription.this, "SUBSCRIBE USER INSERTED", Toast.LENGTH_SHORT).show();
                                 finish();

                             }

                             @Override
                             public void onFailure(Call<JsonObject> call, Throwable t) {
                                 Toast.makeText(Subscription.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                                                 Toast.makeText(Subscription.this, "ORDER DONE", Toast.LENGTH_SHORT).show();
                                                 startActivity(new Intent(Subscription.this, MainActivity.class));


                                             }
                                             @Override
                                             public void onFailure(Call<JsonObject> call, Throwable t) {
                                                 Toast.makeText(Subscription.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                             }
                                         });
                                     }
                                 }

                             }
                             @Override
                             public void onFailure(Call<JsonObject> call, Throwable t) {
                                 Toast.makeText(Subscription.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                             }
                         });
                         finish();
                     } else
                     {
//                         startActivity(new Intent(Subscription.this, DetailsSubscription.class));

                         Toast.makeText(Subscription.this, "okkkk", Toast.LENGTH_SHORT).show();
                     }


                 }

                 @Override
                 public void onFailure(Call<JsonObject> call, Throwable t) {
                     Toast.makeText(Subscription.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                 }
             });


         }
         else {
             Toast.makeText(Subscription.this, "SELECT VALID SUBSCRIPTION", Toast.LENGTH_SHORT).show();
         }

            }
        });
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

                Toast.makeText(Subscription.this, ""+start_date, Toast.LENGTH_SHORT).show();

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

        //new DatePickerDialog(this, dateSetListener, year, month, day).show();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(selectedDateCalendar.getTimeInMillis()); // Set minimum date

        datePickerDialog.show();
    }
}