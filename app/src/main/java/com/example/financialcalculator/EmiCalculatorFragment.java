package com.example.financialcalculator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Import the UI components
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EmiCalculatorFragment extends Fragment {

    // 1. Declare the UI elements
    private EditText editTextPrincipal;
    private EditText editTextRate;
    private EditText editTextTerm;
    private Button buttonCalculate;
    private TextView textViewResult;

    // Required empty public constructor
    public EmiCalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emi_calculator, container, false);

        // 2. Initialize the UI elements (must use "view.findViewById")
        editTextPrincipal = view.findViewById(R.id.editTextPrincipal);
        editTextRate = view.findViewById(R.id.editTextRate);
        editTextTerm = view.findViewById(R.id.editTextTerm);
        buttonCalculate = view.findViewById(R.id.buttonCalculate);
        textViewResult = view.findViewById(R.id.textViewResult);

        // 3. Set a click listener for the button
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEMI();
            }
        });

        // Return the "view" (the layout)
        return view;
    }

    // 4. Create the Calculation Method (This is exactly the same as before)
    private void calculateEMI() {
        String principalStr = editTextPrincipal.getText().toString();
        String rateStr = editTextRate.getText().toString();
        String termStr = editTextTerm.getText().toString();

        if (principalStr.isEmpty() || rateStr.isEmpty() || termStr.isEmpty()) {
            textViewResult.setText("Please enter all fields");
            return;
        }

        try {
            double p = Double.parseDouble(principalStr);
            double annualRate = Double.parseDouble(rateStr);
            int years = Integer.parseInt(termStr);
            double r = (annualRate / 12) / 100;
            int n = years * 12;
            double emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            String emiResult = String.format("Monthly EMI: ₹%.2f", emi);
            textViewResult.setText(emiResult);
        } catch (NumberFormatException e) {
            textViewResult.setText("Invalid input. Please enter valid numbers.");
        }
    }
}