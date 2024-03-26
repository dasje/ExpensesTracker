package com.example.theexpendables

data class ExpenseDataType(
    val name: String,
    val value: Float,
    val active: Boolean,
    val paid: Boolean,
    val group: String
)
