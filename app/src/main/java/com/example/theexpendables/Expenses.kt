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
    }
}