package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.*

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") { WelcomeScreen(navController) }

        composable("list") { ComponentsListScreen(navController) }

        composable("textScreen") { TextScreen(navController) }
        composable("imageScreen") { ImageScreen(navController) }

        composable("textFieldScreen") { TextFieldScreen(navController) }
        composable("passwordFieldScreen") { PasswordFieldScreen(navController) }
        composable("checkboxScreen") { CheckboxScreen(navController) }
        composable("switchScreen") { SwitchScreen(navController) }

        composable("columnScreen") { ColumnScreen(navController) }
        composable("rowScreen") { RowScreen(navController) }
        composable("boxScreen") { BoxScreen(navController) }

        composable("buttonScreen") { ButtonScreen(navController) }
        composable("iconButtonScreen") { IconButtonScreen(navController) }
        composable("fabScreen") { FabScreen(navController) }

        composable("cardScreen") { CardScreen(navController) }
        composable("surfaceScreen") { SurfaceScreen(navController) }
    }
}
