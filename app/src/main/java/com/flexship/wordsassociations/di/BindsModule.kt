package com.flexship.wordsassociations.di

import com.flexship.wordsassociations.data.api.MainRepositoryImpl
import com.flexship.wordsassociations.domain.GetWordsResponseUseCaseImpl
import com.flexship.wordsassociations.domain.GuessTheWordUseCaseImpl
import com.flexship.wordsassociations.domain.MainRepository
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import com.flexship.wordsassociations.presentation.usecases.GuessTheWordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class BindsModule {
    @Binds
    abstract fun bindsRepository(impl: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun bindsGetWordsUseCase(impl: GetWordsResponseUseCaseImpl): GetWordsResponseUseCase

    @Binds
    abstract fun bindsGuessUseCase(impl: GuessTheWordUseCaseImpl): GuessTheWordUseCase
}