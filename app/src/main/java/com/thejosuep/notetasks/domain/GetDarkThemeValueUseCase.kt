package com.thejosuep.notetasks.domain

import com.thejosuep.notetasks.domain.model.Repository
import javax.inject.Inject

class GetDarkThemeValueUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(key: String) = repository.getDarkThemeValue(key)
}