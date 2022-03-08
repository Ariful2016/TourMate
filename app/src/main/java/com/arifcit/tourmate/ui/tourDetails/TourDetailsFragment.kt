package com.arifcit.tourmate.ui.tourDetails;


import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment;
import androidx.fragment.app.viewModels

import com.arifcit.tourmate.databinding.FragmentTourDetailsBinding
import com.arifcit.tourmate.model.Expenses
import com.arifcit.tourmate.model.Moment
import com.arifcit.tourmate.ui.addTour.AddTourViewModel
import com.arifcit.tourmate.ui.dailog.AddExpensesDialog
import com.arifcit.tourmate.ui.dailog.ShowExpenseListDialog
import com.arifcit.tourmate.ui.dailog.ShowImagesListDialog
import com.arifcit.tourmate.ui.tour.TourViewModel


public class TourDetailsFragment : Fragment() {

    private lateinit var binding : FragmentTourDetailsBinding
    private val addTourViewModel : AddTourViewModel by viewModels()
    private var tourId :String? = null
    private var tourName :String? = null

    private var allExpenses = listOf<Expenses>()
    private var allMoments = listOf<Moment>()

    public fun TourDetailsFragment() {
        // Required empty public constructor
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        tourId = arguments?.getString("id")
        binding = FragmentTourDetailsBinding.inflate(inflater)
        binding.BDT.setText("BDT: ")
        binding.expensesText.setText("Total Expenses: BDT ")
        binding.remainingBudgetTxt.setText("Remaining Budget: BDT ")
        binding.totalImagesTxt.setText("Total Images: ")

         tourId?.let {tourId ->
             addTourViewModel.getSingleTourFromDb(tourId).observe(viewLifecycleOwner) {
                 tourName = it.title
                 binding.tour = it
             }
         }
        tourId?.let {tourId ->
            addTourViewModel.getExpensesTourFromDb(tourId).observe(viewLifecycleOwner) {
                allExpenses = it
                val totalExpenses = addTourViewModel.getTotalExpenses(it)
                binding.allExpenses = totalExpenses
            }
        }
        tourId?.let {tourId ->
            addTourViewModel.getMomentsTourFromDb(tourId).observe(viewLifecycleOwner) {
                allMoments = it
                binding.allMoments = it.size

            }
        }

        binding.addExpensesBtn.setOnClickListener {
            tourId?.let {tourId ->
                AddExpensesDialog{
                    addTourViewModel.addExpenses(it,tourId)
                }.show(childFragmentManager,"add expense")
            }
        }

        binding.showExpenseBtn.setOnClickListener {
            ShowExpenseListDialog(allExpenses).show(childFragmentManager,"ok")

        }

        binding.cameraBtn.setOnClickListener {
            dispatchCameraIntent()
        }

        binding.galleryBtn.setOnClickListener {
            ShowImagesListDialog(allMoments).show(childFragmentManager,"ok")
        }


        return binding.root

    }

    private fun dispatchCameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, 101)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            addTourViewModel.UploadPhoto(imageBitmap, tourId!!, tourName!!)

        }
    }

}