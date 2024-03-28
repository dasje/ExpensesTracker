package com.example.theexpendables

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val expenseRecycler = findViewById<RecyclerView>(R.id.expenseRecycler)
        expenseRecycler.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ExpenseDataType>()
        // TODO fetch data from db

        val adapter = ExpenseAdapter(data)
        expenseRecycler.adapter = adapter

//        val addExpenseDialog = AddExpenseDialog()
//        addExpenseDialog.show(AddExpenseDialog, "x")

        val newExpenseButton = findViewById<ImageButton>(R.id.addExpenseButton)
        newExpenseButton?.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Adding a new expense",
                Toast.LENGTH_SHORT
            ).show()
            AddExpenseDialog().show(supportFragmentManager, AddExpenseDialog.TAG)
        }
    }
}