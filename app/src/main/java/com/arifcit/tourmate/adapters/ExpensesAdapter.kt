package com.arifcit.tourmate.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.ExpensesItemBinding
import com.arifcit.tourmate.model.Expenses

class ExpensesAdapter : ListAdapter<Expenses,ExpensesAdapter.ExpensesViewHolder>(diffExpensesCallBack()){



    class ExpensesViewHolder(val binding: ExpensesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(expenses : Expenses){
            binding.expenses = expenses
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val binding = ExpensesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExpensesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.bind(getItem(position))


    }


}

class diffExpensesCallBack : DiffUtil.ItemCallback<Expenses>(){
    override fun areItemsTheSame(oldItem: Expenses, newItem: Expenses): Boolean {
        return oldItem.expenseId == newItem.expenseId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Expenses, newItem: Expenses): Boolean {
        return oldItem == newItem
    }

}