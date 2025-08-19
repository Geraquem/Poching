package com.mmfsin.pochounter.data.repository

import android.content.Context
import com.mmfsin.pochounter.domain.interfaces.IRealmDatabase
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RoomsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IRoomsRepository {

    override suspend fun createRoom(roomName: String) {
    }
}