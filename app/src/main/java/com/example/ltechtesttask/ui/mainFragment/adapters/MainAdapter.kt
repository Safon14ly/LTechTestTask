package com.example.ltechtesttask.ui.mainFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.ltechtesttask.R
import com.example.ltechtesttask.api.models.Element
import com.example.ltechtesttask.ui.mainFragment.diffUtil.ElementItemCallback
import com.example.ltechtesttask.ui.mainFragment.holders.MainViewHolder

class MainAdapter(
    private val onItemCLick: (Element) -> Unit
): ListAdapter<Element, MainViewHolder>(ElementItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.element_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemCLick(getItem(position)) }
    }
}