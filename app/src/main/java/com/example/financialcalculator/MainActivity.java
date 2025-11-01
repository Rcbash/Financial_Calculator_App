package com.example.financialcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // This is your layout with the nav bar

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // This is the listener for the nav bar
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_calculators) {
                selectedFragment = new EmiCalculatorFragment();
            } else if (itemId == R.id.nav_expenses) {
                // This line is now active and loads your new fragment
                selectedFragment = new ExpenseTrackerFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // Load the default fragment (EMI Calculator) when the app starts
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_calculators);
        }
    }

    // This method loads a fragment into the container
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}