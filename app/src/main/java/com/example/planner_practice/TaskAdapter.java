package com.example.planner_practice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private LayoutInflater inflater;
    private int layout;
    private List<Task> tasks;
    private DbManager dbManager;

    public TaskAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        dbManager = new DbManager(getContext());

        TextView taskInfo = view.findViewById(R.id.taskInfo);
        CheckBox taskStatus = view.findViewById(R.id.taskStatus);
        Button deleteTask = view.findViewById(R.id.deleteTask);

        Task task = tasks.get(position);

        taskInfo.setText(task.getInfo());
        if (task.getStatus() == 1) {
            taskStatus.setChecked(true);
        } else {
            taskStatus.setChecked(false);
        }

        taskStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbManager.openDb();
                if (isChecked) {
                    dbManager.changeStatus(task.getId(), 1);
                } else {
                    dbManager.changeStatus(task.getId(), 0);
                }
                dbManager.closeDb();
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.alertTitleDel)
                        .setMessage(R.string.alertMessageDel)
                        .setCancelable(false)
                        .setPositiveButton(R.string.tvYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbManager.openDb();
                                dbManager.deleteTask(task.getId());
                                dbManager.closeDb();
                                remove(task);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), R.string.toastDelTrue, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(R.string.tvNo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}
