package com.flexship.wordsassociations.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.Exception

abstract class BaseFragment<VB : ViewBinding, S : State, VM: BaseViewModel<*, S>> : Fragment() {

    private var _binding: VB? = null

    val binding get() = _binding ?: throw Exception("Binding is can not be accessed.")

    abstract val viewModel: VM

    open lateinit var state: S

    open var errorAction = {
        state.isLoading = false
        render(state)
    }

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
        setupStateObserver()
        setupViews()
        viewModel.errorHandler = ErrorHandler(this::showToast)
    }

    fun showToast(message: Int){
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_LONG).show()
        suspendLoading()
    }


    abstract fun setupViews()

    abstract fun setupStateObserver()

    open fun suspendLoading(){}

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun render(state: S)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}