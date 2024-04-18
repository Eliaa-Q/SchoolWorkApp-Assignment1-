package com.example.schoolworkappassignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionsMenuActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String NAME_KEY = "name";
    private static final String FINAL_MARK_KEY = "final_mark";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView welcometxt;
    private Button act1btn;
    private TextView mark1txt;
    private Button act2btn;
    private TextView mark2txt;
    private Button donebtn;
    private TextView finalresutltxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions_menu);

        // Initialize views
        welcometxt = findViewById(R.id.welcometxt);
        act1btn = findViewById(R.id.act1btn);
        mark1txt = findViewById(R.id.mark1txt);
        act2btn = findViewById(R.id.act2btn);
        mark2txt = findViewById(R.id.mark2txt);
        donebtn = findViewById(R.id.donebtn);
        finalresutltxt = findViewById(R.id.finalresutltxt);

        // Setup SharedPreferences
        setUpSharedPrefs();

        // Get the name from SharedPreferences
        checkPrefs();

        // Set click listener for act1btn
        act1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Question1 Activity
                Intent intent = new Intent(QuestionsMenuActivity.this, Question1Activity.class);
                startActivity(intent);
            }
        });

        // Set click listener for act2btn
        act2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Question2 Activity
                Intent intent = new Intent(QuestionsMenuActivity.this, Question2Activity.class);
                startActivityForResult(intent, 2); // Start for result to receive the mark
            }
        });

        // Set click listener for donebtn
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the sum of the two marks
                int mark1 = Integer.parseInt(mark1txt.getText().toString().split(": ")[1]);
                int mark2 = Integer.parseInt(mark2txt.getText().toString().split(": ")[1]);
                int totalMark = mark1 + mark2;

                // Append the total mark to finalresutltxt
                finalresutltxt.append(" " + totalMark + " out of 5");

                // Save the name and final mark in SharedPreferences
                editor.putString(NAME_KEY, welcometxt.getText().toString());
                editor.putInt(FINAL_MARK_KEY, totalMark);
                editor.apply();
            }
        });

        // Retrieve and display the mark value
        displayMark();
    }

    private void setUpSharedPrefs() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void checkPrefs() {
        String name = sharedPreferences.getString(NAME_KEY, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            // Retrieve the mark value from the intent
            int mark = data.getIntExtra("MARK_EXTRA", 0);

            // Set the mark value in the TextView
            mark2txt.setText("Mark: " + mark);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve and display the mark value
        displayMark();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save current state to SharedPreferences
        editor.putString(NAME_KEY, welcometxt.getText().toString());
        editor.apply();
    }

    private void displayMark() {
        // Retrieve the mark value from the intent
        int mark = getIntent().getIntExtra("MARK_EXTRA", 0);

        // Set the mark value in the TextView
        mark1txt.setText("Mark: " + mark);
    }
}
