package com.arifcit.tourmate.ui.dailog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.arifcit.tourmate.R

class ValidationDailog( var errsms : String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage(errsms)
        builder.setIcon(R.drawable.ic_baseline_error_24)
        builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->

        })

        return builder.create()
    }
}
