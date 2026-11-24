package unap.epis.mytasklist.ui.screens.taskform


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import unap.epis.mytasklist.data.local.TaskEntity
import unap.epis.mytasklist.data.repository.TaskRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    fun saveTask(
        title: String,
        description: String,
        onSaved: () -> Unit
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title.trim(),
                    description = description.trim()
                )
            )

            onSaved()
        }
    }
}