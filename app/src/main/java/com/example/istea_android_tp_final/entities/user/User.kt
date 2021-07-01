package com.example.istea_android_tp_final.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id : Long?,
    var name : String,
    var surname : String,
    @ColumnInfo(name = "id_number")
    var idNumber : String,
    var gender: String,
    @ColumnInfo(name = "birth_date")
    var birthDate : Long,
    var location: String,
    var username: String,
    var password: String,
    var treatment: String
)