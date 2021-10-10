package com.flexship.wordsassociations.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.Exception

abstract class BaseFragment<VB : ViewBinding, S : State> : Fragment() {

    private lateinit var _binding: VB

    val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    abstract fun setupViews()

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun render(state: S)

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

}