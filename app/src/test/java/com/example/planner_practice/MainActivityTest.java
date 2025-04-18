package com.example.planner_practice;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.AdapterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    private DbManager dbManager;
    private TaskAdapter taskAdapter;
    private AdapterView<?> parent;
    private View view;
    private final String todayDate = "12.12.2024";
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        dbManager = mock(DbManager.class);
        taskAdapter= mock(TaskAdapter.class);
        parent = mock(AdapterView.class);
        view = mock(View.class);
        mainActivity = mock(MainActivity.class);
    }

    String[] filters = {"Актуальні", "Виконані", "Застарілі", "Все"};
    String[] expectedQueries = {
            Tasks.STATUS + " = 0 AND " + Tasks.DATE + " >= " + todayDate,
            Tasks.STATUS + " = 1",
            Tasks.STATUS + " = 0 AND " + Tasks.DATE + " < " + todayDate,
            null
    };

    @Test
    public void onItemSelectedTest() {
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filterName = (String)parent.getItemAtPosition(position);
                switch(filterName) {
                    case ("Актуальні"):
                        mainActivity.displayTask(taskAdapter, dbManager.getFromDb(expectedQueries[0]));
                        break;
                    case ("Виконані"):
                        mainActivity.displayTask(taskAdapter, dbManager.getFromDb(expectedQueries[1]));
                        break;
                    case ("Застарілі"):
                        mainActivity.displayTask(taskAdapter, dbManager.getFromDb(expectedQueries[2]));
                        break;
                    case ("Все"):
                        mainActivity.displayTask(taskAdapter, dbManager.getFromDb(expectedQueries[3]));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        for (int i = 0; i < filters.length; i++) {
            when(parent.getItemAtPosition(i)).thenReturn(filters[i]);
            itemSelectedListener.onItemSelected(parent, view, i, 0);
            verify(dbManager).getFromDb(expectedQueries[i]);
        }
    }
}
