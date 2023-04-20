package com.kimpors.tojast.Repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kimpors.tojast.Model.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class Repository extends RoomDatabase {
    public abstract TodoDao todoDao();

    private static volatile Repository instance;

    public static Repository getInstance(Context context) {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            Repository.class, "Todo.db").build();
                }
            }
        }

        return instance;
    }

    public void insert(Todo todo) {
        new Thread(() -> todoDao().insertAll(todo)).start();
    }

    public void delete(int id) {
        new Thread(() -> todoDao().delete(todoDao().findById(id))).start();
    }

    public void edit(int id, boolean status) {
        new Thread(() -> {
            Todo todo = todoDao().findById(id);
            todo.setStatus(status);
            todoDao().update(todo);
        }).start();
    }

    public void edit(int id, String text) {
        new Thread(() -> {
            Todo todo = todoDao().findById(id);
            todo.setTask(text);
            todoDao().update(todo);
        }).start();
    }
}
