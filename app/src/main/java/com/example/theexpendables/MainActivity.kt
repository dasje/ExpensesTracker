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
        expenseRecycler.layoutManager = LinearLayoutManager(this)
        var data = ArrayList<ExpenseDataType>()
        var db = DBHandler(this)
        data = db.readAllFromDB()

        val adapter = ExpenseAdapter(data)
        expenseRecycler.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

//        var db = DBHandler(this)
//
//        data = db.readAllFromDB()
//        for (i in updatedList
//        ) {
//            Log.i("DB", i.toString())
//        }
    }

    fun onAddNewExpenseButtonClick(v: View) {
        val newExpenseIntent = Intent(this, AddNewExpenseActivity::class.java)
        startActivity(newExpenseIntent)

    }
}