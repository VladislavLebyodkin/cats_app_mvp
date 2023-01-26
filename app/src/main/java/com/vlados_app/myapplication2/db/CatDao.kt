package com.vlados_app.myapplication2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCat(catEntity: CatEntity)

    @Query("SELECT EXISTS(SELECT * FROM catEntity WHERE id = :id)")
    fun isFavourite(id: String): Boolean
}
