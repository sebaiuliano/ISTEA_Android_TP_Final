package com.example.istea_android_tp_final.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.istea_android_tp_final.entities.user.LoginUser
import com.example.istea_android_tp_final.entities.user.User
import com.example.istea_android_tp_final.repositories.UserRepository
import kotlinx.coroutines.*

class LoginViewModel (
    private val userRepository: UserRepository
) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val userExistsMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val passwordsDontMatchMutableHandler : MutableLiveData<Boolean> = MutableLiveData()

    val username : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()
    val passwordConfirmation : MutableLiveData<String> = MutableLiveData()

    val loginMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val recoverPasswordMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val registerMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val registerFailMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val registeredMutableHandler : MutableLiveData<Boolean> = MutableLiveData()

    val name : MutableLiveData<String> = MutableLiveData()
    val surname : MutableLiveData<String> = MutableLiveData()
    val idNumber : MutableLiveData<String> = MutableLiveData()
    val birthDate : MutableLiveData<Long> = MutableLiveData()
    val location : MutableLiveData<String> = MutableLiveData()

    var gender = ""
    var treatment = ""

    val failedLoginMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val goMealActivityMutableHandler : MutableLiveData<Boolean> = MutableLiveData()

    val recoverPasswordConfirmMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val recoverPasswordFailMutableHandler : MutableLiveData<Boolean> = MutableLiveData()
    val recoverPasswordSuccessMutableHandler : MutableLiveData<Boolean> = MutableLiveData()

    fun onLogin() {
        loginMutableHandler.value = true
    }

    fun onRecoverPassword() {
        recoverPasswordMutableHandler.value = true
    }

    fun onRegister() {
        registerMutableHandler.value = true
    }

    fun onConfirmRegister() {
        val user = User(
            null,
            name.value ?: "",
            surname.value ?: "",
            idNumber.value ?: "",
            gender,
            birthDate.value ?: 0L,
            location.value ?: "",
            username.value ?: "",
            password.value ?: "",
            treatment
        )
        uiScope.launch {
            val userExists = withContext(Dispatchers.IO) {
                userRepository.getByUserName(user.name) != null
            }
            when {
                userExists -> { userExistsMutableHandler.value = true }
                !password.value.isNullOrEmpty() && password.value != passwordConfirmation.value -> { passwordsDontMatchMutableHandler.value = true }
                else -> {
                    val inserted = withContext(Dispatchers.IO) {
                        userRepository.insert(user)
                    }
                    if (inserted) {
                        registeredMutableHandler.value = true
                    } else {
                        registerFailMutableHandler.value = true
                    }
                }
            }
        }
    }

    fun doLogin() {
        uiScope.launch {
            val user = withContext(Dispatchers.IO) {
                userRepository.getByUserName(username.value!!)
            }
            if (user != null && user.password == password.value) {
                loginSuccess()
            } else {
                loginFailure()
            }
        }
    }

    private fun loginSuccess() {
        goMealActivityMutableHandler.value = true
    }

    private fun loginFailure() {
        failedLoginMutableHandler.value = true
    }

    fun onConfirmRecoverPassword() {
        recoverPasswordConfirmMutableHandler.value = true
    }

    fun recoverPassword() {
        uiScope.launch {
            username.value?.let {
                val user = withContext(Dispatchers.IO) {
                    userRepository.getByUserName(it)
                }
                if (user != null) {
                    password.value?.let { pass ->
                        user.password = pass
                        val updated = withContext(Dispatchers.IO) {
                            userRepository.update(user)
                        }
                        if (updated) {
                            recoverPasswordSuccessMutableHandler.value = true
                        } else {
                            recoverPasswordFailMutableHandler.value = true
                        }
                    }
                }
            }
        }
    }
}