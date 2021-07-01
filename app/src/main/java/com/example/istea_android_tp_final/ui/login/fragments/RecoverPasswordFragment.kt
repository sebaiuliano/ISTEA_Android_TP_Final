package com.example.istea_android_tp_final.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.istea_android_tp_final.R
import com.example.istea_android_tp_final.databinding.FragmentRecoverPasswordBinding
import com.example.istea_android_tp_final.databinding.FragmentRegisterBinding
import com.example.istea_android_tp_final.ui.login.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RecoverPasswordFragment : Fragment() {
    private val model : LoginViewModel by sharedViewModel()

    private lateinit var mView: View
    private lateinit var mBinding: FragmentRecoverPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecoverPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.loginViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

        this.mBinding = binding
        this.mView = view

        setObservers()

        return view
    }

    private fun setObservers() {
        model.recoverPasswordConfirmMutableHandler.observe(requireActivity()) {
            if (it) {
                model.recoverPasswordConfirmMutableHandler.value = false
                when {
                    model.username.value.isNullOrEmpty() -> { Toast.makeText(requireContext(), resources.getString(R.string.missing_username), Toast.LENGTH_SHORT).show() }
                    model.idNumber.value.isNullOrEmpty() -> { Toast.makeText(requireContext(), resources.getString(R.string.missing_id_number), Toast.LENGTH_SHORT).show() }
                    model.password.value.isNullOrEmpty() -> { Toast.makeText(requireContext(), resources.getString(R.string.missing_password), Toast.LENGTH_SHORT).show() }
                    model.password.value != model.passwordConfirmation.value -> { Toast.makeText(requireContext(), resources.getString(R.string.passwords_dont_match_notification), Toast.LENGTH_SHORT).show() }
                    else -> {
                        model.recoverPassword()
                    }
                }
            }
        }
        model.recoverPasswordFailMutableHandler.observe(requireActivity()) {
            if (it) {
                model.recoverPasswordFailMutableHandler.value = false
                Toast.makeText(requireContext(), resources.getString(R.string.recover_password_fail), Toast.LENGTH_SHORT).show()
            }
        }
    }
}