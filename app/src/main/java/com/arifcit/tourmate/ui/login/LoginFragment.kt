package com.arifcit.tourmate.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.FragmentLoginBinding
import com.arifcit.tourmate.ui.dailog.ValidationDailog


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginViewModel.authStatusLiveData.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthenticationStatus.AUTHENTICATED){
                findNavController().navigate(R.id.action_loginFragment_to_nav_tours)
            }
        }



        loginViewModel.errMsgLiveData.observe(viewLifecycleOwner) {
            binding.errMsg.text = it
        }

        binding = FragmentLoginBinding.inflate(inflater)


        binding.noAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.login.setOnClickListener {

            if (binding.email.text.toString().isEmpty()){
                ValidationDailog(errsms = "Email field can't be empty").show(childFragmentManager,"show")
            }else if ( binding.password.text.toString().isEmpty()){
                ValidationDailog(errsms = "Password field can't be empty").show(childFragmentManager,"show")
            }else if ( binding.password.text.toString().length<6){
                ValidationDailog(errsms = "Password should be more than 6 character").show(childFragmentManager,"show")
            }else{
                loginViewModel.Login(
                    binding.email.text.toString(),
                    binding.password.text.toString())
            }






        }


        return binding.root
    }




}