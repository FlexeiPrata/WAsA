package com.flexship.wordsassociations.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.common.ItemViewHolder
import com.flexship.wordsassociations.databinding.AdapterHeaderBinding
import com.flexship.wordsassociations.databinding.AdapterWordBinding
import com.flexship.wordsassociations.presentation.uimodels.HeaderUIModel
import com.flexship.wordsassociations.presentation.uimodels.WordUIModel

class WordsAdapter: ListAdapter<Item, ItemViewHolder<*>>(DiffUtilStt) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            1 -> {
                val binding =
                    AdapterWordBinding.inflate(inflater, parent, false)
                WordViewHolder(binding)
            }
            else -> {
                val binding =
                    AdapterHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder<*>, position: Int) {
        getItem(position)?.let {
            if (holder is WordViewHolder) holder.bind(it as WordUIModel)
            if (holder is HeaderViewHolder) holder.bind(it as HeaderUIModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is WordUIModel) 1 else 2
    }

    inner class WordViewHolder(private val binding: AdapterWordBinding): ItemViewHolder<WordUIModel>(binding){
        override fun bind(item: WordUIModel) {
            binding.wordText.text = item.word.item
            binding.frText.text = item.word.weight.toString()
        }
    }

    inner class HeaderViewHolder(private val binding: AdapterHeaderBinding): ItemViewHolder<HeaderUIModel>(binding){
        override fun bind(item: HeaderUIModel) {
            binding.headerText.text = binding.root.context.getString(item.data)
        }
    }



}