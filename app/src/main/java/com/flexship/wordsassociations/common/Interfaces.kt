package com.flexship.wordsassociations.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface State
interface Action
interface Item{
    fun id(): Any
    fun getUIContent(): MutableList<Any>
}
abstract class  ItemViewHolder<I: Item>(binding: ViewBinding): RecyclerView.ViewHolder(binding.root){
    abstract fun bind(item: I)
}
interface Payloadable