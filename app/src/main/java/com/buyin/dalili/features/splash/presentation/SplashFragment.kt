package com.buyin.dalili.features.splash.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buyin.dalili.R
import com.buyin.dalili.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    lateinit var  preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(2000)

            var logo=binding.imageViewLogo
            logo.animate().rotationY(360f).setDuration(1500).alpha(0f).withEndAction{
                logo.animate().setDuration(900).alpha(1f).withEndAction {
                    findNavController().popBackStack(R.id.item_splash, true)
                    checkSession()
                }
                }
        }
    }
    private fun checkSession() {
        preferences =
            requireContext().getSharedPreferences("appPre", Context.MODE_PRIVATE)
        if (!preferences.getString("name","").isNullOrBlank()){
            findNavController().navigate(R.id.item_college)
        }else{
            findNavController().navigate(R.id.item_login)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

}