package com.example.financialcalculator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Query("SELECT * FROM expenses ORDER BY id DESC")
    List<Expense> getAllExpenses();
}