package com.kimpors.tojast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kimpors.tojast.Adapter.TodoAdapter;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Observer<List<Todo>> {

    private RecyclerView view;
    private FloatingActionButton fab;
    private TodoAdapter adapter;
    private LiveData<List<Todo>> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this);
        view.setAdapter(adapter);

        fab = findViewById(R.id.add);
        fab.setOnClickListener(this);

        tasks =  Repository.getInstance(this)
                .todoDao().getAll();
        tasks.observe(this, this::onChanged);
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