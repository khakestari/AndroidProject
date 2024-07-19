package com.example.androidproject.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidproject.MainActivity
import com.example.androidproject.databinding.FragmentSplashScreenBinding
import com.example.androidproject.presentation.home.HomeScreenFragment
import com.example.androidproject.presentation.register.RegisterScreenFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserAndNavigate()
    }

    private fun checkUserAndNavigate() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val mainActivity = activity as? MainActivity
                val userCount = mainActivity?.appDatabase?.userDao()?.getUserCount() ?: 0

                withContext(Dispatchers.Main) {
                    if (userCount > 0) {
                        mainActivity?.replaceFragment(HomeScreenFragment())
                    } else {
                        mainActivity?.replaceFragment(RegisterScreenFragment())
                    }
                }
            } catch (e: Exception) {
                Log.e("SplashScreenFragment", "Error checking user in database", e)
                withContext(Dispatchers.Main) {
                    (activity as? MainActivity)?.replaceFragment(RegisterScreenFragment())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}