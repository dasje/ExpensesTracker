package com.example.theexpendables

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter (private val mList: List<ExpenseDataType>) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    companion object {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_expense, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        // sets the text to the textview from our itemHolder class
        holder.expenseName.text = itemsViewModel.name
        holder.expenseAmount.text = itemsViewModel.value.toString()
        holder.expenseActive.isChecked = itemsViewModel.active
        holder.expensePaid.isChecked = itemsViewModel.paid

//        holder.expenseActive.setOnClickListener(View.OnClickListener { view ->
//            var db = DBHandler(ExpenseAdapter)
//            db.updateDB()
//        })
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.textExpenseListName)
        val expenseAmount: TextView = itemView.findViewById(R.id.textExpenseListAmount)
        val expenseActive: CheckBox = itemView.findViewById(R.id.checkSelectActive)
        val expensePaid: CheckBox = itemView.findViewById(R.id.checkSelectPaid)
    }
}