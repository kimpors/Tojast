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

import com.kimpors.tojast.Model.Todo;
import com.kimpors.tojast.Repository.Repository;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText task;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        task = findViewById(R.id.task_edt);
        create = findViewById(R.id.create_btn);
        create.setBackgroundColor(0x22002200);


        task.addTextChangedListener(this);
        create.setOnClickListener(this);
    }

    public void insert(Context context, Todo todo)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Repository.getInstance(context).todoDao().insertAll(todo);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {

        Todo todo = new Todo();
        todo.setTask(task.getText().toString());

        insert(this, todo);
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