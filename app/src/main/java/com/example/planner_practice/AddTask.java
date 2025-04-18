package com.example.planner_practice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
    private EditText name;
    private EditText difficulty;
    private EditText time;
    private EditText date;
    private EditText priority;
    private EditText type;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        dbManager = new DbManager(this);
        name = findViewById(R.id.editTextAddName);
        difficulty = findViewById(R.id.editTextAddDifficulty);
        time = findViewById(R.id.editTextAddTime);
        date = findViewById(R.id.editTextAddDate);
        priority = findViewById(R.id.editTextAddPriority);
        type = findViewById(R.id.editTextAddType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public void cancelClick(View view) {
        finish();
    }

    public void saveClick(View view) {
        try {
            dbManager.insertToDb(name.getText().toString(),
                    Integer.parseInt(difficulty.getText().toString()),
                    Integer.parseInt(time.getText().toString()),
                    date.getText().toString(),
                    Integer.parseInt(priority.getText().toString()),
                    type.getText().toString());
            Toast.makeText(this, R.string.toastAddTrue, Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, R.string.toastAddFalse, Toast.LENGTH_LONG).show();
        }
    }
}