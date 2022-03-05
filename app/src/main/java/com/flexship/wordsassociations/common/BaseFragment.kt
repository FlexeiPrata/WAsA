package com.flexship.wordsassociations.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, S : State, VM: BaseViewModel<*, S>> : Fragment() {

    private var _binding: VB? = null

    val binding get() = _binding ?: throw Exception("Binding is can not be accessed.")

    abstract val viewModel: VM

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
        viewModel.errorHandler = ErrorHandler(requireContext()) { error ->
            when (error) {
                Error.MessageError -> suspendLoading()
            }
        }
    }

    fun navigate(direction: NavDirections) {
        tryNull {
            findNavController().navigate(direction)
        }
    }

    fun popBack() {
        tryNull {
            findNavController().popBackStack()
        }
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