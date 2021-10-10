package com.flexship.wordsassociations.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    /*@Binds
    abstract fun bindsContactsRepository(impl: ContactsRepositoryImpl): ContactsRepository

    @Binds
    abstract fun bindsFavRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    abstract fun bindsDescRepository(impl: DescRepositoryImpl): DescRepository

    @Binds
    abstract fun bindsProductRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    abstract fun bindsCartRepository(impl: CartRepositoryImpl): CartRepository*/

}