package com.example.financialcalculator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseTrackerFragment extends Fragment {

    // UI Elements
    private TextInputEditText etExpenseName;
    private TextInputEditText etExpenseAmount;
    private Button btnAddExpense;
    private RecyclerView recyclerViewExpenses;

    // Room Database components
    private AppDatabase db;
    private ExpenseDao expenseDao;
    private ExpenseAdapter expenseAdapter;

    // We need a background thread to run database queries
    private ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    public ExpenseTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_tracker, container, false);

        // --- Initialize Database ---
        db = AppDatabase.getDatabase(getContext());
        expenseDao = db.expenseDao();

        // --- Initialize UI Elements ---
        etExpenseName = view.findViewById(R.id.etExpenseName);
        etExpenseAmount = view.findViewById(R.id.etExpenseAmount);
        btnAddExpense = view.findViewById(R.id.btnAddExpense);
        recyclerViewExpenses = view.findViewById(R.id.recyclerViewExpenses);

        // --- Setup RecyclerView ---
        setupRecyclerView();

        // --- Set Click Listener for "Add" Button ---
        btnAddExpense.setOnClickListener(v -> addExpense());

        // --- Load initial data ---
        loadAllExpenses();

        return view;
    }

    private void setupRecyclerView() {
        expenseAdapter = new ExpenseAdapter();
        recyclerViewExpenses.setAdapter(expenseAdapter);
        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void addExpense() {
        String name = etExpenseName.getText().toString();
        String amountStr = etExpenseAmount.getText().toString();

        if (name.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            Expense newExpense = new Expense(name, amount);

            // Run database insert on a background thread
            databaseExecutor.execute(() -> {
                expenseDao.insert(newExpense);
                // After inserting, reload the expenses on the UI thread
                getActivity().runOnUiThread(() -> loadAllExpenses());
            });

            // Clear input fields
            etExpenseName.setText("");
            etExpenseAmount.setText("");
            Toast.makeText(getContext(), "Expense added!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAllExpenses() {
        // Run database query on a background thread
        databaseExecutor.execute(() -> {
            List<Expense> expenses = expenseDao.getAllExpenses();
            // Update the RecyclerView on the UI thread
            getActivity().runOnUiThread(() -> {
                expenseAdapter.setExpenses(expenses);
            });
        });
    }
}