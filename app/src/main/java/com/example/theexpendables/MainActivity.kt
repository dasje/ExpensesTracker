package com.example.theexpendables

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    companion object {
        var data : MutableList<ExpenseDataType> = ArrayList()
        val adapter = ExpenseAdapter(data)
    }

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
        expenseRecycler.layoutManager
        expenseRecycler.layoutManager = LinearLayoutManager(this)

        expenseRecycler.adapter = adapter

        var db = DBHandler(this)
        var newData = db.readAllFromDB()
        data.clear()
        data.addAll(0, newData.toMutableList())
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        var db = DBHandler(this)
        var newData = db.readAllFromDB()
        data.clear()
        data.addAll(0, newData.toMutableList())
        adapter.notifyDataSetChanged()
    }

    fun onAddNewExpenseButtonClick(v: View) {
        val newExpenseIntent = Intent(this, AddNewExpenseActivity::class.java)
        startActivity(newExpenseIntent)

    }
}