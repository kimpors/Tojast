package com.kimpors.tojast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ThemedSpinnerAdapter;

import com.kimpors.tojast.Adapter.TodoAdapter;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView view;
    private TodoAdapter adapter;
    private List<Todo> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();

        view = findViewById(R.id.view);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this);
        view.setAdapter(adapter);

        Todo task = new Todo();
        task.setTask("Todo home work");
        task.setStatus(false);
        task.setId(1);

        for (int i = 0; i < 5; i++) {
            tasks.add(task);
        }

        adapter.setTasks(tasks);
    }
}