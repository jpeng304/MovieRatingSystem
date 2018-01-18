package com.cpsc411.app4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    //The Custom adapter for the listview
    private Context context;
    private List<Task> tasks;
    public TaskAdapter(Context context, List<Task> tasks){
        this.context=context;
        this.tasks=tasks;
    }
    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        TaskLayout taskLayout = null;
        Task task = tasks.get(position);
        if(view == null){
            taskLayout = new TaskLayout(context, task);
        }
        else{
            taskLayout = (TaskLayout) view;
            taskLayout.setTask(task);
        }
        return taskLayout;
    }
}