package com.example.gitapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gitapp.databinding.FragmentRepositoryDetailsBinding

class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        val repository = arguments?.getParcelable<Repository>("repository")

        if (repository != null) {
            Log.d("RepositoryDetails", "Repository received: $repository")
            binding.repositoryName.text = repository.name
            binding.repositoryDescription.text = repository.description ?: "No Description Available"
            binding.repositoryStars.text = "Stars: ${repository.stars}"
        } else {
            Log.e("RepositoryDetails", "Repository is null!")
            binding.repositoryName.text = "Repository not found"
            binding.repositoryDescription.text = ""
            binding.repositoryStars.text = ""
        }

        return binding.root
    }
}