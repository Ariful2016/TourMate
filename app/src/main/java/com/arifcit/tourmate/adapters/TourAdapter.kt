package com.arifcit.tourmate.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifcit.tourmate.databinding.TourItemBinding
import com.arifcit.tourmate.model.Tour

class TourAdapter(val callBack :(String) ->Unit) : ListAdapter<Tour, TourAdapter.TourViewHolder>(TourDiffCallBack()) {
    private lateinit var binding: TourItemBinding
    class TourViewHolder(val binding : TourItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tour: Tour){
            binding.tour = tour
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        binding = TourItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(getItem(position))
        binding.tourItem.setOnClickListener {
            callBack(getItem(position).id!!)
        }
    }

    class TourDiffCallBack : DiffUtil.ItemCallback<Tour>(){
        override fun areItemsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem == newItem
        }

    }

}