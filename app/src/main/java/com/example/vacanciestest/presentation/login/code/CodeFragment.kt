package com.example.vacanciestest.presentation.login.code

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentCodeBinding
import com.example.vacanciestest.presentation.login.LoginViewModel
import com.example.vacanciestest.presentation.main.MainActivity

class CodeFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    private var _binding: FragmentCodeBinding? = null
    private val binding get() = _binding!!

    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
        viewModel = viewModelProvider[LoginViewModel::class.java]

        binding.sentCodeTextView.text = resources.getString(R.string.sent_code_title, email)

        initCodeEditTexts()

        binding.confirmButton.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    private fun initCodeEditTexts() {
        with(binding) {
            digitEditText1.onFocusChangeListener = createOnFocusChangeListener()
            digitEditText2.onFocusChangeListener = createOnFocusChangeListener()
            digitEditText3.onFocusChangeListener = createOnFocusChangeListener()
            digitEditText4.onFocusChangeListener = createOnFocusChangeListener()

            digitEditText1.addTextChangedListener(createCodeTextWatcher(digitEditText2, 0))
            digitEditText2.addTextChangedListener(createCodeTextWatcher(digitEditText3, 1))
            digitEditText3.addTextChangedListener(createCodeTextWatcher(digitEditText4, 2))
            digitEditText3.addTextChangedListener(createCodeTextWatcher(null, 3))
        }
    }

    private fun createCodeTextWatcher(nextEditText: EditText?, index: Int): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == 1) {
                    // Переход к следующему полю
                    nextEditText?.requestFocus()

                    // Передача данных во ViewModel для валидации и дальнейшей обработки
                    viewModel.updateCode(index, s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun createOnFocusChangeListener() : OnFocusChangeListener {
        return object : OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                val editText = view as EditText
                if (hasFocus && editText.text.toString() == "*") {
                    editText.setText("")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PARAM1 = "email"
    }
}