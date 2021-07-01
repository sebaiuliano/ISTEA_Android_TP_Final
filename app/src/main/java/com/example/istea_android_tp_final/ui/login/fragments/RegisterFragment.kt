package com.example.istea_android_tp_final.ui.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.istea_android_tp_final.R
import com.example.istea_android_tp_final.databinding.FragmentRegisterBinding
import com.example.istea_android_tp_final.ui.login.LoginViewModel
import com.example.istea_android_tp_final.ui.meal.MealActivity
import com.example.istea_android_tp_final.util.Tools
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val model : LoginViewModel by sharedViewModel()

    private lateinit var mView: View
    private lateinit var mBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.loginViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner
        setObservers()

        this.mBinding = binding
        this.mView = view
        initGenders()
        initTreatments()

        return view
    }

    private fun setObservers() {
        model.registeredMutableHandler.observe(requireActivity()) {
            if (it) {
                model.registeredMutableHandler.value = false
                Tools.writeToSharedPreferences(requireContext(), "username", model.username.value!!)
                val intent = Intent(requireContext(), MealActivity::class.java)
                startActivity(intent)
            }
        }
        model.userExistsMutableHandler.observe(requireActivity()) {
            if (it) {
                model.userExistsMutableHandler.value = false
                Toast.makeText(requireContext(), resources.getString(R.string.user_exists_notification), Toast.LENGTH_SHORT).show()
            }
        }
        model.passwordsDontMatchMutableHandler.observe(requireActivity()) {
            if (it) {
                model.passwordsDontMatchMutableHandler.value = false
                Toast.makeText(requireContext(), resources.getString(R.string.passwords_dont_match_notification), Toast.LENGTH_SHORT).show()
            }
        }
        model.registerFailMutableHandler.observe(requireActivity()) {
            if (it) {
                model.registerFailMutableHandler.value = false
                Toast.makeText(requireContext(), resources.getString(R.string.register_fail_notification), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initGenders() {
        val genders = resources.getStringArray(R.array.genders)
        for ((i, gender) in genders.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.text = gender
            rb.id = i
            mBinding.rgGender.addView(rb)
        }

        mBinding.rgGender.setOnCheckedChangeListener { group, checkedId ->
            model.gender = genders[checkedId]
        }
    }

    private fun initTreatments() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.treatments,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBinding.spnTreatment.adapter = adapter
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        model.treatment = parent?.getItemAtPosition(pos).toString()
    }
}