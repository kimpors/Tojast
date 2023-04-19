package com.kimpors.tojast.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kimpors.tojast.Model.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAll();
    @Query("SELECT * FROM todo WHERE id = :id")
    Todo findById(int id);
    @Update
    void update(Todo... todoList);
    @Insert
    void insertAll(Todo... todoList);
    @Delete
    void delete(Todo todo);
}
