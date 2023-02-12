package com.example.samplesocialnetwork.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplesocialnetwork.Data
import com.example.samplesocialnetwork.TAG
import com.example.samplesocialnetwork.databinding.FragmentPostsBinding
import com.example.samplesocialnetwork.datasource.local.db.MyDatabase
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.ui.adapter.PostsAdapter
import com.example.samplesocialnetwork.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@AndroidEntryPoint
class PostsFragment : Fragment() {

    @Inject
    lateinit var myDatabase: MyDatabase

    private var _binding: FragmentPostsBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<PostsViewModel>()
    private lateinit var posts: List<Post>
    private var end = 10
    private var lock = false


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
                launch {
                    Data.initDB(myDatabase)
                    Log.d(TAG, "inside lifecycle")
                    viewModel.getPosts().collectLatest {
                        end = 10
                        posts = it
                        postsAdapter.submitList(getPosts())
                        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                                launch {
                                    if (!recyclerView.canScrollVertically(1)) {
                                        if (!lock) {
                                            lock = true
                                            Log.d(TAG, "loading new data")
                                            binding.appendProgress.visibility = VISIBLE
                                            delay(3000)
                                            postsAdapter.submitList(getPosts())
                                            binding.appendProgress.visibility = GONE
                                            lock = false
                                        }
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getPosts(): List<Post> {
        val last = min(end, posts.size)
        val list = posts.subList(0, last)
        end += 10
        return list
    }
}