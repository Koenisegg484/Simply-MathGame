package com.example.simplymathgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button addition;
    Button subtract;
    Button divide;
    Button multiply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(MainActivity.this, QuizPage.class);

        addition = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);


        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operator = 1;
                intent.putExtra("opr", operator);
                Log.d("operator", "Addition");
                startActivity(intent);
                finish();
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operator = 2;
                intent.putExtra("opr", operator);
                Log.d("operator", "Subtraction");
                startActivity(intent);
                finish();
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operator = 3;
                intent.putExtra("opr", operator);
                Log.d("operator", "Multiplication");
                startActivity(intent);
                finish();
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operator = 4;
                intent.putExtra("opr", operator);
                Log.d("operator", "Division");
                startActivity(intent);
                finish();
            }
        });
    }
}