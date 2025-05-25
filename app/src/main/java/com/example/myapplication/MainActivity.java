package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView output;

    String expression = ""; // lưu biểu thức người dùng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout bạn gửi ở trên

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

        // Xử lý nút "="
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

        // Xử lý nút "AC" – xóa hết
        findViewById(R.id.ClearAll).setOnClickListener(v -> {
            expression = "";
            input.setText("");
            output.setText("Output");
        });
    }

    // Hàm tính toán đơn giản (chỉ hỗ trợ + - * / % và .)
    private double calculate(String expr) {
        // Loại bỏ các ký tự lạ
        expr = expr.replaceAll("[^0-9.+\\-*/%]", "");

        // Sử dụng JavaScript Engine đơn giản nếu cho phép
        // Nhưng ở đây ta làm đơn giản: chỉ tính toán chuỗi không ngoặc
        // Dùng split và tính từng bước

        return evalSimple(expr);
    }

    // Cách thủ công đơn giản: chỉ tính toán trái sang phải
    private double evalSimple(String expr) {
        // Replace % bằng /100 để xử lý phần trăm
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
