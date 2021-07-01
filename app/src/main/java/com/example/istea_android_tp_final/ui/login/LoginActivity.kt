package com.example.istea_android_tp_final.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.istea_android_tp_final.R
import com.example.istea_android_tp_final.databinding.ActivityLoginBinding
import com.example.istea_android_tp_final.ui.login.fragments.RecoverPasswordFragment
import com.example.istea_android_tp_final.ui.login.fragments.RegisterFragment
import com.example.istea_android_tp_final.ui.meal.MealActivity
import com.example.istea_android_tp_final.util.Tools
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val model: LoginViewModel by viewModel()
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = model
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        setObservers()
        checkLoggedUser()
    }

    private fun setObservers() {
        model.loginMutableHandler.observe(this) {
            if (it) {
                model.loginMutableHandler.value = false
                when {
                    model.username.value.isNullOrEmpty() -> {
                        Toast.makeText(this, resources.getString(R.string.missing_username), Toast.LENGTH_SHORT).show()
                    }
                    model.password.value.isNullOrEmpty() -> {
                        Toast.makeText(this, resources.getString(R.string.missing_password), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        model.doLogin()
                    }
                }
            }
        }
        model.failedLoginMutableHandler.observe(this) {
            if (it) {
                model.failedLoginMutableHandler.value = false
                Toast.makeText(this, resources.getString(R.string.failed_login), Toast.LENGTH_SHORT).show()
            }
        }
        model.recoverPasswordMutableHandler.observe(this) {
            if (it) {
                goRecoverPassword()
            }
        }
        model.goMealActivityMutableHandler.observe(this) {
            if (it) {
                model.goMealActivityMutableHandler.value = false
                Tools.writeToSharedPreferences(this, "username", model.username.value!!)
                goMealActivity()
            }
        }
        model.registerMutableHandler.observe(this) {
            if (it) {
                model.registerMutableHandler.value = false
                supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.cl_login, RegisterFragment(), "register_fragment").commit()
            }
        }
        model.recoverPasswordSuccessMutableHandler.observe(this) {
            if (it) {
                model.recoverPasswordSuccessMutableHandler.value = false
                Toast.makeText(this, resources.getString(R.string.recover_password_success), Toast.LENGTH_SHORT).show()
                val fragment = supportFragmentManager.findFragmentByTag("recover_password_fragment")
                if (fragment != null) {
                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                }
            }
        }
    }

    private fun goRecoverPassword() {
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.cl_login, RecoverPasswordFragment(), "recover_password_fragment").commit()
    }

    private fun checkLoggedUser() {
        model.username.value = Tools.readFromSharedPreferences(this, "username", "") as String
        if (!model.username.value.isNullOrEmpty()) {
            goMealActivity()
        }
    }

    private fun goMealActivity() {
        val intent = Intent(this, MealActivity::class.java)
        startActivity(intent)
    }
}