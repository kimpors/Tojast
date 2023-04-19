package com.kimpors.tojast.Repository;

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
    List<Todo> getAll();
    @Update
    void update(Todo... todoList);
    @Insert
    void insertAll(Todo... todoList);
    @Delete
    void delete(Todo todo);
}
