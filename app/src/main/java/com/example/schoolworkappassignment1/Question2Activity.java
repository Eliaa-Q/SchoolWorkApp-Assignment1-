package com.example.schoolworkappassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Question2Activity extends AppCompatActivity {

    private ListView listView;
    private Button submitBtn;
    private ArrayList<String> selectedItems = new ArrayList<>();
    String[] items = {
            "I have visited Paris twice.",
            "She has already finished her homework.",
            "They have lived in that house for ten years.",
            "I have went to the store yesterday.",
            "He has ate lunch already."
    };
    private int mark = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_queston2);

        // Initialize views
        listView = findViewById(R.id.listview);
        submitBtn = findViewById(R.id.submitq2btn);

        // Populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // ListView item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) listView.getItemAtPosition(position);
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                    listView.setItemChecked(position, false);
                } else if (selectedItems.size() < 2) {
                    selectedItems.add(selectedItem);
                } else {
                    listView.setItemChecked(position, false);
                }
            }
        });

        // Submit button click listener
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGrade();
                sendMarkBack();
            }
        });
    }

    private void calculateGrade() {
        mark = 0;
        // Check selected items and calculate mark
        for (String item : selectedItems) {
            if (isIncorrect(item)) {
                mark++;
            }
        }
    }

    private boolean isIncorrect(String item) {
        // Define a list of incorrect items
        String[] incorrectItems = {
                "I have went to the store yesterday.",
                "He has ate lunch already."
        };

        // Check if the selected item is in the list of incorrect items
        for (String incorrectItem : incorrectItems) {
            if (item.equals(incorrectItem)) {
                return true; // Return true if the item is incorrect
            }
        }
        return false; // Return false if the item is not incorrect
    }

    private void sendMarkBack() {
        Intent intent = new Intent();
        intent.putExtra("MARK_EXTRA", mark);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save selected items in case the activity is paused
        saveSelectedItems();
    }

    private void saveSelectedItems() {
        // Save selected items to SharedPreferences or any other storage mechanism if needed
        // For simplicity, let's assume we save them in SharedPreferences
        StringBuilder selectedItemsString = new StringBuilder();
        for (String item : selectedItems) {
            selectedItemsString.append(item).append(",");
        }

        // Remove the last comma
        if (selectedItemsString.length() > 0) {
            selectedItemsString.deleteCharAt(selectedItemsString.length() - 1);
        }

        // Save the selected items string in SharedPreferences
        getSharedPreferences("SelectedItems", MODE_PRIVATE)
                .edit()
                .putString("SelectedItems", selectedItemsString.toString())
                .apply();
    }
}
