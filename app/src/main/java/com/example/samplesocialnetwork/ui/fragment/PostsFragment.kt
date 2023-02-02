package com.example.samplesocialnetwork.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplesocialnetwork.Data
import com.example.samplesocialnetwork.TAG
import com.example.samplesocialnetwork.databinding.FragmentPostsBinding
import com.example.samplesocialnetwork.datasource.local.db.MyDatabase
import com.example.samplesocialnetwork.ui.adapter.PostsAdapter
import com.example.samplesocialnetwork.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : Fragment() {

    @Inject
    lateinit var myDatabase: MyDatabase

    private var _binding: FragmentPostsBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<PostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        binding.postsViewModel = viewModel
        val postListener: (postId: Int) -> Unit = { postId ->
            val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(postId)
            this.findNavController().navigate(action)
        }
        val postsAdapter = PostsAdapter(postListener, viewModel.likeListener)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Data.initDB(myDatabase)
                Log.d(TAG, "inside lifecycle")
                viewModel.postFlow.collect {
                    postsAdapter.submitData(it)
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}