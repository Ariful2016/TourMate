package com.arifcit.tourmate.ui.addTour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.FragmentAddTourBinding
import com.arifcit.tourmate.model.Tour
import com.arifcit.tourmate.ui.dailog.ProgressDialog
import com.arifcit.tourmate.ui.dailog.ValidationDailog
import com.arifcit.tourmate.ui.login.LoginViewModel


class AddTourFragment : Fragment() {

    private lateinit var binding : FragmentAddTourBinding

    private val loginViewModel : LoginViewModel by viewModels()
    private val addTourViewModel : AddTourViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTourBinding.inflate(inflater)


        /*val progressDialog = ProgressDialog(requireActivity())
        progressDialog.*/

       // supportActionBar?.setDisplayHomeAsUpEnabled(true);



        binding.createTourBtn.setOnClickListener {
            val title =  binding.tourName.text.toString()
            val destination = binding.destination.text.toString()
            val budget = binding.budget.text.toString()
           // val tour = Tour(title = title, destination =  destination, budget = budget, userId = loginViewModel.user?.uid)
            if (title.isEmpty()){
                ValidationDailog("Tour name can't be empty!").show(childFragmentManager,"tour name")
            }else if (destination.isEmpty()){
                ValidationDailog("Destination can't be empty!").show(childFragmentManager,"tour destination")
            }else if (budget.toString().isEmpty()){
                ValidationDailog("Budget can't be empty!").show(childFragmentManager,"tour budget")
            }else{
                val tour = Tour(title = title, destination =  destination, budget = budget.toInt(), userId = loginViewModel.user?.uid)
                addTourViewModel.addTour(tour)
                Toast.makeText(context,"Tour adds successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }



        return binding.root

    }

}


