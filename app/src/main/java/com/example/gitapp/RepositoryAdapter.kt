package com.example.gitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.databinding.ItemRepositoryBinding

class RepositoryAdapter(
    private val repositories: MutableList<Repository>,
    private val onItemClick: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.repositoryName.text = repository.name
            binding.repositoryDescription.text = repository.description ?: "No description"
            binding.repositoryStars.text = "★ ${repository.stars}"

            binding.root.setOnClickListener {
                onItemClick(repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    fun updateList(newList: List<Repository>) {
        repositories.clear()
        repositories.addAll(newList)
        notifyDataSetChanged()
    }
}