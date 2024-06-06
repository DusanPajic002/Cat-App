package com.example.mobilne2.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import androidx.datastore.core.Serializer

class AuthDataSerializer : Serializer<String> {
    override val defaultValue: String = ""

    override suspend fun readFrom(input: InputStream): String {
        return withContext(Dispatchers.IO) {
            val sb = StringBuilder()
            var ch: Int
            while ((input.read().also { ch = it }) != -1) {
                sb.append(ch.toChar())
            }
            sb.toString()
        }
    }

    override suspend fun writeTo(t: String, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(t.toByteArray())
        }
    }
}