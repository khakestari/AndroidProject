package com.example.androidproject.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidproject.MainActivity
import com.example.androidproject.data.network.API
import com.example.androidproject.data.repository.RegisterRepository
import com.example.androidproject.data.request.RegisterRequest
import com.example.androidproject.database.Entities.User
import com.example.androidproject.databinding.FragmentRegisterScreenBinding
import java.security.MessageDigest
import com.example.androidproject.presentation.home.HomeScreenFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterScreenFragment : Fragment() {

    private var _binding: FragmentRegisterScreenBinding? = null
    private val binding get() = _binding!!

//    private lateinit var viewModel: RegisterScreenViewModel
    private val viewModel: RegisterScreenViewModel by viewModels {
        RegisterModule().provideRegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = RegisterRepository(API.baseUserService)
        val viewModelFactory = RegisterScreenViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubmitButton()
        observeRegisterResult()
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val studentId = binding.studentNumberEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInputs(name, email, studentId, password)) {
                val hashedPassword = password.md5()
                val user = RegisterRequest(email,name, studentId, hashedPassword)
                viewModel.register(user)
            }
        }
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }

    private fun validateInputs(name: String, email: String, studentId: String, password: String): Boolean {
        if (name.isBlank()) {
            showError("Name cannot be empty")
            return false
        }
        if (!isValidEmail(email)) {
            showError("Invalid email format")
            return false
        }
        if (studentId.isBlank()) {
            showError("Student ID cannot be empty")
            return false
        }
        if (password.length < 6) {
            showError("Password must be at least 6 characters long")
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun observeRegisterResult() {
        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { response ->
                    when (response.status) {
                        200 -> {
                            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                            // Create User object
                            response.data?.let { userData ->
                                val user = User(
                                    id = userData.id,
                                    name = userData.name,
                                    email = userData.email
                                )
                                navigateToNextScreen()
                            } ?: run {
                                Toast.makeText(context, "User data is null", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else -> Toast.makeText(context, "Registration failed: ${response.description.fa}", Toast.LENGTH_SHORT).show()
                    }
                },
                onFailure = { error ->
                    Toast.makeText(context, "Registration failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun insertUserIntoDatabase(user: User) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val check = (requireActivity() as MainActivity).appDatabase.userDao().insertUser(user)
        }
    }


    private fun navigateToNextScreen() {
        // Assuming you want to navigate to HomeScreenFragment
        try {
            (activity as? MainActivity)?.replaceFragment(HomeScreenFragment())
        } catch (e: Exception){
            println(e)
        }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}