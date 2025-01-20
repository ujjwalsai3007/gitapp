package com.example.gitapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") username: String): Response<List<Repository>>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String
    ): Response<SearchResponse>
}


data class SearchResponse(
    val items: List<Repository>
)

