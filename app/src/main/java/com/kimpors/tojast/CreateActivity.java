package com.kimpors.tojast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText task;
    private Button create;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if (id != -1) {
            setTaskText(this, id);
        }

        task = findViewById(R.id.task_edt);
        create = findViewById(R.id.create_btn);
        create.setBackgroundColor(0x22002200);

        task.addTextChangedListener(this);
        create.setOnClickListener(this);
    }

    public void setTaskText(Context context, int id) {
        new Thread(() -> task.setText(
                Repository.getInstance(context)
                        .todoDao().findById(id).getTask()
        )).start();
    }

    @Override
    public void onClick(View view) {
        if (id != -1) {
            Repository.getInstance(view.getContext()).edit(id, task.getText().toString());
        } else {
            Todo todo = new Todo();
            todo.setTask(task.getText().toString());
            Repository.getInstance(view.getContext()).insert(todo);
        }

        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        create.setEnabled(charSequence.length() != 0);

        if (create.isEnabled()) {
            create.setBackgroundColor(0xFF00FF00);
        } else {
            create.setBackgroundColor(0x22002200);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}