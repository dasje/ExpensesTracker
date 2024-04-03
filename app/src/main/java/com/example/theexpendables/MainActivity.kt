package com.example.theexpendables

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    companion object {
        var data : MutableList<ExpenseDataType> = ArrayList()
        private val adapterListener = object : ExpenseAdapter.AdapterListener {
            override fun iconExpenseActiveOnClick(v: View?, expenseName: String, checkedValue: Boolean, position: Int) {
                Log.d("LISTENER", "iconExpenseActiveOnClick value is $checkedValue")
                var db = v?.let { DBHandler(v.context) }
                var updated = db?.updateExpenseActiveState(expenseName, checkedValue)
                Log.d("LISTENER", "iconExpenseActiveOnClick updated value $updated")
                data.clear()
                if (v != null) {
                    updateDataList(v.context)
                }

            }

            override fun iconExpensePaidOnClick(v: View?, position: Int) {
                Log.d("LISTENER", "iconExpensePaidOnClick at position $position")
            }

        }
        val adapter = ExpenseAdapter(data, adapterListener)

        @SuppressLint("NotifyDataSetChanged")
        private fun updateDataList(context: Context) {
            var db = DBHandler(context)
            var newData = db.readAllFromDB()
            data.clear()
            data.addAll(0, newData.toMutableList())
            adapter.notifyDataSetChanged()
        }
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

        expenseRecycler.setOnClickListener(View.OnClickListener { v ->
            Log.i("ExpenseRecycler", "Recycler clicked")
        })

        expenseRecycler.layoutManager
        expenseRecycler.layoutManager = LinearLayoutManager(this)

        expenseRecycler.adapter = adapter

        updateDataList(this)
    }

    override fun onResume() {
        super.onResume()
        updateDataList(this)
    }

    fun onAddNewExpenseButtonClick(v: View) {
        val newExpenseIntent = Intent(this, AddNewExpenseActivity::class.java)
        startActivity(newExpenseIntent)

    }
}