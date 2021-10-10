package com.flexship.wordsassociations.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.flexship.wordsassociations.common.Item

object DiffUtilStt : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id() == newItem.id()
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.getUIContent() == newItem.getUIContent()
    }

}