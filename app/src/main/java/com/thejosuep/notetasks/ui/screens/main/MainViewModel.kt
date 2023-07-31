package com.thejosuep.notetasks.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejosuep.notetasks.domain.GetDarkThemeValueUseCase
import com.thejosuep.notetasks.domain.PutDarkThemeValueUseCase
import com.thejosuep.notetasks.ui.UiEvent
import com.thejosuep.notetasks.ui.UiState
import com.thejosuep.notetasks.utils.DARK_THEME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDarkThemeValue: GetDarkThemeValueUseCase,
    private val putDarkThemeValue: PutDarkThemeValueUseCase
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        getDarkTheme()
    }

    fun onUiEvent(e: UiEvent){
        when(e){
            is UiEvent.SaveDarkThemeValue -> saveDarkThemeValue(state.darkThemeValue)
            is UiEvent.SelectedDarkThemeValue -> state = state.copy(darkThemeValue = e.value)
        }
    }

    private fun saveDarkThemeValue(value: Boolean) {
        viewModelScope.launch {
            putDarkThemeValue(DARK_THEME_KEY, value)
        }
    }

    private fun getDarkTheme(){
        viewModelScope.launch {
            getDarkThemeValue(DARK_THEME_KEY)?.let { state = state.copy(darkThemeValue = it) }
        }
    }
}