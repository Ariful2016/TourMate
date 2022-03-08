package com.arifcit.tourmate.model


import java.sql.Timestamp


class Expenses (
    var expenseId : String? = null,
    var title : String? = null,
    var amount : Int? = null,
    var createOn : com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
)


