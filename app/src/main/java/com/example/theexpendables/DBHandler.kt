package com.example.theexpendables

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns

class DBHandler (context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertToDB(name: String, value: Float, active: Boolean, paid: Boolean, group: String): Long? {
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

    fun readAllFromDB() {
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
        val sortOrder = "${ModelsContract.ExpenseEntries.COLUMN_NAME_GROUP} DESC, ${ModelsContract.ExpenseEntries.COLUMN_NAME_NAME} DESC"

        val cursor = db.query(
            ModelsContract.ExpenseEntries.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        println(cursor)

//        val itemIds = mutableListOf<Long>()
//        with(cursor) {
//            while (moveToNext()) {
//                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
//                itemIds.add(itemId)
//            }
//        }
        cursor.close()
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