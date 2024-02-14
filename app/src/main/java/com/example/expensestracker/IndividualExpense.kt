package com.example.expensestracker

data class IndividualExpense(
    var expenseName: String,
    var expenseValue: Double,
    var expenseIsActive: Boolean,
    var expenseIsPaid: Boolean,
)
