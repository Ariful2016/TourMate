package com.arifcit.tourmate.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arifcit.tourmate.model.Expenses
import com.arifcit.tourmate.model.Moment
import com.arifcit.tourmate.model.Tour
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class TourRepository {

    val  db = Firebase.firestore
    fun addTour(tour: Tour){
      val docRef = db.collection(collection_tour).document()
          tour.id =docRef.id
          docRef.set(tour).addOnSuccessListener {

          }.addOnFailureListener {
              Log.i(TAG, it.localizedMessage)
          }
    }
    fun addExpenses(expenses: Expenses, tourId: String){
        val docRef = db.collection(collection_tour)
            .document(tourId)
            .collection(collection_expenses)
            .document()
        expenses.expenseId =docRef.id
        docRef.set(expenses).addOnSuccessListener {

        }.addOnFailureListener {
            Log.i(TAG, it.localizedMessage)
        }
    }
    fun addMoment(moment: Moment, tourId: String){
        val docRef = db.collection(collection_tour)
            .document(tourId)
            .collection(collection_moment)
            .document()
        moment.momentId =docRef.id
        docRef.set(moment).addOnSuccessListener {

        }.addOnFailureListener {
            Log.i(TAG, it.localizedMessage)
        }
    }

    fun getTourFromDb(userId : String) : LiveData<List<Tour>>{
        val tourListLiveData = MutableLiveData<List<Tour>>()
        db.collection(collection_tour)
            .whereEqualTo("userId",userId)
            .addSnapshotListener { value, error ->
                if (error!= null){
                    return@addSnapshotListener
                }
                val temp = ArrayList<Tour>()
                for (doc in value!!){
                    temp.add(doc.toObject(Tour::class.java))
                }
                tourListLiveData.postValue(temp)
            }

        return tourListLiveData
    }
    fun getSingleTourFromDb(tourId : String) : LiveData<Tour>{
        val tourLiveData = MutableLiveData<Tour>()
        db.collection(collection_tour)
            .document(tourId)
            .addSnapshotListener { value, error ->
                if (error!= null){
                    return@addSnapshotListener
                }

                tourLiveData.postValue(value!!.toObject(Tour::class.java))
            }

        return tourLiveData
    }

    fun getExpensesFromDb(tourId: String ) : LiveData<List<Expenses>>{
        val expensesListLiveData = MutableLiveData<List<Expenses>>()
        db.collection(collection_tour)
            .document(tourId)
            .collection(collection_expenses)
            .addSnapshotListener { value, error ->
                if (error!= null){
                    return@addSnapshotListener
                }
                val temp = ArrayList<Expenses>()
                for (doc in value!!){
                    temp.add(doc.toObject(Expenses::class.java))
                }
                expensesListLiveData.postValue(temp)
            }

        return expensesListLiveData
    }
    fun getMomentsFromDb(tourId: String ) : LiveData<List<Moment>>{
        val imageListLiveData = MutableLiveData<List<Moment>>()
        db.collection(collection_tour)
            .document(tourId)
            .collection(collection_moment)
            .addSnapshotListener { value, error ->
                if (error!= null){
                    return@addSnapshotListener
                }
                val temp = ArrayList<Moment>()
                for (doc in value!!){
                    temp.add(doc.toObject(Moment::class.java))
                }
                imageListLiveData.postValue(temp)
            }

        return imageListLiveData
    }

    companion object{
        val TAG = "db_error"
        val collection_tour = "MY Tours"
        val collection_expenses = "MY Expenses"
        val collection_moment = "MY Moments"
    }
}