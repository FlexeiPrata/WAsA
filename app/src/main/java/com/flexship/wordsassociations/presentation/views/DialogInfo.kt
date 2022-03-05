package com.flexship.wordsassociations.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.flexship.wordsassociations.R
import com.flexship.wordsassociations.databinding.DialogInfoBinding

class DialogInfo: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogInfoBinding.inflate(LayoutInflater.from(requireContext()), container, false).root.also {
            requireDialog().window?.setBackgroundDrawableResource(R.color.transparent)
        }
    }

}