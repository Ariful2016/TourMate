package com.arifcit.tourmate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.arifcit.tourmate.R
import com.arifcit.tourmate.ui.dailog.ValidationDailog
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    enum class AuthenticationStatus {
        AUTHENTICATED, UNAUTHENTICATED
    }
    private val  auth = FirebaseAuth.getInstance()
    var user = auth.currentUser
    var errMsgLiveData : MutableLiveData<String> = MutableLiveData()
    val authStatusLiveData : MutableLiveData<AuthenticationStatus> = MutableLiveData()

    init {
        user?.let {
            authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)
        } ?: authStatusLiveData.postValue(AuthenticationStatus.UNAUTHENTICATED)
    }
    fun Login(email : String, password : String){


            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)

                    /*if(it.isSuccessful){
                        findNavController().navigate(R.id.action_loginFragment_to_nav_tours)
                    }*/

                }.addOnFailureListener{
                    errMsgLiveData.value = it.localizedMessage

                    ValidationDailog(it.localizedMessage.toString())

                }


    }

    fun isUserValid() = user?.isEmailVerified

    fun sendVerificationMail(){
        user?.let {
            it.sendEmailVerification().addOnCompleteListener {

            }
        }
    }

    fun Logout(){
        if (user!= null){
            auth.signOut()
        }
    }
}