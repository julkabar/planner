package com.example.planner_practice;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DbManagerTest {

    @Mock
    SQLiteDatabase mockDb;

    @Mock
    Cursor mockCursor;

    @Mock
    Context mockContext;

    @InjectMocks
    DbManager dbManager = new DbManager(mockContext);

    @Test
    public void getFromDbFoundDataTest() {

        Mockito.when(mockDb.query(eq(Tasks.TABLE_NAME), isNull(), eq(" "), isNull(), isNull(), isNull(),
                eq(Tasks.DATE + " ASC, " + Tasks.PRIORITY + " ASC")
                )).thenReturn(mockCursor);

        Mockito.when(mockCursor.moveToNext())
                .thenReturn(true)
                .thenReturn(false);

        Mockito.when(mockCursor.getInt(mockCursor.getColumnIndex(Tasks._ID))).thenReturn(1).
                thenReturn(2).thenReturn(120).thenReturn(3).thenReturn(1);
        Mockito.when(mockCursor.getString(mockCursor.getColumnIndex(Tasks.NAME))).thenReturn("Test").
                thenReturn("12.12.2024").thenReturn("Work");

        List<Task> tasks = dbManager.getFromDb(" ");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertEquals(1, task.getId());
        assertEquals("Test", task.getName());
        assertEquals(2, task.getDifficulty());
        assertEquals(120, task.getTime());
        assertEquals("12.12.2024", task.getDate());
        assertEquals(3, task.getPriority());
        assertEquals("Work", task.getType());
        assertEquals(1, task.getStatus());

        verify(mockCursor).close();
    }
}
