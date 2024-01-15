package com.example.tiffinwala.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffinwala.DetailsSubscription;
import com.example.tiffinwala.R;
import com.example.tiffinwala.Utils.RetroFitClient;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




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

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize your TextViews and other UI elements
        EditTextName1 = view.findViewById(R.id.EditTextName1);
        EditTextName2 = view.findViewById(R.id.EditTextName2);
        EditTextStartDate1 = view.findViewById(R.id.EditTextStartDate1);
        EditTextStartDate2 = view.findViewById(R.id.EditTextStartDate2);
        EditTextEndDate1 = view.findViewById(R.id.EditTextEndDate1);
        EditTextEndDate2 = view.findViewById(R.id.EditTextEndDate2);
        EditTextRemainingDays1 = view.findViewById(R.id.EditTextRemainingDays1);
        EditTextRemainingDays2 = view.findViewById(R.id.EditTextRemainingDays2);
        EditTextAmountPaid1 = view.findViewById(R.id.EditTextAmountPaid1);
        EditTextAmountPaid2 = view.findViewById(R.id.EditTextAmountPaid2);
        subscriptionSwitch = view.findViewById(R.id.subscriptionSwitch);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("shop", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        RetroFitClient.getInstance().getApi().DetailsOfUser(userId).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                if (array.size() == 0) {
                    Toast.makeText(getActivity(), "THERE IS NO ANY DATA", Toast.LENGTH_SHORT).show();
                } else {
                    JsonObject object = array.get(0).getAsJsonObject();
                    String name = object.get("name").getAsString();
                    EditTextName2.setText(name);
                    String start_date = object.get("start_date").getAsString();
                    EditTextStartDate2.setText(start_date);
                    String end_date = object.get("end_date").getAsString();
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
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return view; // Return the configured view
    }

}