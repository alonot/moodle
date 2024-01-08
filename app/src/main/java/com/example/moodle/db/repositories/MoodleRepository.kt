package com.example.moodle.db.repositories

import com.example.moodle.api.RetrofitInterface
import com.example.moodle.db.models.LoginRequest

class MoodleRepository(

) {

    suspend fun login(loginRequest: LoginRequest)=
        RetrofitInterface.api.login(loginRequest)

    suspend fun  authenticate(token : String)=
        RetrofitInterface.api.authenticate(token)

    suspend fun logout(token: String)=
        RetrofitInterface.api.logout(token)
}