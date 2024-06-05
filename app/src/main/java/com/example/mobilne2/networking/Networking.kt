package rs.edu.raf.rma6.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import rs.edu.raf.rma6.networking.serialization.AppJson


val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { interceptorChain ->
        val updatedRequest = interceptorChain.request().newBuilder()
            .addHeader( "CustomHeader",  "CustomValue")
            .addHeader( "x-api-key",  "live_1huPsqiQBXIbZzvXoXfLlao4c23Y3ogfpmWuzYI3xu4ioiEcOfvDu7nPSr9tkvlg")
            .build()
            interceptorChain.proceed(updatedRequest)
    }
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    )
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.thecatapi.com/v1/")
    .client(okHttpClient)
    .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
    .build()
