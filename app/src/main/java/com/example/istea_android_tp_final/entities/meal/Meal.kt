package com.example.istea_android_tp_final.entities.meal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "meals")
data class Meal (
    @PrimaryKey(autoGenerate = true)
    var id : Long?,
    @ColumnInfo(name = "meal_type")
    var mealType : String,
    @ColumnInfo(name = "main_meal")
    var mainMeal : String,
    @ColumnInfo(name = "secondary_meal")
    var secondaryMeal : String,
    var drink : String,
    @ColumnInfo(name = "ate_dessert")
    var ateDessert : Boolean,
    var dessert : String,
    var temptation : Boolean,
    @ColumnInfo(name = "temptation_meal")
    var temptationMeal : String,
    var hungry : Boolean,
    @ColumnInfo(name = "datetime")
    var dateTime : Long
)