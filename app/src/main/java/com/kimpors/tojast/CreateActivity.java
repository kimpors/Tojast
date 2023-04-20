package com.kimpors.tojast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kimpors.tojast.Adapter.TodoAdapter;
import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;
import com.kimpors.tojast.Repository.TodoDao;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText task;
    private Button create;
    private  Todo todo;
    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        todo = new Todo();

        task = findViewById(R.id.task_edt);
        create = findViewById(R.id.create_btn);
        create.setBackgroundColor(0x22002200);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if(id != -1)
        {
            setTaskText(this, id);
        }

        task.addTextChangedListener(this);
        create.setOnClickListener(this);
    }

    public void insert(Context context, Todo todo)
    {
        new Thread(() -> Repository.getInstance(context).todoDao().insertAll(todo)).start();
    }

    public void setTaskText(Context context, int id)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                todo = Repository.getInstance(context).todoDao().findById(id);
                task.setText(todo.getTask());
            }
        }).start();
    }
    public void edit(Context context, int id, String text)
    {
        new Thread(() ->
        {
            TodoDao todoDao = Repository.getInstance(context).todoDao();
            Todo item = todoDao.findById(id);
            item.setTask(text);
            todoDao.update(item);
        }).start();
    }

    @Override
    public void onClick(View view) {

        Todo todo = new Todo();
        todo.setTask(task.getText().toString());

        if(id != -1)
        {
            edit(this, id, task.getText().toString());
        }
        else {
            insert(this, todo);

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