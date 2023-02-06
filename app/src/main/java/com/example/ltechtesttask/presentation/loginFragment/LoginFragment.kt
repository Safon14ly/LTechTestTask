package com.example.ltechtesttask.presentation.loginFragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.ltechtesttask.presentation.core.afterTextChanged
import com.example.ltechtesttask.databinding.LoginFragmentBinding
import com.santalu.maskara.Mask
import com.santalu.maskara.MaskChangedListener
import com.santalu.maskara.MaskStyle
import org.koin.android.ext.android.inject

class LoginFragment: Fragment() {

    private lateinit var binding: LoginFragmentBinding

    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var phone: String
        var password: String
        var phoneMask: String
        var maskListener: MaskChangedListener?

        with(binding) {
            vLoader.isVisible = savedInstanceState == null

            viewModel.phoneMaskData.observe(viewLifecycleOwner) { maskContainer ->
                maskContainer?.let {
                    vLoader.visibility = VISIBLE
                    phoneMask = it.phoneMask
                    val mask = Mask(phoneMask, 'Х', MaskStyle.NORMAL)
                    maskListener = MaskChangedListener(mask)
                    vEnterPhone.addTextChangedListener(maskListener)
                    vLoader.visibility = GONE
                }
            }

            viewModel.obtainPassword().apply {
                password = this
                vEnterPassword.setText(this)
            }
            viewModel.obtainPhone().apply {
                phone = this
                vEnterPhone.setText(this)
            }

            vEnterPassword.afterTextChanged {
                password = it
            }

            vLoginButton.setOnClickListener {
                phone = vEnterPhone.text?.let { insertCountryCode(it) }.toString()
                viewModel.onLoginClick(phone, password)
            }
        }

    }

    // Удаление из номера телефона всех не алфавитно-цифровых символов
    private fun insertCountryCode(mask: Editable): String {
        val regex = Regex("[^a-zA-Z0-9]")
        return regex.replace(mask, "")
    }
}