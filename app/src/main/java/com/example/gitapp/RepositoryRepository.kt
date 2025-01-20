package com.example.gitapp

class RepositoryRepository(private val apiService: ApiService) {

    suspend fun fetchRepositories(username: String): List<Repository>? {
        return try {
            val response = apiService.getRepositories(username)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}