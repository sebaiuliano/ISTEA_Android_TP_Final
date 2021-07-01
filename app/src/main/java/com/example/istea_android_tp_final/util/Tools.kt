package com.example.istea_android_tp_final.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.HashSet

class Tools {
    companion object {
        private fun getMasterKey() : String {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            return MasterKeys.getOrCreate(keyGenParameterSpec)
        }

        private fun getSharedPreferences(context: Context) : SharedPreferences {
            return EncryptedSharedPreferences.create(
                context.getString(context.applicationInfo.labelRes),
                getMasterKey(),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        fun writeToSharedPreferences(context: Context, key: String, value: Any) : Boolean {
            val sharedPref = getSharedPreferences(context)
            var retorno = true
            with(sharedPref.edit()) {
                when (value) {
                    is String -> { this.putString(key, value) }
                    is Int -> { this.putInt(key, value) }
                    is Float -> { this.putFloat(key, value) }
                    is Long -> { this.putLong(key, value) }
                    is Boolean -> { this.putBoolean(key, value) }
                    is HashSet<*> -> {
                        if (value as? Set<String> != null) {
                            this.putStringSet(key, value as? Set<String>)
                        }
                    }
                    else -> {retorno = false}
                }
                this.apply()
            }
            return retorno
        }

        fun readFromSharedPreferences(context: Context, key: String, defaultValue: Any) : Any? {
            val sharedPref = getSharedPreferences(context)
            var retorno : Any? = null
            with(sharedPref) {
                when (defaultValue) {
                    is String -> { this.getString(key, defaultValue)?.let { retorno = it } }
                    is Int -> { retorno = this.getInt(key, defaultValue) }
                    is Float -> { retorno = this.getFloat(key, defaultValue) }
                    is Long -> { retorno = this.getLong(key, defaultValue) }
                    is Boolean -> { retorno = this.getBoolean(key, defaultValue) }
                    is HashSet<*> -> { retorno = this.getStringSet(key, defaultValue as? Set<String>) }
                    else -> {}
                }
            }
            return retorno
        }

        fun removeFromSharedPreferences(context: Context, key: String){
            val sharedPref = getSharedPreferences(context)
            sharedPref.edit().remove(key).apply()
        }
    }
}