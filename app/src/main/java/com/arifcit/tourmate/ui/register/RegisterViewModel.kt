package com.arifcit.tourmate.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arifcit.tourmate.ui.dailog.ValidationDailog
import com.arifcit.tourmate.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {
    enum class AuthenticationStatus {
        AUTHENTICATED, UNAUTHENTICATED
    }
    private val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser
    var errMsgLiveData : MutableLiveData<String> = MutableLiveData()
    val authStatusLiveData : MutableLiveData<LoginViewModel.AuthenticationStatus> = MutableLiveData()

    init {
        user?.let {
            authStatusLiveData.postValue(LoginViewModel.AuthenticationStatus.AUTHENTICATED)
        } ?: authStatusLiveData.postValue(LoginViewModel.AuthenticationStatus.UNAUTHENTICATED)
    }
    fun Register (email : String , password : String){
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    authStatusLiveData.postValue(LoginViewModel.AuthenticationStatus.AUTHENTICATED)
                }.addOnFailureListener {
                    errMsgLiveData.value = it.localizedMessage
                    ValidationDailog(it.localizedMessage.toString())
                }

   }
}