package com.sk.contactsapplication.di

import com.sk.contactsapplication.data.storage.PreferenceStorage
import com.sk.contactsapplication.data.storage.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BindingInjectionModule {

    @Singleton
    @Binds
    abstract fun bindSessionManagerToStorage(sessionManager: SessionManager): PreferenceStorage

}