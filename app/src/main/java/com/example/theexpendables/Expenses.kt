package com.example.theexpendables

class Expenses {
    companion object {
        fun validateExpenseValues(
            name: String,
            amount: String,
            group: String,
        ): Boolean {
            return name.isNotEmpty() || amount.isNotEmpty() || group.isNotEmpty()
        }

        fun validateFloatValue(
            amount: String
        ): Boolean {
            try {
                amount.toFloat()
            } catch (exception: NumberFormatException) {
                return false
            }
            return true
        }

        fun totalActiveExpenses(
            expenses: ArrayList<ExpenseDataType>
        ): Pair<Float, Float> {
            var totalExpenses = 0.0
            var totalRemainingExpenses = 0.0
            for (expense in expenses) {
                if (expense.active) {
                    totalExpenses += expense.value
                    if (!expense.paid) {
                        totalRemainingExpenses += expense.value
                    }
                }
            }
            return Pair(totalExpenses.toFloat(), totalRemainingExpenses.toFloat())
        }
    }
}