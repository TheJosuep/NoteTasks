package com.thejosuep.notetasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.thejosuep.notetasks.navigation.AppNavigation
import com.thejosuep.notetasks.ui.UiEvent
import com.thejosuep.notetasks.ui.screens.main.MainViewModel
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel = hiltViewModel<MainViewModel>()
            val darkThemeState = viewModel.state.darkThemeValue

            NoteTasksTheme(
                darkTheme = darkThemeState
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        darkTheme = darkThemeState,
                        onThemeClick = {
                            viewModel.onUiEvent(UiEvent.SelectedDarkThemeValue(!darkThemeState))
                            viewModel.onUiEvent(UiEvent.SaveDarkThemeValue(!darkThemeState))
                        }
                    )
                }
            }
        }
    }
}