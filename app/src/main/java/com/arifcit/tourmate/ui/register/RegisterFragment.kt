package com.arifcit.tourmate.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.FragmentRegisterBinding
import com.arifcit.tourmate.ui.dailog.ValidationDailog


class RegisterFragment : Fragment() {

   private lateinit var  binding : FragmentRegisterBinding
   private val registerViewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerViewModel.errMsgLiveData.observe(viewLifecycleOwner) {
            binding.errMsg.text = it
        }

        binding.signup.setOnClickListener {
            var email = binding.email.text.toString()
            var pass = binding.password.text.toString()

            if (email.isEmpty()){
                ValidationDailog(errsms = "Email field can't be empty").show(childFragmentManager,"show")
            }else if ( pass.isEmpty()){
                ValidationDailog(errsms = "Password field can't be empty").show(childFragmentManager,"show")
            }else if (pass.length<6){
                ValidationDailog(errsms = "Password should be more than 6 character").show(childFragmentManager,"show")
            }else {

                registerViewModel.Register(email, pass)
            }
        }

        return  binding.root
    }


}