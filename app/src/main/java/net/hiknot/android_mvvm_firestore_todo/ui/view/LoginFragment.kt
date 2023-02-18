package net.hiknot.android_mvvm_firestore_todo.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.hiknot.android_mvvm_firestore_todo.R
import net.hiknot.android_mvvm_firestore_todo.databinding.FragmentLoginBinding
import net.hiknot.android_mvvm_firestore_todo.ui.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReplaceEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setReplaceEvent() = lifecycleScope.launch {
        viewModel.replaceEvent.collect { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    LoginViewModel.ReplacePage.TaskList -> {
                        findNavController().navigate(R.id.action_loginFragment_to_taskListFragment)
                    }
                }
            }
        }
    }
}
