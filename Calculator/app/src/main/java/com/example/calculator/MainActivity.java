package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private String currentExpression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "=":
                try {
                    double result = evaluateExpression(currentExpression);
                    editText.setText(String.valueOf(result));
                    currentExpression = String.valueOf(result);
                } catch (Exception e) {
                    editText.setText("Error");
                }
                break;

            case "C":
                editText.setText("0");
                currentExpression = "";
                break;

            default:
                if (currentExpression.equals("0")) {
                    currentExpression = buttonText;
                } else {
                    currentExpression += buttonText;
                }
                editText.setText(currentExpression);
        }
    }

    private double evaluateExpression(String expression) {
        try {
            ExpressionBuilder builder = new ExpressionBuilder(expression);
            BigDecimal result = BigDecimal.valueOf(builder.build().evaluate());
            return result.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }

}
