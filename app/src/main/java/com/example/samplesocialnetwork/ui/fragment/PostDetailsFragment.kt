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
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.samplesocialnetwork.R
import com.example.samplesocialnetwork.TAG
import com.example.samplesocialnetwork.databinding.FragmentPostDetailsBinding
import com.example.samplesocialnetwork.databinding.FragmentPostsBinding
import com.example.samplesocialnetwork.ui.adapter.PostCommentsAdapter
import com.example.samplesocialnetwork.ui.viewmodel.PostDetailsViewModel
import com.example.samplesocialnetwork.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<PostDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        val postId = PostDetailsFragmentArgs.fromBundle(requireArguments()).postId
        viewModel.setPostId(postId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.postFlow.collect { post ->
                        Log.d(TAG, "likes = ${post.likesCount}   ${post.commentsCount}")
                        binding.post = post
                        binding.postImage.load(post.contentUrl)
                        if (post.favorite) {
                            binding.icFavorite.setImageResource(R.drawable.ic_redheart)
                        } else {
                            binding.icFavorite.setImageResource(R.drawable.ic_emptyheart)
                        }
                        binding.icFavorite.setOnClickListener {
                            if (post.favorite) {
                                Log.d(TAG, "unliked a post")
                                viewModel.likeListener(post.copy(likesCount = post.likesCount - 1, favorite = false))
                                binding.icFavorite.setImageResource(R.drawable.ic_emptyheart)
                            } else {
                                Log.d(TAG, "liked a post")
                                viewModel.likeListener(post.copy(likesCount = post.likesCount + 1, favorite = true))
                                binding.icFavorite.setImageResource(R.drawable.ic_redheart)
                            }
                        }
                    }
                }
                launch {
                    val commentsAdapter = PostCommentsAdapter()
                    binding.commentsRecyclerview.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = commentsAdapter
                    }
                    viewModel.commentsFlow.collect() {
                        commentsAdapter.submitData(it)
                    }
                }
                binding.addComment.load(R.drawable.ic_add_comment)
                binding.addComment.setOnClickListener {
                    val text = binding.newComment.text.toString()
                    if (text != null && text != "") {
                        Log.d(TAG, "comment is ${text.toString()}")
                        viewModel.addComment(text.toString())
                    }
                    binding.newComment.text.clear()
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