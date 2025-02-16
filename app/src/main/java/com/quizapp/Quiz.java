package com.quizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {
    private TextView questionView, questionIndicator;
    private RadioGroup optionsGroup;
    private Button nextButton, prevButton;
    private String[] questions = {
            "In what year did the United States host the FIFA World Cup for the first time?",
            "Which country won the 2018 FIFA World Cup?",
            "Who is the top scorer in FIFA World Cup history?"
    };
    private String[][] options = {
            {"1986", "1994", "2002", "2010"},
            {"France", "Germany", "Brazil", "Argentina"},
            {"Pele", "Messi", "Ronaldo", "Miroslav Klose"}
    };
    private int currentQuestionIndex = 0;
    private int selectedAnswerIndex = -1;  // Track selected option

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionView = findViewById(R.id.questionView);
        questionIndicator = findViewById(R.id.questionIndicator);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        loadQuestion(); // Load first question

        // Handle option selection
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                RadioButton rb = (RadioButton) optionsGroup.getChildAt(i);
                if (rb.getId() == checkedId) {
                    selectedAnswerIndex = i;
                    rb.setBackgroundColor(Color.parseColor("#B0D9B1")); // Green when selected
                } else {
                    rb.setBackgroundColor(Color.WHITE); // Reset others
                }
            }
        });

        // Handle "Next" button
        nextButton.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadQuestion();
            } else {
                Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle "Previous" button
        prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });
    }

    private void loadQuestion() {
        questionView.setText(questions[currentQuestionIndex]);
        questionIndicator.setText((currentQuestionIndex + 1) + "/" + questions.length);

        optionsGroup.removeAllViews();
        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(options[currentQuestionIndex][i]);
            rb.setId(View.generateViewId());
            rb.setPadding(20, 20, 20, 20);
            rb.setTextSize(16);
            rb.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            rb.setBackgroundColor(Color.WHITE);
            rb.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            optionsGroup.addView(rb);
        }

        // Hide "Previous" button on first question
        prevButton.setVisibility(currentQuestionIndex == 0 ? View.GONE : View.VISIBLE);

        // Change "Next" to "Finish" on the last question
        nextButton.setText(currentQuestionIndex == questions.length - 1 ? "Finish" : "Next");
    }
}
