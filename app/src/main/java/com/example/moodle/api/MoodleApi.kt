package com.example.moodle.api

import com.example.moodle.db.models.ApiResponse
import com.example.moodle.db.models.ApiResponseCredntls
import com.example.moodle.db.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MoodleApi {

    @POST("api/v1/user/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<ApiResponse>

    @GET("/user/auth/")
    suspend fun authenticate(
        @Query("token")
        token: String
    ) : Response<ApiResponseCredntls>

    @GET("/user/logout/")
    suspend fun logout(
        @Query("token")
        token: String
    ) : Response<ApiResponseCredntls>

}