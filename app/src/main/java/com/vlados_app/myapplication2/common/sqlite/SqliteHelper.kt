package com.vlados_app.myapplication2.common.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject

class SqliteHelper @Inject constructor(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "cats_db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_FAVOURITES = "favourites"
        private const val KEY_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_FAVOURITES($KEY_ID TEXT PRIMARY KEY)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_FAVOURITES")
        onCreate(db)
    }

    fun addCatId(id: String): Long {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(KEY_ID, id)
        val success = db.insert(TABLE_FAVOURITES, null, contentValue)
        db.close()
        return success
    }

    fun isCatFavourite(id: String): Boolean {
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_FAVOURITES WHERE $KEY_ID =?"

        val cursor = db.rawQuery(query, arrayOf(id))
        var hasObject = false
        if (cursor.moveToFirst()) {
            hasObject = true
            var count = 0
            while (cursor.moveToNext()) {
                count++
            }
        }
        cursor.close()
        db.close()
        return hasObject
    }
}