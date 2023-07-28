package com.thejosuep.notetasks.ui

sealed class UiEvent {
    data class SaveDarkThemeValue(val value: Boolean): UiEvent()
    data class SelectedDarkThemeValue(val value: Boolean): UiEvent()
}