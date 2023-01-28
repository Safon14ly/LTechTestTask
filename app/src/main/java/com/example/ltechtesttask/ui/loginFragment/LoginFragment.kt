package com.example.ltechtesttask.ui.loginFragment

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.ltechtesttask.R
import com.example.ltechtesttask.afterTextChanged
import com.example.ltechtesttask.viewModels.LoginViewModel
import com.santalu.maskara.Mask
import com.santalu.maskara.MaskChangedListener
import com.santalu.maskara.MaskStyle
import com.santalu.maskara.widget.MaskEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var phone: String
        var password: String
        var phoneMask: String
        var maskListener: MaskChangedListener? = null

        val editTextPhone = view.findViewById<MaskEditText>(R.id.vEnterPhone)
        val editTextPassword = view.findViewById<EditText>(R.id.vEnterPassword)
        val buttonLogin = view.findViewById<Button>(R.id.vLoginButton)
        val loader = view.findViewById<FrameLayout>(R.id.vLoader)

        loader.isVisible = savedInstanceState == null

        viewModel.getPhoneMask().observe(viewLifecycleOwner) { maskContainer ->
            maskContainer?.let {
                loader.visibility = VISIBLE
                phoneMask = it.phoneMask
                val mask = Mask(phoneMask, 'Х', MaskStyle.NORMAL)
                maskListener = MaskChangedListener(mask)
                editTextPhone.addTextChangedListener(maskListener)
                loader.visibility = GONE
            }
        }

        viewModel.obtainPassword().apply {
            password = this
            editTextPassword.setText(this)
        }
        viewModel.obtainPhone().apply {
            phone = this
            editTextPhone.setText(this)
        }

        editTextPassword.afterTextChanged {
            password = it
        }

        buttonLogin.setOnClickListener {
            val formattedPhone = phone.ifEmpty {
                insertCountryCode(editTextPhone.masked)
            }
            editTextPhone.removeTextChangedListener(maskListener)
            viewModel.onLoginClick(formattedPhone, password)
        }
    }

    // Удаление из номера телефона всех не алфавитно-цифровых символов
    private fun insertCountryCode(mask: String): String {
        val regex = Regex("[^a-zA-Z0-9]")
        return regex.replace(mask, "")
    }
}