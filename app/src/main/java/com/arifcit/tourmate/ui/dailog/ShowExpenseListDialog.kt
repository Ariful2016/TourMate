package com.arifcit.tourmate.ui.dailog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.arifcit.tourmate.R
import com.arifcit.tourmate.adapters.ExpensesAdapter
import com.arifcit.tourmate.model.Expenses

class ShowExpenseListDialog(val list: List<Expenses>): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.view_expenses,null)
        val recycler = layout.findViewById<RecyclerView>(R.id.expensesRecycler)

        val adapter = ExpensesAdapter().apply {
            submitList(list)
        }
        recycler.adapter = adapter

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("All Expenses")
            .setView(layout)
            .setNegativeButton("Cancel",null)

        return builder.create()
    }
}