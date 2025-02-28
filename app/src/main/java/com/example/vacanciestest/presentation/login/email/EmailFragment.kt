package com.example.vacanciestest.presentation.login.email

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentEmailBinding
import com.example.vacanciestest.presentation.login.LoginViewModel

class EmailFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
        viewModel = viewModelProvider[LoginViewModel::class.java]

        initValidation()

        viewModel.continueButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.continueButton.isEnabled = isEnabled
        }

        binding.continueButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            val action = EmailFragmentDirections.actionEmailFragmentToCodeFragment(email)
            it.findNavController().navigate(action)
        }
    }

    private fun initValidation() {
        // Валидация поля ввода. Устанавливаем полю TextChangedListener и отправляем
        // содержимое во ViewModel для валидации, а также подписываемся на объект LiveData.
        // Если значение невалидно, будет отображена ошибка, если валидно - ошибка будет убрана
        viewModel.emailValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.emailLayout.error = null
            } else {
                binding.emailLayout.error = getString(R.string.error_email)
            }
        }
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateEmail(p0.toString())
            }
        })
    }
}