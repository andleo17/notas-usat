package com.andres.notasusat.data

import android.content.Context
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

private var instance: ApolloClient? = null

fun apolloClient(): ApolloClient {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Only the main thread can get the apolloClient instance"
    }

    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .build()

    instance = ApolloClient.builder()
            .serverUrl("https://notas-gql.herokuapp.com/graphql/endpoint")
            .okHttpClient(okHttpClient)
            .build()

    return instance!!
}

private class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiQURNSU4ifQ.F4OwT6QvJri94etMcbawGbpu6mBs_81A8lg3OWSAdZk")
                .build()

        return chain.proceed(request)
    }
}
