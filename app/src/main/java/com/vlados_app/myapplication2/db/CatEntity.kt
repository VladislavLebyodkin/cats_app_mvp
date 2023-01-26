package com.vlados_app.myapplication2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
)
