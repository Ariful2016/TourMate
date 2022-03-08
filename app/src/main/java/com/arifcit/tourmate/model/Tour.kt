package com.arifcit.tourmate.model

import com.google.firebase.Timestamp

data class Tour(
     var id : String? = null,
     var userId : String? = null,
     var title : String? = null,
     var destination : String? = null,
     var budget : Int? = null,
     var idCompleted : Boolean = false,
     var createOn : Timestamp = Timestamp.now()
)
