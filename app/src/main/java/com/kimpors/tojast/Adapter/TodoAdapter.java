package com.kimpors.tojast.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.kimpors.tojast.MainActivity;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.R;
import com.kimpors.tojast.Repository.Repository;
import com.kimpors.tojast.Repository.TodoDao;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>
{
    private List<Todo> tasks;
    private MainActivity activity;

    public TodoAdapter(MainActivity activity)
    {
        this.activity = activity;
    }

    public Context getContext(){return activity;}

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Todo task = get(position);
        holder.task.setText(task.getTask());
        holder.task.setChecked(task.getStatus());

        holder.task.setOnCheckedChangeListener((compoundButton, b) ->
        {
            edit(getContext(), position, b);
        });
    }
    public int getItemCount()
    {
        if(tasks == null)
            return 0;

        return tasks.size();
    }

    public void setTasks(List<Todo> tasks)
    {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder
    {
        CheckBox task;

        public ViewHolder(View view)
        {
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }

    public Todo get(int position)
    {
        if(position < 0 || position > getItemCount())
            return tasks.get(0);

        return tasks.get(position);
    }

    public void delete(Context context, int position)
    {
        new Thread(() ->
        {
            TodoDao todoDao = Repository.getInstance(context).todoDao();
            Todo item = tasks.get(position);
            todoDao.delete(item);
        }).start();
    }

//    public void edit(Context context, int position, String text)
//    {
//        new Thread(() ->
//        {
//            TodoDao todoDao = Repository.getInstance(context).todoDao();
//            Todo item = tasks.get(position);
//            item.setTask(text);
//            todoDao.update(item);
//        }).start();
//    }

    public void edit(Context context, int position, boolean status)
    {
        new Thread(() ->
        {
            TodoDao todoDao = Repository.getInstance(context).todoDao();
            Todo item = tasks.get(position);
            item.setStatus(status);
            todoDao.update(item);
        }).start();
    }
}
