package com.mmfsin.pochounter.di

import com.mmfsin.pochounter.data.repository.RoomsRepository
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RoomsRepositoryModule {
    @Binds
    fun bind(repository: RoomsRepository): IRoomsRepository
}