package com.example.apps.baseProject.irecyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class MyBaseAdapter<T>(val mData: List<T>) :
    RecyclerView.Adapter<MyBaseAdapter.BaseViewHolder<T>>() {
    abstract class BaseViewHolder<T>(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(mData.get(position))
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return TYPE_TYPE2_HEAD
            (mData.size - 1) -> return TYPE_TYPE3_FOOT
            else -> {
                return TYPE_TYPE2
            }
        }
    }

    companion object {
        const val TYPE_SLIDER = 0x001;
        const val TYPE_TYPE2_HEAD = 0x002; // 头部
        const val TYPE_TYPE2 = 0x003; // 普通
        const val TYPE_TYPE3_FOOT = 0x004; //底部
        const val TYPE_TYPE3 = 0x005;
        const val TYPE_TYPE4 = 0x006;
    }
}
