package com.purple.hello.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AccountDataSerializer @Inject constructor() : Serializer<AccountData> {
    override val defaultValue: AccountData = AccountData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AccountData {
        try {
            return AccountData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("can not read account proto", exception)
        }
    }

    override suspend fun writeTo(t: AccountData, output: OutputStream) {
        t.writeTo(output)
    }
}

//private val Context.accountDataStore: DataStore<AccountData> by dataStore(
//    fileName = "account_data.pb",
//    serializer = AccountDataSerializer(),
//)
