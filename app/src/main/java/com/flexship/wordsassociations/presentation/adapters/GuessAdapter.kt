package com.flexship.wordsassociations.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.common.ItemViewHolder
import com.flexship.wordsassociations.databinding.AdapterWordForGuessBinding
import com.flexship.wordsassociations.presentation.uimodels.GuessUIModel
import com.flexship.wordsassociations.presentation.uimodels.WordUIModel

class GuessAdapter(private val onDelete: (String) -> Unit) : ListAdapter<Item, ItemViewHolder<*>>(DiffUtilStt) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return GuessViewHolder(AdapterWordForGuessBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder<*>, position: Int) {
        getItem(position)?.let {
            if (holder is GuessViewHolder) holder.bind(it as GuessUIModel)
        }
    }

    inner class GuessViewHolder(private val binding: AdapterWordForGuessBinding) :
        ItemViewHolder<GuessUIModel>(binding) {
        override fun bind(item: GuessUIModel) {
            binding.wordText.text = item.word
            binding.delete.setOnClickListener {
                onDelete(item.word)
            }
        }
    }

}