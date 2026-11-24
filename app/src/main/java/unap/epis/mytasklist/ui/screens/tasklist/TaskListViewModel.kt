package unap.epis.mytasklist.ui.screens.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import unap.epis.mytasklist.data.local.TaskEntity
import unap.epis.mytasklist.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(TaskFilter.ALL)

    val filter: StateFlow<TaskFilter> = _filter

    val tasks: StateFlow<List<TaskEntity>> =
        combine(
            repository.getAllTasks(),
            _filter
        ) { taskList, selectedFilter ->

            when (selectedFilter) {

                TaskFilter.ALL -> taskList

                TaskFilter.PENDING ->
                    taskList.filter { !it.isCompleted }

                TaskFilter.COMPLETED ->
                    taskList.filter { it.isCompleted }
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun setFilter(filter: TaskFilter) {
        _filter.value = filter
    }

    fun insertTask(
        title: String,
        description: String
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title.trim(),
                    description = description.trim()
                )
            )
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(
                task.copy(
                    isCompleted = !task.isCompleted
                )
            )
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

}