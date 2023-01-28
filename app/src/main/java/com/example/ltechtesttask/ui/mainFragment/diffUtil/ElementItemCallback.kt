package com.example.ltechtesttask.ui.mainFragment.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.ltechtesttask.api.models.Element

class ElementItemCallback: DiffUtil.ItemCallback<Element>() {

    override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.id ==newItem.id
    }

    override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem == newItem
    }
}