package com.thejosuep.notetasks.di

import com.thejosuep.notetasks.data.repository.RepositoryImpl
import com.thejosuep.notetasks.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(impl: RepositoryImpl): Repository
}