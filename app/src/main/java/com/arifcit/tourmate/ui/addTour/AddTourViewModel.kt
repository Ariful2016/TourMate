package com.arifcit.tourmate.ui.addTour

import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.arifcit.tourmate.model.Expenses
import com.arifcit.tourmate.model.Moment
import com.arifcit.tourmate.model.Tour
import com.arifcit.tourmate.repository.TourRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class AddTourViewModel : ViewModel() {
    val tourRepository = TourRepository()

    fun addTour(tour: Tour){
        tourRepository.addTour(tour)
    }
    fun addExpenses(expenses: Expenses, tourId: String){
        tourRepository.addExpenses(expenses, tourId)
    }

    fun getTourFromDb(userId : String) = tourRepository.getTourFromDb(userId)
    fun getSingleTourFromDb(tourId : String) = tourRepository.getSingleTourFromDb(tourId)
    fun getExpensesTourFromDb(tourId : String) = tourRepository.getExpensesFromDb(tourId)
    fun getMomentsTourFromDb(tourId : String) = tourRepository.getMomentsFromDb(tourId)

    fun getTotalExpenses(list: List<Expenses>) : Int{
        var total = 0
        list.forEach { expenses ->  
            total += expenses.amount!!
        }
        return total
    }


    fun UploadPhoto(bitmap: Bitmap, tourId: String, tourName : String){

        val imageName = "${tourId}_${System.currentTimeMillis()}.jpg"
        val photoRef = Firebase.storage.reference.child("$tourName/$imageName")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val imageData = baos.toByteArray()
        val uploadTask = photoRef.putBytes(imageData)

        uploadTask.addOnSuccessListener {


        }.addOnFailureListener {

        }

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val moment = Moment(imageName = imageName,imageUrl = downloadUri.toString()
                )
                tourRepository.addMoment(moment, tourId)
            }
        }




    }

}