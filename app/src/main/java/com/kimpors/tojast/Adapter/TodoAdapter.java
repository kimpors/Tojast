package com.kimpors.tojast.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.kimpors.tojast.MainActivity;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todoList;
    private MainActivity activity;

    public TodoAdapter(MainActivity activity)
    {
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder holder, int positino)
    {
        Todo item = todoList.get(positino);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.getStatus());
    }

    public int getItemCount()
    {
        return todoList.size();
    }

    public void setTasks(List<Todo> todoList)
    {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        CheckBox task;

        public ViewHolder(View view)
        {
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }
}
