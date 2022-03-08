package com.arifcit.tourmate.model

data class Moment(
    var momentId : String? = null,
    var imageName : String? = null,
    var imageUrl : String? = null,
    var timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
)
