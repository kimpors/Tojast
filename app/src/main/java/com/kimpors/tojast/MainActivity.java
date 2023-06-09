package com.kimpors.tojast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.kimpors.tojast.Adapter.TodoAdapter;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Observer<List<Todo>> {
    private TodoAdapter adapter;
    private LiveData<List<Todo>> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView view = findViewById(R.id.view);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this);
        view.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(this);

        tasks =  Repository.getInstance(this)
                .todoDao().getAll();
        tasks.observe(this, this);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        touchHelper.attachToRecyclerView(view);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChanged(List<Todo> todos) {
        adapter.setTasks(tasks.getValue());
    }
}