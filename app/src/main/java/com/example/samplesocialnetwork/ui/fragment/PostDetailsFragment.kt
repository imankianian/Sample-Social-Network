package com.example.samplesocialnetwork.ui.fragment

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.samplesocialnetwork.R
import com.example.samplesocialnetwork.TAG
import com.example.samplesocialnetwork.databinding.FragmentPostDetailsBinding
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.ui.adapter.PostCommentsAdapter
import com.example.samplesocialnetwork.ui.viewmodel.PostDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import kotlin.math.min


@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<PostDetailsViewModel>()
    private lateinit var comments: List<Comment>
    private var end = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        val postId = PostDetailsFragmentArgs.fromBundle(requireArguments()).postId
        viewModel.setPostId(postId)
        val commentsAdapter = PostCommentsAdapter()
        binding.commentsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
        binding.loadMore.setOnClickListener {
            commentsAdapter.submitList(getComments())
        }
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
                    viewModel.getPostComments().collectLatest {
                        comments = it
                        commentsAdapter.submitList(getComments())
                        binding.addComment.load(R.drawable.ic_add_comment)
                        binding.addComment.setOnClickListener {
                            val text = binding.newComment.text.toString()
                            if (text != null && text != "") {
                                Log.d(TAG, "comment is ${text.toString()}")
                                viewModel.addComment(text.toString())
                                binding.newComment.text.clear()
                                closeKeyBoard()
                            }
                        }
                    }
                }
                launch {

                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun closeKeyBoard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(binding.newComment.windowToken, 0)
    }

    private fun getComments(): List<Comment> {
        val lastIndex = min(end, comments.size)
        if (comments.size >= 10) {
            binding.loadMore.visibility = VISIBLE
        }
        if (lastIndex == comments.size) {
            binding.loadMore.visibility = GONE
        }
        val list = comments.subList(0, lastIndex)
        end += 10
        return list
    }
}

