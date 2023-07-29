package com.thejosuep.notetasks.domain

import com.thejosuep.notetasks.domain.model.Repository
import javax.inject.Inject

class PutDarkThemeValueUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(key: String, value: Boolean){
        repository.putDarkThemeValue(key, value)
    }
}