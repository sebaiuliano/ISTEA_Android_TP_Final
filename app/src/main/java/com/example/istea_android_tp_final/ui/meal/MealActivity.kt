package com.example.istea_android_tp_final.ui.meal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.istea_android_tp_final.R
import com.example.istea_android_tp_final.databinding.ActivityMealBinding
import com.example.istea_android_tp_final.ui.login.LoginActivity
import com.example.istea_android_tp_final.util.Tools
import org.koin.android.viewmodel.ext.android.viewModel

class MealActivity : AppCompatActivity() {
    private val model: MealViewModel by viewModel()
    private lateinit var binding : ActivityMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)
        binding.mealViewModel = model
        binding.lifecycleOwner = this
        initSwitches()
        initSpinner()
        setObservers()
    }

    private fun initSwitches() {
        binding.swDessert.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.etDessert.visibility = if (isChecked) { View.VISIBLE } else { View.GONE }
            model.ateDessert = isChecked
        }
        binding.swTemptation.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.etTemptation.visibility = if (isChecked) { View.VISIBLE } else { View.GONE }
            model.temptation = isChecked
        }
        binding.swHungry.setOnCheckedChangeListener { buttonView, isChecked ->
            model.hungry = isChecked
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.mealType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnMealType.adapter = adapter
        }
        binding.spnMealType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                model.mealType = parent?.getItemAtPosition(position).toString()
                val visibility = if(model.mealType.equals("almuerzo", true) || model.mealType.equals("cena", true)) { View.VISIBLE } else { View.GONE }
                binding.swDessert.visibility = visibility
                if (visibility == View.VISIBLE && binding.swDessert.isChecked) {
                    binding.etDessert.visibility = View.VISIBLE
                } else {
                    binding.etDessert.visibility = View.GONE
                    binding.swDessert.isChecked = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun setObservers() {
        model.mealTypeIncompleteMutableHandler.observe(this) {
            if (it) {
                model.mealTypeIncompleteMutableHandler.value = false
                toast(resources.getString(R.string.mealtype_incomplete))
            }
        }
        model.mainMealIncompleteMutableHandler.observe(this) {
            if (it) {
                model.mainMealIncompleteMutableHandler.value = false
                toast(resources.getString(R.string.mainmeal_incomplete))
            }
        }
        model.dessertIncompleteMutableHandler.observe(this) {
            if (it) {
                model.dessertIncompleteMutableHandler.value = false
                toast(resources.getString(R.string.dessert_incomplete))
            }
        }
        model.temptationMealIncompleteMutableHandler.observe(this) {
            if (it) {
                model.temptationMealIncompleteMutableHandler.value = false
                toast(resources.getString(R.string.temptationmeal_incomplete))
            }
        }
        model.mealInsertFailMutableHandler.observe(this) {
            if (it) {
                model.mealInsertFailMutableHandler.value = false
                toast(resources.getString(R.string.meal_insert_fail))
            }
        }
        model.mealInsertSuccessMutableHandler.observe(this) {
            if (it) {
                model.mealInsertSuccessMutableHandler.value = false
                toast("Insertado correctamente")
                finish()
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Tools.removeFromSharedPreferences(this, "username")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
        return true
    }
}