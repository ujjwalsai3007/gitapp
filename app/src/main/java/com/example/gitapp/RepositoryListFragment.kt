package com.example.gitapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.databinding.FragmentRepositoryListBinding

class RepositoryListFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryListBinding
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var adapter: RepositoryAdapter

    private val repositoryList = mutableListOf<Repository>()
    private val displayedList = mutableListOf<Repository>() // Filtered list for search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable options menu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryListBinding.inflate(inflater, container, false)

        val username = arguments?.getString("USERNAME") ?: ""
        viewModel = ViewModelProvider(this)[RepositoryViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        // Check network availability and fetch repositories
        val isNetworkAvailable = isNetworkAvailable(requireContext())
        viewModel.fetchRepositories(username, isNetworkAvailable)

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RepositoryAdapter(displayedList) { repository ->
            showRepositoryDetails(repository)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.repositoryList.observe(viewLifecycleOwner) { repositories ->
            repositoryList.clear()
            repositoryList.addAll(repositories)
            displayedList.clear()
            displayedList.addAll(repositories)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showRepositoryDetails(repository: Repository) {
        val fragment = RepositoryDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("repository", repository)
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.repository_list_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search Repositories"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterRepositories(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterRepositories(it) }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filterRepositories(query: String) {
        displayedList.clear()
        if (query.isEmpty()) {
            displayedList.addAll(repositoryList)
        } else {
            displayedList.addAll(repositoryList.filter {
                it.name.contains(query, ignoreCase = true)
            })
        }
        adapter.notifyDataSetChanged()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}