package com.mmfsin.pochounter.di

import com.mmfsin.pochounter.data.database.RealmDatabase
import com.mmfsin.pochounter.data.models.PlayerDTO
import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.interfaces.IRealmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@Module
@InstallIn(ViewModelComponent::class, ServiceComponent::class)
object RealmDatabaseModule {

    @Provides
    fun provideRealmDatabase(): IRealmDatabase {
        val config = RealmConfiguration.create(
            schema = setOf(
                RoomDTO::class,
                PlayerDTO::class
            )
        )

        val realm = Realm.open(config)
        return RealmDatabase(realm)
    }
}