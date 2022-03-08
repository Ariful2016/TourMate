package com.arifcit.tourmate.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.ImagesItemBinding
import com.arifcit.tourmate.model.Expenses
import com.arifcit.tourmate.model.Moment
import com.squareup.picasso.Picasso

class ImagesAdapter : ListAdapter<Moment,ImagesAdapter.ImageViewHolder>(diffImagesCallBack()){

    lateinit var binding : ImagesItemBinding



    class ImageViewHolder(val binding: ImagesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(moment: Moment ){
            binding.moment = moment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ImagesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
         //var moment = binding.moment(position)
       // Picasso.get().load(moment.imageUrl).into(holder.binding.image);

    }


}

class diffImagesCallBack : DiffUtil.ItemCallback<Moment>(){
    override fun areItemsTheSame(oldItem: Moment, newItem: Moment): Boolean {
        return oldItem.momentId == newItem.momentId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Moment, newItem: Moment): Boolean {
        return oldItem == newItem
    }

}