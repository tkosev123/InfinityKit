package com.tk.infinitykit.di

import android.content.res.Resources
import com.tk.infinitykit.presentation.features.login.LoginErrorUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginErrorUiMapper(resources: Resources): LoginErrorUiMapper = LoginErrorUiMapper(resources)
}