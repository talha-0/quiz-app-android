package com.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {
    private TextView scoreText;
    private Button shareButton, exitButton;
    private int score;
    private int totalQuestions;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        scoreText = findViewById(R.id.scoreText);
        shareButton = findViewById(R.id.shareButton);
        exitButton = findViewById(R.id.exitButton);

        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE", 0);
        totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 10);
        name = intent.getStringExtra("USERNAME");

        // Set the score text
        scoreText.setText(name + ", you scored " + score + "/" + totalQuestions);

        // Share Button Click Listener
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareScore();
            }
        });

        // Exit Button Click Listener
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes all activities and exits the app
            }
        });
    }

    private void shareScore() {
        String shareText = name + " scored " + score + "/" + totalQuestions + " in QuizKhelo! 🎉";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Share your score via"));
    }
}