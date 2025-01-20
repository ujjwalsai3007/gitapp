package com.example.gitapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val stars: Int,
    val username: String
)