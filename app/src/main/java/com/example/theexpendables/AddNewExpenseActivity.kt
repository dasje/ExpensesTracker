package com.example.theexpendables

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddNewExpenseActivity : AppCompatActivity() {

    companion object {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_expense)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun addExpense(view: View) {
        var newName : EditText = findViewById(R.id.addExpensePopupLabelNewExpenseNameValue)
        var newAmount : EditText = findViewById(R.id.addExpensePopupLabelNewExpenseAmountValue)
        var newGroup : EditText = findViewById(R.id.addExpensePopupLabelNewExpenseGroupValue)
//
        if (Expenses.validateExpenseValues(
                newName.text.toString(),
                newAmount.text.toString(),
                newGroup.text.toString()
            ) && Expenses.validateFloatValue(
                newAmount.text.toString()
            )) {
            // TODO do stuff
            var toast = Toast.makeText(this, "Valid input", Toast.LENGTH_SHORT)
            toast.show()

            var db = DBHandler(this)
            Log.i("DB", "Inserting to DB")
            db.insertToDB(
                newName.text.toString(),
                newAmount.text.toString().toFloat(),
                active=true,
                paid=false,
                newGroup.text.toString()
            )
            Log.i("DB", "Inserted to DB")

            finish()
        } else {
            var toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun exitAddExpense(view: View) {
        finish()
    }
}