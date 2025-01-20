package com.example.gitapp


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): Response<List<Repository>>
}