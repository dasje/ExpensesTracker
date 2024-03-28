package com.example.theexpendables

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class AddExpenseDialog : DialogFragment() {

    companion object {

        const val TAG = "AddExpenseDialog"

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
//            val builder = CustomDialog.Builder(it)
//            builder.setMessage("Start game")
//                .setPositiveButton("Add") { dialog, id ->
//                    // START THE GAME!
//                }
//                .setNegativeButton("Cancel") { dialog, id ->
//                    // User cancelled the dialog.
//                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

class OldXmlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_expense)

        AddExpenseDialog().show(supportFragmentManager, "GAME_DIALOG")
    }
}