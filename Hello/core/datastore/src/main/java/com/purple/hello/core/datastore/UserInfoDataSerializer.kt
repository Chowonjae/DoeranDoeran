package com.purple.hello.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserInfoDataSerializer @Inject constructor() : Serializer<UserInfoData> {
    override val defaultValue: UserInfoData = UserInfoData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfoData {
        try {
            return UserInfoData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("can not read userinfo proto", exception)
        }
    }

    override suspend fun writeTo(t: UserInfoData, output: OutputStream) {
        t.writeTo(output)
    }
}

//private val Context.userInfoDataStore: DataStore<UserInfoData> by dataStore(
//    fileName = "user_info_data.pb",
//    serializer = UserInfoDataSerializer(),
//)
