package com.example.theexpendables

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ExpenseAdapter (private val mList: List<ExpenseDataType>, private val listener: AdapterListener) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    interface AdapterListener {
        fun iconExpenseActiveOnClick(v: View?, position: Int)
        fun iconExpensePaidOnClick(v: View?, position: Int)
    }

    companion object {
        lateinit var onClickListener: AdapterListener
    }

    init {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(com.example.theexpendables.R.layout.single_expense, parent, false)

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

        val expenseName: TextView = itemView.findViewById(com.example.theexpendables.R.id.textExpenseListName)
        val expenseAmount: TextView = itemView.findViewById(com.example.theexpendables.R.id.textExpenseListAmount)
        val expenseActive: CheckBox = itemView.findViewById(com.example.theexpendables.R.id.checkSelectActive)
        val expensePaid: CheckBox = itemView.findViewById(com.example.theexpendables.R.id.checkSelectPaid)

        init {
            val expenseName: TextView = itemView.findViewById(com.example.theexpendables.R.id.textExpenseListName)
            val expenseAmount: TextView = itemView.findViewById(com.example.theexpendables.R.id.textExpenseListAmount)
            val expenseActive: CheckBox = itemView.findViewById(com.example.theexpendables.R.id.checkSelectActive)
            val expensePaid: CheckBox = itemView.findViewById(com.example.theexpendables.R.id.checkSelectPaid)

            expenseActive.setOnClickListener { v ->
                onClickListener.iconExpenseActiveOnClick(
                    v,
                    getAdapterPosition()
                )
            }
            expensePaid.setOnClickListener { v ->
                onClickListener.iconExpensePaidOnClick(
                    v,
                    getAdapterPosition()
                )
            }
        }
    }

}