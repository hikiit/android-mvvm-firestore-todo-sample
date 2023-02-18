package net.hiknot.android_mvvm_firestore_todo.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.hiknot.android_mvvm_firestore_todo.R
import net.hiknot.android_mvvm_firestore_todo.data.Task
import net.hiknot.android_mvvm_firestore_todo.databinding.FragmentTaskListBinding
import net.hiknot.android_mvvm_firestore_todo.databinding.ListTaskItemBinding
import net.hiknot.android_mvvm_firestore_todo.ui.entity.Page
import net.hiknot.android_mvvm_firestore_todo.ui.viewmodel.TaskListViewModel

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setReplaceEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        taskAdapter = TaskAdapter(viewLifecycleOwner, viewModel)

        binding.taskListView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            adapter = taskAdapter
        }

        setItemTouchHelper()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.taskList.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setReplaceEvent() = lifecycleScope.launch {
        viewModel.replaceEvent.collect { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    Page.AddTask -> {
                        findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
                    }

                    else -> {
                        // Do Nothing
                    }
                }
            }
        }
    }

    // アイテムスワイプで削除する
    private fun setItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // true if moved, false otherwise
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                viewModel.onRemoveTask(taskAdapter.currentList[viewHolder.adapterPosition])
                view?.let { Snackbar.make(it, "Removed", Snackbar.LENGTH_SHORT).show() };
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.taskListView)
    }
}

class TaskAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: TaskListViewModel,
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class TaskViewHolder(val binding: ListTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task, viewLifecycleOwner: LifecycleOwner, viewModel: TaskListViewModel) {
            binding.apply {
                lifecycleOwner = viewLifecycleOwner
                task = item
                this.viewModel = viewModel
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return TaskViewHolder(ListTaskItemBinding.inflate(layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(getItem(position), viewLifecycleOwner, viewModel)
        viewHolder.binding.executePendingBindings()
    }

    companion object {
        /** Task の差分を確認する **/
        val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.documentId == newItem.documentId
            }
        }
    }
}
