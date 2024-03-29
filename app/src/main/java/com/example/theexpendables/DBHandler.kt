package com.example.theexpendables

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log

class DBHandler (context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertToDB(name: String, value: Float, active: Boolean, paid: Boolean, group: String): Long? {
        // TODO Check expense doesn't already exist

        val db = dbHelper.writableDatabase

        var activeInt = 0
        if (active) {
            activeInt = 1
        }
        var paidInt = 0
        if (paid) {
            paidInt = 1
        }

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(ModelsContract.ExpenseEntries.COLUMN_NAME_NAME, name)
            put(ModelsContract.ExpenseEntries.COLUMN_NAME_VALUE, value)
            put(ModelsContract.ExpenseEntries.COLUMN_NAME_ACTIVE, activeInt)
            put(ModelsContract.ExpenseEntries.COLUMN_NAME_PAID, paidInt)
            put(ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP, group)
        }

        // Insert the new row, returning the primary key value of the new row

        val newRowId = db?.insert(ModelsContract.ExpenseEntries.TABLE_NAME, null, values)

        db?.close()

        return newRowId
    }

    fun readAllFromDB(): ArrayList<ExpenseDataType> {
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP,
            ModelsContract.ExpenseEntries.COLUMN_NAME_NAME,
            ModelsContract.ExpenseEntries.COLUMN_NAME_VALUE,
            ModelsContract.ExpenseEntries.COLUMN_NAME_ACTIVE,
            ModelsContract.ExpenseEntries.COLUMN_NAME_PAID
        )

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${ModelsContract.ExpenseEntries.COLUMN_NAME_NAME} ASC"

        val cursor = db.query(
            ModelsContract.ExpenseEntries.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        Log.i("DB", "Cursor complete")

        val items = arrayListOf<ExpenseDataType>()
        while (cursor.moveToNext()) {
            var colInd = cursor.getColumnIndex(ModelsContract.ExpenseEntries.COLUMN_NAME_NAME)
            val itemName = cursor.getString(colInd)
            colInd = cursor.getColumnIndex(ModelsContract.ExpenseEntries.COLUMN_NAME_VALUE)
            val itemValue = cursor.getString(colInd)
            colInd = cursor.getColumnIndex(ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP)
            val itemGroup = cursor.getString(colInd)
            colInd = cursor.getColumnIndex(ModelsContract.ExpenseEntries.COLUMN_NAME_ACTIVE)
            val itemActive = cursor.getString(colInd)
            colInd = cursor.getColumnIndex(ModelsContract.ExpenseEntries.COLUMN_NAME_PAID)
            val itemPaid = cursor.getString(colInd)
            items.add(ExpenseDataType(itemName, itemValue.toFloat(), itemActive.toBoolean(), itemPaid.toBoolean(), itemGroup))
        }

        Log.i("DB", "items generated")
        Log.i("DB", items.toString())
        cursor.close()
        db.close()

        return items
    }

    fun deleteFromDB(expenseName: String): Int {
        val db = dbHelper.writableDatabase
        // Define 'where' part of query.
        val selection = "${ModelsContract.ExpenseEntries.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf(expenseName)
        // Issue SQL statement.
        val deletedRows = db.delete(ModelsContract.ExpenseEntries.TABLE_NAME, selection, selectionArgs)
        db.close()
        return deletedRows
    }

    fun updateDB(expenseName: String, fieldName: String, updateValue: Any?): Int {
        val db = dbHelper.writableDatabase

        val updateVal = ""

        if (fieldName == ModelsContract.ExpenseEntries.COLUMN_NAME_PAID || fieldName == ModelsContract.ExpenseEntries.COLUMN_NAME_ACTIVE) {
            var i = updateValue as Boolean
            if (i) {
                val updateVal = 1
            } else {
                val updateVal = 0
            }
        } else if (fieldName == ModelsContract.ExpenseEntries.COLUMN_NAME_NAME || fieldName == ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP) {
            val updateVal = updateValue as String
        } else if (fieldName == ModelsContract.ExpenseEntries.COLUMN_NAME_VALUE) {
            val updateVal = updateValue as Float
        } else {
            return 0
        }

        val values = ContentValues().apply {
            put(fieldName, updateVal)
        }

        // Which row to update, based on the title
        val selection = "${ModelsContract.ExpenseEntries.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf(expenseName)
        return db.update(
            ModelsContract.ExpenseEntries.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    fun getUniqueGroups(): Cursor {
        val db = dbHelper.writableDatabase
        return db.query(
            true,
            ModelsContract.ExpenseEntries.TABLE_NAME,
            arrayOf(ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP),
            null,
            null,
            null,
            null,
            null,
            null,
        )

    }

}