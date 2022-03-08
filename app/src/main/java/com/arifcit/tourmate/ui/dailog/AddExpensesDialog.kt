package com.arifcit.tourmate.ui.dailog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.arifcit.tourmate.R
import com.arifcit.tourmate.model.Expenses


class AddExpensesDialog(val callBack : (Expenses) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.add_expenses,null)
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Add Expense")
            .setView(layout)
            .setPositiveButton("Save") { dialog, which ->
                val titleET : EditText = layout.findViewById(R.id.title)
                val amountET : EditText = layout.findViewById(R.id.amount)

                val title_str = titleET.text.toString()
                val amount_str = amountET.text.toString()
                if (title_str.isEmpty()){
                    ValidationDailog("Title can't be empty").show(childFragmentManager,"ok")
                }else if (amount_str.isEmpty()){
                    ValidationDailog("Amount can't be empty").show(childFragmentManager,"ok")
                }else{
                    val expenses = Expenses(
                        title = title_str,
                        amount = amount_str.toInt()
                    )
                    callBack(expenses)
                }




            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }
}