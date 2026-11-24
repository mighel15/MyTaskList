package unap.epis.mytasklist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import unap.epis.mytasklist.ui.screens.taskform.TaskFormScreen
import unap.epis.mytasklist.ui.screens.tasklist.TaskListScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TaskFlowRoutes.TASK_LIST
    ) {

        composable(TaskFlowRoutes.TASK_LIST) {
            TaskListScreen(
                onAddTask = {
                    navController.navigate(TaskFlowRoutes.TASK_FORM)
                }
            )
        }

        composable(TaskFlowRoutes.TASK_FORM) {
            TaskFormScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}