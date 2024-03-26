package com.example.theexpendables

import android.provider.BaseColumns

object ModelsContract {
    object ExpenseEntries : BaseColumns {
        const val TABLE_NAME = "expenses"
        const val COLUMN_NAME_NAME = "expense_name"
        const val COLUMN_NAME_VALUE = "expense_value"
        const val COLUMN_NAME_ACTIVE = "expense_active"
        const val COLUMN_NAME_PAID = "expense_paid"
        const val COLUMN_NAME_GROUP = "expense_group"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ExpenseEntries.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ExpenseEntries.COLUMN_NAME_NAME} TEXT," +
                "${ExpenseEntries.COLUMN_NAME_VALUE} REAL," +
                "${ExpenseEntries.COLUMN_NAME_ACTIVE} INTEGER," +
                "${ExpenseEntries.COLUMN_NAME_PAID} INTEGER," +
                "${ExpenseEntries.COLUMN_NAME_GROUP} TEXT" +
                ";"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ExpenseEntries.TABLE_NAME};"

}