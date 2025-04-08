package com.bolaware.vocalscribe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bolaware.core.theme.AppTheme
import com.bolaware.feature_history.ui.TranscriptHistoryScreen
import com.bolaware.feature_home.ui.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.KoinAndroidContext

sealed interface AppDestination {
    @Serializable
    data object Home: AppDestination

    @Serializable
    data object TranscriptHistory: AppDestination
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                KoinAndroidContext {
                    MyApp()
                }
            }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestination.Home,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                composable<AppDestination.Home> { HomeScreen() }
                composable<AppDestination.TranscriptHistory> { TranscriptHistoryScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(Screen.Home, Screen.History)
        val currentRoute by navController.currentBackStackEntryAsState()

        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    selected = currentRoute?.destination == screen.destination,
                    onClick = {
                        navController.navigate(screen.destination) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(screen.icon),
                            contentDescription = screen.title)
                    },
                    label = { Text(screen.title) }
                )
            }
        }
    }

    sealed class Screen(
        val destination: AppDestination, val title: String, @DrawableRes val icon: Int
    ) {
        data object Home :
            Screen(AppDestination.Home, "Home", R.drawable.ic_home_black_24dp)

        data object History :
            Screen(AppDestination.TranscriptHistory, "History", R.drawable.ic_history_black_24)
    }
}