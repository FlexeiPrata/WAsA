package com.flexship.wordsassociations.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flexship.wordsassociations.databinding.ViewToolbarBinding

class WasaToolbar(context: Context, attrs: AttributeSet?): CustomView<ViewToolbarBinding>(context, attrs) {

    override fun customViewInflater(
        from: LayoutInflater,
        customView: ConstraintLayout
    ): ViewToolbarBinding {
        return ViewToolbarBinding.inflate(from, customView, true)
    }

    fun setBackOnClickListener(onClick: (View) -> Unit) {
        binding.back.setOnClickListener(onClick)
    }

    fun setClearOnClickListener(onClick: (View) -> Unit) {
        binding.clear.setOnClickListener(onClick)
    }

}