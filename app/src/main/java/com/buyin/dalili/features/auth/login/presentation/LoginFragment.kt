package com.buyin.dalili.features.auth.login.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buyin.dalili.R
import com.buyin.dalili.databinding.FragmentLoginBinding
import com.buyin.dalili.features.auth.register.data.AccountModel
import com.buyin.dalili.features.auth.register.presentation.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var users = ArrayList<AccountModel>()

    private var teacher = ArrayList<AccountModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.successGetUserLiveData.observe(viewLifecycleOwner, ::onSuccessGetUser)
        viewModel.successGetTeacherLiveData.observe(viewLifecycleOwner, ::onSuccessGetTeacher)

    }

    private fun onSuccessGetTeacher(list: List<AccountModel>?) {
        if (list != null) {
            teacher.addAll(list)
        }
    }


    private fun onSuccessGetUser(list: List<AccountModel>?) {
        if (list != null) {
            users.addAll(list)
        }
    }

    private fun initView() {
        val content = SpannableString(getString(R.string.create_account))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.textViewLabelCreateAccount.text = content
    }

    private fun initListener() {
        binding.buttonLogin.setOnClickListener {
            if (isAllValid()) {
                val account = AccountModel(
                    password = binding.editTextPassword.text.toString(),
                    university_id = binding.editTextId.text.toString(),
                )

//                var isTeacherExist = false
//                teacher.forEach {
//                    if (it.university_id == account.university_id) {
//                        Toast.makeText(requireContext(), "isExist", Toast.LENGTH_LONG).show()
//                        isTeacherExist = true
//                    }
//                }
                run breaking@{
                    users.forEach {
                        if (it.university_id == account.university_id) {
                            if (it.password == account.password) {
                                findNavController().popBackStack(R.id.item_login, true);
                                findNavController().navigate(R.id.item_college)
                                saveUserInfo(it, "student")
                                return@breaking
                            } else {
                                Toast.makeText(requireContext(), "password invalid", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                }

            }
        }


        binding.textViewLabelCreateAccount.setOnClickListener {
            findNavController().navigate(
                R.id.item_register
            )
        }


    }


    private fun saveUserInfo(model: AccountModel, userType: String?) {
        val preferences: SharedPreferences =
            requireContext().getSharedPreferences("appPre", Context.MODE_PRIVATE)

        preferences.edit().putString("universityId", model.university_id).apply()
        preferences.edit().putString("name", model.name).apply()
        preferences.edit().putBoolean("isTeacher", model.isTeacher == true).apply()
        preferences.edit().putString("password", model.password).apply()
        preferences.edit().putString("userType", userType).apply()

    }

    private fun isAllValid(): Boolean {
        var isValid = true


        if (binding.editTextId.text?.isEmpty() == true && binding.editTextId.text!!.length != 8) {
            binding.textViewErrorUniversity.isVisible = true
            isValid = false
        } else {
            binding.textViewErrorUniversity.isVisible = false
        }

        if (binding.editTextPassword.text?.isEmpty() == true || binding.editTextPassword.text!!.length < 4) {
            binding.textViewErrorPass.isVisible = true
            isValid = false
        } else {
            binding.textViewErrorPass.isVisible = false
        }

        return isValid
    }

}