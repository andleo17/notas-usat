package com.andres.notasusat.ui.business

import android.content.Context
import com.apollographql.apollo.coroutines.toDeferred
import android.util.Log
import com.andres.notasusat.ui.data.apolloClient
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.LoginQuery
import java.util.*

class User {
    var id: String? = null
    var nickname: String? = null
    var password: String? = null
    var email: String? = null
    var name: String? = null
    var lastname: String? = null
    var birthdate: Date? = null
    var photo: String? = null
    var genre: Boolean? = null
    var state: Boolean? = null
    var login: Boolean? = null
    var eMessage: String? = null
    var semesterId: String? = null
    var schoolId: String? = null

    suspend fun login(nickname: String, password: String, context: Context): User {
        val user = User()
        try {
            val response = apolloClient(context).query(LoginQuery( nickname, password)).toDeferred().await()
//            user.name = response.data?.login()?.user()?.name()
            user.eMessage = response.errors?.get(0).toString()
        } catch (e: ApolloException) {
            user.eMessage = e.message
        }

        return user;
    }
}


