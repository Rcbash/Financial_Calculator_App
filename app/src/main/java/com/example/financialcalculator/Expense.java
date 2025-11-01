package com.example.financialcalculator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses") // This creates a table named "expenses"
public class Expense {

    @PrimaryKey(autoGenerate = true) // Makes each ID unique
    public int id;

    public String name;
    public double amount;

    // We need a constructor for Room
    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}