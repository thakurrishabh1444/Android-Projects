package com.example.tiffinwala.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffinwala.R;
import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.entity.update_user;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends Fragment {

    TextView editTextName;


    TextView editTextEmail1;


    TextView editTextPassword;


    TextView editTextPhone;


    TextView editTextAddress;

    private Button updateButton;

    SharedPreferences sharedPreferences;


    int userId;



    Spinner spinnerArea;
    String listArea[] = new String []{"Phase1","Phase2","Phase3"};


    //*****************************************************************************

    String name;
    String email;

    String password;

    String phone;

    String address;

    String area;

    update_user user1;


    //***************************************************************************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerArea = view.findViewById(R.id.SpinnerArea);
        editTextName=view.findViewById(R.id.editTextName);
        editTextEmail1=view.findViewById(R.id.editTextEmail1);
        editTextPassword=view.findViewById(R.id.editTextPassword);
        editTextPhone=view.findViewById(R.id.editTextPhone);
        editTextAddress=view.findViewById(R.id.editTextAddress);
        updateButton = view.findViewById(R.id.updateButton);



        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listArea);
        spinnerArea.setAdapter(arrayAdapter);

        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("shop", Context.MODE_PRIVATE);

        // Retrieve the userId from shared preferences
        userId = sharedPreferences.getInt("userId", 0);



        RetroFitClient.getInstance().getApi().getUser(userId).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                if (array.size() != 0) {
//                    Toast.makeText(getActivity(), "USER GOT", Toast.LENGTH_SHORT).show();
                    JsonObject object = array.get(0).getAsJsonObject();
//                    Toast.makeText(getActivity(), ""+object, Toast.LENGTH_SHORT).show();

                    String name1;
                    String email1;
                    String password1;
                    String phone1;
                    String address1;
                    String area1;
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(object));
                         name1 = jsonObject.getString("name");
                         email1=jsonObject.getString("email");
                         password1=jsonObject.getString("password");
                         phone1=jsonObject.getString("phone");
                         address1=jsonObject.getString("address");
                         area1=jsonObject.getString("area");

//                         Log.e("",""+email1);
//                        Log.e("",""+password1);
//                        Log.e("",""+phone1);
//                        Log.e("",""+address1);
//                        Log.e("",""+area1);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                     editTextName.setText(name1);
                     editTextEmail1.setText(email1);
                     editTextPassword.setText(password1);
                     editTextPhone.setText(phone1);
                     editTextAddress.setText(address1);


                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Collections.singletonList(area1));
                    spinnerArea.setAdapter(arrayAdapter);


                    ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listArea);
                    spinnerArea.setAdapter(arrayAdapter1);



                    spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                             area = listArea[position]; // Get the selected area from the list
                             getAllUpdatedDetails();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing when nothing is selected
                        }
                    });

                } else
                    Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }

            public void getAllUpdatedDetails()
            {
                name=editTextName.getText().toString();
                email=editTextEmail1.getText().toString();
                password=editTextPassword.getText().toString();
                phone=editTextPhone.getText().toString();
                address=editTextAddress.getText().toString();

            }

        });





        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClicked();
            }

            private void onUpdateButtonClicked() {
                name=editTextName.getText().toString();
                email=editTextEmail1.getText().toString();
                password=editTextPassword.getText().toString();
                phone=editTextPhone.getText().toString();
                address=editTextAddress.getText().toString();

                user1=new update_user(name,email,password,phone,address,area,userId);

                RetroFitClient.getInstance().getApi().insert_updated_user(user1).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(getActivity(), "DATA INSERTED SUCCESFULLY", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), "FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}