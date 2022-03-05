package com.flexship.wordsassociations.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding

abstract class CustomView<B: ViewBinding>(context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs) {

    private var _binding: B? = null

    abstract fun customViewInflater(from: LayoutInflater, customView: ConstraintLayout): B

    init {
        constructView()
    }

    val binding get() = _binding ?: throw Exception("View is already destroyed")

    private fun constructView() {
        _binding = customViewInflater(LayoutInflater.from(context), this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}