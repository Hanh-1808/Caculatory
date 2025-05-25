package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView output;

    String expression = ""; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 

        input = findViewById(R.id.editTextText);
        output = findViewById(R.id.textView);

        int[] buttonIds = {
                R.id.Zero, R.id.One, R.id.Two, R.id.Three, R.id.Four,
                R.id.Five, R.id.Six, R.id.Seven, R.id.Eight, R.id.Nine,
                R.id.Add, R.id.Sub, R.id.Core, R.id.Div,
                R.id.Dot, R.id.percent
        };

        String[] buttonValues = {
                "0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9",
                "-", "+", "*", "/",
                ".", "%"
        };

        for (int i = 0; i < buttonIds.length; i++) {
            final String value = buttonValues[i];
            Button btn = findViewById(buttonIds[i]);
            btn.setOnClickListener(v -> {
                expression += value;
                input.setText(expression);
            });
        }
        Button equalBtn = findViewById(R.id.ES);
        equalBtn.setOnClickListener(v -> {
            try {
                double result = calculate(expression);
                if (result == (int) result) {
                    output.setText(String.valueOf((int) result));
                } else {
                    output.setText(String.valueOf(result));
                }
            } catch (Exception e) {
                output.setText("Lỗi");
            }
        });

        findViewById(R.id.Clear).setOnClickListener(v -> {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1);
                input.setText(expression);
            }
        });

        findViewById(R.id.ClearAll).setOnClickListener(v -> {
            expression = "";
            input.setText("");
            output.setText("Output");
        });
    }

    private double calculate(String expr) {
        expr = expr.replaceAll("[^0-9.+\\-*/%]", "");

        return evalSimple(expr);
    }
    private double evalSimple(String expr) {
        expr = expr.replace("%", "/100");

        String[] tokens = expr.split("(?<=[-+*/])|(?=[-+*/])");

        double result = 0;
        String op = "+";

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;

            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                op = token;
            } else {
                double num = Double.parseDouble(token);
                switch (op) {
                    case "+": result += num; break;
                    case "-": result -= num; break;
                    case "*": result *= num; break;
                    case "/": result /= num; break;
                }
            }
        }

        return result;
    }
}
