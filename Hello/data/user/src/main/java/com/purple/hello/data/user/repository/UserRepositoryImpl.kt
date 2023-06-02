package com.purple.hello.data.user.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.MemberEntity
import com.purple.core.model.Profile
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.utils.toLocalDateTime
import com.purple.hello.data.user.datasource.RemoteUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val userDao: UserDao,
    private val userDataStore: UserDataStore,
) : UserRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchUserInfo() {
        runCatching {
            remoteUserDataSource.getUserInfo()
        }.onFailure {
            throw it
        }.getOrThrow().let {
            val response = checkNotNull(it.body())
            withContext(Dispatchers.IO) {
                userDao.insertMember(
                    MemberEntity(
                        response.birth?.toLocalDateTime(),
                        response.userId,
                        response.profileUrl,
                    ),
                )
                userDataStore.setUserId(response.userId)
            }
        }
    }
    override fun getUserId(): Flow<Long> = userDataStore.userId

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun setProfile(profileUrl: String, birth: String) {
        remoteUserDataSource.setProfile(
            profileUrl,
            birth,
        ).let {
            val userId = runBlocking { userDataStore.userId.first() }
            if (it.isSuccessful) {
                userDao.updateMember(
                    MemberEntity(
                        userId = userId,
                        profileUrl = profileUrl,
                        birth = birth.toLocalDateTime("yyyy-MM-dd"),
                    ),
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getProfile(): Flow<Profile> {
        val userId = runBlocking { userDataStore.userId.first() }
        return userDao.getProfileUrl(userId).map {
            Profile(
                profileUrl = it.profileUrl,
                birth = it.birth,
            )
        }
    }

    override suspend fun sendSafeAlarm(): Result<String> = runCatching {
        val response = remoteUserDataSource.sendSafeAlarm()
        response.body() ?: ""
    }
}
