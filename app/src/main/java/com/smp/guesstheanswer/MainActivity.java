package com.smp.guesstheanswer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int first_val;
    public int sec_val;
    public int third_val;
    public int life = 3;
    public int score = 0;
    private TextView val_1;
    private TextView val_2;
    private TextView val_3;
    private Button button_yes;
    private Button button_no;
    private TextView result_text;
    private TextView score_val;
    private TextView life_val;
    private TextView score_text;
    private TextView life_text;
    private TextView divider;
    private LinearLayout all_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        newValGenerate();
        boolean checkVal = checkVal();
        setUpButtonOnClickListener(checkVal);
        updateUI(); // Update UI with restored values

        if (savedInstanceState != null) {
            // Restore values from savedInstanceState
            life = savedInstanceState.getInt("life");
            score = savedInstanceState.getInt("score");
        }
    }

    private void findViews() {
        val_1 = findViewById(R.id.val_1);
        val_2 = findViewById(R.id.val_2);
        val_3 = findViewById(R.id.val_3);
        button_yes = findViewById(R.id.button_yes);
        button_no = findViewById(R.id.button_no);
        result_text = findViewById(R.id.result_text);
        score_val = findViewById(R.id.score_val);
        life_val = findViewById(R.id.life_val);
        divider = findViewById(R.id.divider);
        life_text = findViewById(R.id.life_text);
        score_text = findViewById(R.id.score_text);
        all_result = findViewById(R.id.all_result);
    }

    @SuppressLint("SetTextI18n")
    private void newValGenerate() {
        Random randomVal = new Random();
        first_val = (randomVal.nextInt(50)) + 1;
        sec_val = (randomVal.nextInt(50)) + 1;
        boolean isSum = randomVal.nextBoolean();

        if (isSum) {
            third_val = first_val + sec_val;
        } else {
            do {
                third_val = (randomVal.nextInt(100)) + 1;
            } while (third_val == first_val + sec_val);
        }
        val_1.setText(Integer.toString(first_val));
        val_2.setText(Integer.toString(sec_val));
        val_3.setText(Integer.toString(third_val));
    }

    private boolean checkVal() {
        int num_1 = Integer.parseInt(val_1.getText().toString());
        int num_2 = Integer.parseInt(val_2.getText().toString());
        int num_3 = Integer.parseInt(val_3.getText().toString());
        int result = num_1 + num_2;
        return result == num_3;
    }

    private void setUpButtonOnClickListener(boolean checkVal) {
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkVal) {
                    rightVal();
                } else {
                    wrongVal();
                }
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkVal) {
                    wrongVal();
                } else {
                    rightVal();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void rightVal() {
        result_text.setText("Right Answer!!!");
        score = score + 1;
        score_val.setText(Integer.toString(score));
        newValGenerate();
        boolean checkVal = checkVal();
        setUpButtonOnClickListener(checkVal);
    }

    @SuppressLint("SetTextI18n")
    private void wrongVal() {
        if (life != 0) {
            result_text.setText("Wrong Answer!!!");
            life = life - 1;
            life_val.setText(Integer.toString(life));
            newValGenerate();
            boolean checkVal = checkVal();
            setUpButtonOnClickListener(checkVal);
        }

        if (life == 0) {
            result_text.setText("Game Over" + "\n" + "Your Score is: " + score);
            score_text.setVisibility(View.GONE);
            score_val.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            life_text.setVisibility(View.GONE);
            life_val.setVisibility(View.GONE);
            button_yes.setEnabled(false);
            button_no.setEnabled(false);
            Button newStartButton = new Button(this);
            newStartButton.setText("Start Again");
            all_result.addView(newStartButton);
            Button newStopButton = new Button(this);
            newStopButton.setText("Exit");
            all_result.addView(newStopButton);
            newStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Finish the current activity
                    startActivity(getIntent()); // Start a new instance of MainActivity
                }
            });
            newStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current values to the outState
        outState.putInt("life", life);
        outState.putInt("score", score);
    }

    // Update UI with current values
    @SuppressLint("SetTextI18n")
    private void updateUI() {
        life_val.setText(Integer.toString(life));
        score_val.setText(Integer.toString(score));
    }


}