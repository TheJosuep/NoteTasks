package com.thejosuep.notetasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thejosuep.notetasks.navigation.AppNavigation
import com.thejosuep.notetasks.navigation.Graphs
import com.thejosuep.notetasks.ui.screens.main.MainScreen
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoteTasksTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        onThemeClick = {
                            /* TODO: Change theme */
                        }
                    )
                }
            }
        }
    }
}