package com.example.schoolworkappassignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText nametxt;
    private Button gobtn;
    private String name;
    private TextView notetxt;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nametxt = findViewById(R.id.nametxt);
        gobtn = findViewById(R.id.gobtn);
        notetxt = findViewById(R.id.notetxt);
        notetxt.setVisibility(View.INVISIBLE);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve name from SharedPreferences
        name = sharedPreferences.getString("NAME", "");
        nametxt.setText(name);

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notetxt.setVisibility(View.INVISIBLE);

                if (nametxt.getText().toString().isEmpty() || nametxt.getText().toString().contentEquals("Full Name")) {
                    notetxt.setVisibility(View.VISIBLE);
                } else {
                    name = nametxt.getText().toString();
                    Log.d("TAG", "name is:" + name); // Debug log

                    // Save name to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("NAME", name);
                    editor.apply();

                    // Create an intent to start the QuestionsMenuActivity
                    Intent intent = new Intent(MainActivity.this, QuestionsMenuActivity.class);
                    intent.putExtra("NAME_EXTRA", name); // Pass the name as an extra with key "NAME_EXTRA"
                    startActivity(intent); // Start the QuestionsMenuActivity
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save name to SharedPreferences when activity is paused
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME", name);
        editor.apply();
    }
}
