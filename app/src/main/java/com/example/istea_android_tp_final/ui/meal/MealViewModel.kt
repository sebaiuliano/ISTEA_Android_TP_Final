package com.example.istea_android_tp_final.ui.meal

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.istea_android_tp_final.entities.meal.Meal
import com.example.istea_android_tp_final.repositories.MealRepository
import kotlinx.coroutines.*

class MealViewModel(
    private val mealRepository: MealRepository
) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var mealType : String = ""
    val mainMeal : MutableLiveData<String> = MutableLiveData()
    val secondaryMeal : MutableLiveData<String> = MutableLiveData()
    val drink : MutableLiveData<String> = MutableLiveData()
    var ateDessert : Boolean = false
    val dessert : MutableLiveData<String> = MutableLiveData()
    var temptation : Boolean = false
    val temptationMeal : MutableLiveData<String> = MutableLiveData()
    var hungry : Boolean = false

    val mealTypeIncompleteMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val mainMealIncompleteMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val dessertIncompleteMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val temptationMealIncompleteMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val mealInsertFailMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val mealInsertSuccessMutableHandler : MutableLiveData<Boolean> = MutableLiveData()

    fun onSave() {
        val meal = Meal(
            null,
            mealType,
            mainMeal.value ?: "",
            secondaryMeal.value ?: "",
            drink.value ?: "",
            ateDessert,
            dessert.value ?: "",
            temptation,
            temptationMeal.value ?: "",
            hungry,
            System.currentTimeMillis()
        )
        when {
            meal.mealType.isEmpty() -> { mealTypeIncompleteMutableHandler.value = true }
            meal.mainMeal.isEmpty() -> { mainMealIncompleteMutableHandler.value = true }
            ateDessert && meal.dessert.isEmpty() -> { dessertIncompleteMutableHandler.value = true }
            temptation && meal.temptationMeal.isEmpty() -> { temptationMealIncompleteMutableHandler.value = true }
            else -> {
                saveMeal(meal)
            }
        }
    }

    private fun saveMeal(meal: Meal) {
        uiScope.launch {
            val inserted = withContext(Dispatchers.IO) {
                mealRepository.insert(meal)
            }
            if (inserted) {
                mealInsertSuccessMutableHandler.value = true
            } else {
                mealInsertFailMutableHandler.value = true
            }
        }
    }
}