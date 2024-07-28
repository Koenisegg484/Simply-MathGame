package com.example.simplymathgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Random;

public class QuizPage extends AppCompatActivity {
    TextView score;
    TextView life;
    int uScore;
    int ulives = 3;
    TextView timer;
    TextView question;
    EditText answer;
    ImageView failure;
    Button submit;
    Button nextQuestion;
    Random rnd = new Random();
    int num1;
    int num2;
    int userAnswer;
    int correctAnswer;
    CountDownTimer ctimer;

    private static final long START_TIMER_IN_MILIS = 60000;
    Boolean timerRunning;
    long timeL_left_in_milis = START_TIMER_IN_MILIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        score = findViewById(R.id.score);
        timer = findViewById(R.id.timer);
        life = findViewById(R.id.lives);
        failure = findViewById(R.id.failure);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        submit = findViewById(R.id.submit);
        nextQuestion = findViewById(R.id.next);

        gameContinue();

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                userAnswer = Integer.parseInt(answer.getText().toString());
                pauseTimer();
                if (userAnswer == correctAnswer){
                    question.setText("Correct Answer");
                    uScore += 10;
                    score.setText("" + uScore);
                }else{
                    ulives -= 1;
                    life.setText(""+ulives);
                    failure.setVisibility(View.VISIBLE);
                    question.setText("Wrong Answer\nFailure");
                }
                answer.setText("");
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checklives()){
                    answer.setText("");
                    resetTimer();
                    gameContinue();
                }else{
                    question.setText("You have lost");
                    score.setText("0");
                    timer.setText("60");
                    life.setText("3");
                    userLost();
                    finish();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void gameContinue(){
        num1 = rnd.nextInt(150);
        num2 = rnd.nextInt(150);
        failure.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        int opr = intent.getIntExtra("opr", 1);

        switch (opr){
            case 1:
                correctAnswer = num1 + num2;
                question.setText(num1 + " + " + num2);
                Log.d("opr", "Addition");
                break;

            case 2:
                correctAnswer = num1 - num2;
                question.setText(num1 + " - " + num2);
                Log.d("opr", "Subtraction");
                break;

            case 3:
                correctAnswer = num1 * num2;
                question.setText(num1 + " x " + num2);
                Log.d("opr", "Multiplication");
                break;

            case 4:
                correctAnswer = num1 / num2;
                question.setText(num1 + " ÷ " + num2);
                Log.d("opr", "Division");
                break;

            default:
                correctAnswer = num1 + num2;
                question.setText(num1 + " + " + num2);
                break;
        }


        startTimer();
    }

    public boolean checklives(){
        return ulives > 0;
    }

    public void  startTimer(){
        ctimer = new CountDownTimer(timeL_left_in_milis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeL_left_in_milis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                pauseTimer();
                resetTimer();
                updateTimer();
                ulives = ulives - 1;
                life.setText(""+ulives);
                question.setText("Time's up");

            }
        }.start();
        timerRunning = true;
    }

    public void updateTimer(){
        int second = (int)(timeL_left_in_milis / 1000) % 60;
        String time = String.format(Locale.getDefault(), "%02d", second);
        timer.setText(time);
    }

    public void pauseTimer(){
        ctimer.cancel();
        timerRunning = false;
    }

    public void resetTimer(){
        timeL_left_in_milis = START_TIMER_IN_MILIS;
        updateTimer();
    }

    public void userLost(){
        Intent intent = new Intent(QuizPage.this, ScorePage.class);
        intent.putExtra("score", uScore);
        startActivity(intent);
        finish();
    }
}