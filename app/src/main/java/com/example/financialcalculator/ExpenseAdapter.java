package com.example.financialcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * This Adapter connects our List of Expense data to the RecyclerView.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    // A list to hold all our expenses
    private List<Expense> expenses = new ArrayList<>();

    // This method is called by the RecyclerView to create a new "row"
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // We use the item_expense.xml layout we created earlier
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    // This method is called by the RecyclerView to show data at a specific row
    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense currentExpense = expenses.get(position);

        // We find the TextViews in our ViewHolder and set the text
        holder.tvExpenseName.setText(currentExpense.name);
        String amountText = String.format("₹%.2f", currentExpense.amount);
        holder.tvExpenseAmount.setText(amountText);
    }

    // This tells the RecyclerView how many items are in our list
    @Override
    public int getItemCount() {
        return expenses.size();
    }

    // A helper method to update the adapter's data from our fragment
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged(); // This tells the RecyclerView to refresh the list
    }

    /**
     * The ViewHolder. This class holds the references to the UI elements
     * in our item_expense.xml layout (the two TextViews).
     */
    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvExpenseName;
        private final TextView tvExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseName = itemView.findViewById(R.id.tvExpenseName);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
        }
    }
}