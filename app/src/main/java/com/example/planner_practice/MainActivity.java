package com.example.planner_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private ListView listViewTasks;
    private final String[] filterTask= {"Актуальні", "Виконані", "Застарілі", "Все"};
    private Spinner spinner;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GregorianCalendar gc = new GregorianCalendar();
        todayDate = "'" + gc.get(Calendar.DAY_OF_MONTH) + "." + gc.get(Calendar.MONTH+1) + "." +
            gc.get(Calendar.YEAR) + "'";

        dbManager = new DbManager(this);
        listViewTasks = findViewById(R.id.listViewTasks);
        spinner = findViewById(R.id.spinnerTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();

        TaskAdapter adapterTask = new TaskAdapter(this, R.layout.task_item,
                dbManager.getFromDb(Tasks.STATUS + " = 0"));
        listViewTasks.setAdapter(adapterTask);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterTask);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String filterName = (String)parent.getItemAtPosition(position);
                switch(filterName) {
                    case ("Актуальні"):
                        displayTask(adapterTask, dbManager.getFromDb(Tasks.STATUS + " = 0 AND "
                                + Tasks.DATE + " >= " + todayDate));
                        break;
                    case ("Виконані"):
                        displayTask(adapterTask, dbManager.getFromDb(Tasks.STATUS + " = 1"));
                        break;
                    case ("Застарілі"):
                        displayTask(adapterTask, dbManager.getFromDb(Tasks.STATUS + " = 0 AND "
                                + Tasks.DATE + " < " + todayDate));
                        break;
                    case ("Все"):
                        displayTask(adapterTask, dbManager.getFromDb(null));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void displayTask(TaskAdapter taskAdapter, List<Task> tasks) {
        taskAdapter.clear();
        taskAdapter.addAll(tasks);
        taskAdapter.notifyDataSetChanged();
    }
}