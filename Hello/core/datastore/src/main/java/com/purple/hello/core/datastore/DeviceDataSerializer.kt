package com.purple.hello.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class DeviceDataSerializer @Inject constructor() : Serializer<DeviceData> {
    override val defaultValue: DeviceData = DeviceData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DeviceData {
        try {
            return DeviceData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("can not read account proto", exception)
        }
    }

    override suspend fun writeTo(t: DeviceData, output: OutputStream) {
        t.writeTo(output)
    }
}
