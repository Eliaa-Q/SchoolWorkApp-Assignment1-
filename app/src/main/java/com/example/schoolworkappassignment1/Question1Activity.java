package com.example.schoolworkappassignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Question1Activity extends AppCompatActivity {
    private static final String TAG = "Question1Activity";

    private TextView readingq1txt;
    private Spinner readingq1tspinner;

    private TextView readingq2txt;
    private Spinner readingq2spinner;

    private TextView readingq3txt;
    private Spinner readingq3spinner;

    private Button readingsubmitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question1);

        // Initialize views
        readingq1txt = findViewById(R.id.readingq1txt);
        readingq1tspinner = findViewById(R.id.readingq1tspinner);

        readingq2txt = findViewById(R.id.readingq2txt);
        readingq2spinner = findViewById(R.id.readingq2spinner);

        readingq3txt = findViewById(R.id.readingq3txt);
        readingq3spinner = findViewById(R.id.readingq3spinner);

        readingsubmitbtn = findViewById(R.id.readingsubmitbtn);

        // Populate spinners with choices taken from the strings xml
        populateSpinner(readingq1tspinner, getResources().getStringArray(R.array.question1_choices));
        populateSpinner(readingq2spinner, getResources().getStringArray(R.array.question2_choices));
        populateSpinner(readingq3spinner, getResources().getStringArray(R.array.question3_choices));

        // Set onClickListener for readingsubmitbtn
        readingsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int correctAnswers = calculateGrade();
                // we send the mark

                // Create an intent to start Activity2
                Intent intent = new Intent(Question1Activity.this, QuestionsMenuActivity.class);

                // Add the mark as an extra to the intent
                intent.putExtra("MARK_EXTRA", correctAnswers);

                // Start Activity2
                startActivity(intent);

            }
        });
    }

    // Method to populate spinner with choices
    private void populateSpinner(Spinner spinner, String[] choices) {
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choices);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadapter);
    }

    // Method to calculate the grade based on the selected answers
    private int calculateGrade() {
        int correctAnswers = 0;

        if (isAnswerCorrect(readingq1tspinner, getResources().getString(R.string.question1_answer))) {
            correctAnswers++;
        }

        if (isAnswerCorrect(readingq2spinner, getResources().getString(R.string.question2_answer))) {
            correctAnswers++;
        }

        if (isAnswerCorrect(readingq3spinner, getResources().getString(R.string.question3_answer))) {
            correctAnswers++;
        }

        // Print the calculated mark to logcat
        Log.d(TAG, "The calculated mark is: " + correctAnswers);
        return correctAnswers;
    }

    // Method to check if the selected answer in the spinner is correct
    private boolean isAnswerCorrect(Spinner spinner, String correctAnswer) {
        String selectedAnswer = spinner.getSelectedItem().toString();
        return selectedAnswer.equals(correctAnswer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save selected answers in case the activity is paused
        saveSelectedAnswers();
    }

    private void saveSelectedAnswers() {
        // Save selected answers to SharedPreferences or any other storage mechanism if needed
        // For simplicity, let's assume we save them in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("SelectedAnswers", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Question1", readingq1tspinner.getSelectedItem().toString());
        editor.putString("Question2", readingq2spinner.getSelectedItem().toString());
        editor.putString("Question3", readingq3spinner.getSelectedItem().toString());
        editor.apply();
    }
}
