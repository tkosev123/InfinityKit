package com.tk

import com.tk.repository.AuthenticationRepositoryImp
import com.tk.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthenticationRepository(impl: AuthenticationRepositoryImp): AuthenticationRepository
}