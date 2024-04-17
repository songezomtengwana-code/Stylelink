package com.ekasi.studios.stylelink.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.ekasi.studios.stylelink.UserDetail
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/*
* source https://developer.android.com/topic/libraries/architecture/datastore
* @description - serializer for UserDetail class
* */
object UserDetailSerializer: Serializer<UserDetail> {
    override val defaultValue: UserDetail = UserDetail.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDetail {
        try {
            return UserDetail.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot Read Proto.", e)
        }
    }

    override suspend fun writeTo(
        t: UserDetail,
        output: OutputStream
    )  = t.writeTo(output)

    val Context.userDetailDatastore: DataStore<UserDetail> by dataStore(
        fileName = "user.pb",
        serializer = UserDetailSerializer
    )
}
