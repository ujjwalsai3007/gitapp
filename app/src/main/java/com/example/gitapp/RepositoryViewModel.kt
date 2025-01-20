package com.example.gitapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryDao = AppDatabase.getInstance(application).repositoryDao()
    private val _repositoryList = MutableLiveData<List<Repository>>()
    val repositoryList: LiveData<List<Repository>> get() = _repositoryList

    fun fetchRepositories(username: String, isNetworkAvailable: Boolean) {
        if (!isNetworkAvailable) {
            // Load cached repositories from Room database
            viewModelScope.launch(Dispatchers.IO) {
                val cachedRepositories = repositoryDao.getRepositoriesByUsername(username)
                val repositories = cachedRepositories.map {
                    Repository(it.id, it.name, it.description, it.stars)
                }
                _repositoryList.postValue(repositories)
            }
        } else {
            // Fetch repositories from GitHub API
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiClient.githubApiService.getRepositories(username)
                    if (response.isSuccessful) {
                        response.body()?.let { repoList ->
                            saveRepositoriesToDatabase(repoList, username)
                            _repositoryList.postValue(repoList)
                        }
                    } else {
                        // Handle API error
                        _repositoryList.postValue(emptyList())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _repositoryList.postValue(emptyList()) // Set empty list on failure
                }
            }
        }
    }

    private fun saveRepositoriesToDatabase(repositories: List<Repository>, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val entities = repositories.map {
                RepositoryEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description ?: "No description",
                    stars = it.stars,
                    username = username
                )
            }
            repositoryDao.insertRepositories(entities)
        }
    }
}