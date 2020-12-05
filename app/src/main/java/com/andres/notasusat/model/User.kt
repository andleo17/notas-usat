package com.andres.notasusat.model

import android.content.Context
import com.andres.notasusat.data.apolloClient
import com.apollographql.apollo.exception.ApolloException
import com.andres.notasusat.LoginQuery
import com.apollographql.apollo.coroutines.await
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
            val response = apolloClient().query(LoginQuery( nickname, password)).await()
//            user.name = response.data?.login()?.user()?.name()
            user.eMessage = response.errors?.get(0).toString()
        } catch (e: ApolloException) {
            user.eMessage = e.message
        }

        return user
    }
}


