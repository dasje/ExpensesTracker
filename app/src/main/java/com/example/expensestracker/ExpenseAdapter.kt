package com.example.expensestracker

import android.app.usage.UsageEvents.Event
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.MainActivity

class ExpenseAdapter(
    private val expenses: MutableList<IndividualExpense>
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var listener : ((item : DataClass) -> Unit)? = null

    fun setOnItemClickListener(listener: (item: DataClass) -> unit) {
        this.listener = listener
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val curExpenseName : TextView = itemView.findViewById(R.id.tvExpenseName)
        val curExpenseValue: TextView = itemView.findViewById(R.id.etDecExpenseValue)
        val curExpenseSwitch : Switch = itemView.findViewById(R.id.s1ActiveExpense).setOnClickListener { listener?.invoke(data[adapterPosition]) }
        val curExpenseIsPaidCheck : CheckBox = itemView.findViewById(R.id.cbPaidExpense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.individual_expense,
                parent,
                false,
            )
        return ExpenseViewHolder(view)
    }

    fun updateEvent(event: Event) {
        println(event.toString())

    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentExpense = expenses[position]
        holder.apply {
            curExpenseName.text = currentExpense.expenseName
            curExpenseValue.text = currentExpense.expenseValue.toString()
            curExpenseSwitch.isChecked = currentExpense.expenseIsActive
            curExpenseIsPaidCheck.isChecked = currentExpense.expenseIsPaid
            toggleStrikeThrough(curExpenseName, currentExpense.expenseIsPaid)
            curExpenseIsPaidCheck.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(curExpenseName, isChecked)
                currentExpense.expenseIsPaid = !currentExpense.expenseIsPaid
            }
        }
        holder.curExpenseSwitch.setOnClickListener { v ->
            println(v)
            MainActivity.
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun addExpense(expense: IndividualExpense) {
        expenses.add(expense)
        notifyItemInserted(expenses.size - 1)
        println(expenses)
    }

    private fun toggleStrikeThrough(expenseName: TextView, isChecked: Boolean) {
        if (isChecked) {
            expenseName.paintFlags = expenseName.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            expenseName.paintFlags = expenseName.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun sumExpenses() : Double {
        var total : Double = 0.0
        for (expense in expenses) {
            if (expense.expenseIsActive) {
                total += expense.expenseValue
            }
        }
        return total
    }

    fun sumActiveToPayExpenses() : Double {
        var total : Double = 0.0
        for (expense in expenses) {
            if (expense.expenseIsActive and !expense.expenseIsPaid) {
                total += expense.expenseValue
            }
        }
        return total
    }
}