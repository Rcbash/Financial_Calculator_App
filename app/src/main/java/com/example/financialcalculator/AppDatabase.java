package com.example.financialcalculator;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao(); // Room will generate the code for this

    private static volatile AppDatabase INSTANCE;

    // This is a singleton pattern. It ensures only one database instance is ever created.
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "expense_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}