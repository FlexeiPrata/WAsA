package com.flexship.wordsassociations.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface State
interface Action
interface Item{
    fun id(): Any
    fun getUIContent(): Any
}
abstract class  ItemViewHolder<VB: ViewBinding, I: Item>(binding: VB): RecyclerView.ViewHolder(binding.root){
    abstract fun bind(item: I)
}