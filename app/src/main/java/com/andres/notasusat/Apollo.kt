package com.andres.notasusat

import android.content.Context
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
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
            .serverUrl("http://192.168.1.16:4000")
//            .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory("ws://192.168.1.16:4000/graphql", okHttpClient))
            .okHttpClient(okHttpClient)
            .build()

    return instance!!
}

private class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiVVNFUiJ9.V4ZQ7c89g1z39DB_U7BzrXzFsQbKtlXPFLUXqw1unfk")
                .build()

        return chain.proceed(request)
    }
}