package com.example.expensestracker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var expenseAdapter: ExpenseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RV expenses handling
        var rvExpenses : RecyclerView = findViewById(R.id.rvExpenses)
        rvExpenses.layoutManager = LinearLayoutManager(this)
        expenseAdapter = ExpenseAdapter(mutableListOf())
        rvExpenses.adapter = expenseAdapter


        // Button handling
        var btnAddNewExpense : Button = findViewById(R.id.btnAddNewExpense)
        var etNewExpenseName : EditText = findViewById(R.id.etNewExpenseName)
        var etDec2NewExpenseAmount : EditText = findViewById(R.id.etDec2NewExpenseAmount)
        btnAddNewExpense.setOnClickListener {
            if (
                etNewExpenseName.text.isNotEmpty() and
                (etNewExpenseName.text.toString() != "New expense name") and
                (etDec2NewExpenseAmount.text.isNotEmpty())
            ) {
                val expenseName = etNewExpenseName.text.toString()
                val expenseAmount = etDec2NewExpenseAmount.text.toString()
                val expenseToAdd = IndividualExpense(
                    expenseName = expenseName,
                    expenseValue = expenseAmount.toDouble(),
                    expenseIsActive = true,
                    expenseIsPaid = false
                )
                expenseAdapter.addExpense(expenseToAdd)
                println(expenseAdapter.getprivateitems())
                etNewExpenseName.text.clear()
                etDec2NewExpenseAmount.text.clear()
                calculateTotals(expenseAdapter)
            } else {
                var invalidInputMessage = Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG)
                invalidInputMessage.show()
            }
        }

    }

    private fun calculateTotals(expenseAdapter: ExpenseAdapter) {
        // Calculations
        var incomeValue : EditText = findViewById<EditText>(R.id.etDec2IncomeValue)
        var periodExpensesTotal : TextView = findViewById<TextView>(R.id.etDec2PeriodExpenses)
        var remainingExpensesToPay : TextView = findViewById<TextView>(
            R.id.etDec2PeriodExpensesRemaining)
        var remainingBudget : TextView = findViewById<TextView>(R.id.etDec2BudgetRemaining)

        periodExpensesTotal.text = expenseAdapter.sumExpenses().toString()
        remainingExpensesToPay.text = expenseAdapter.sumActiveToPayExpenses().toString()
        remainingBudget.text = (
                incomeValue.text.toString().toDouble() -
                        remainingExpensesToPay.text.toString().toDouble()
                ).toString()
    }
}