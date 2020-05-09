package com.example.apps.happybaby.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.databinding.FragmentSubjectItemBinding

class CategoryAdapter :
    ListAdapter<Catergory, RecyclerView.ViewHolder>(CatergoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            FragmentSubjectItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var category = getItem(position)
        (holder as CategoryViewHolder).bind(category)
    }

    class CategoryViewHolder(val binding: FragmentSubjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                Log.i(TAG, it.toString())
            }
        }

        fun bind(item: Catergory) {
            binding.apply {
                category = item
                executePendingBindings()
            }
        }
    }

    companion object {
        val TAG = "HOMEFRAGMENT"
    }
}

private class CatergoryDiffCallback : DiffUtil.ItemCallback<Catergory>() {

    override fun areItemsTheSame(oldItem: Catergory, newItem: Catergory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Catergory, newItem: Catergory): Boolean {
        return oldItem == newItem
    }
}