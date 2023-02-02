package com.example.samplesocialnetwork.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.samplesocialnetwork.R
import com.example.samplesocialnetwork.TAG
import com.example.samplesocialnetwork.databinding.PostLayoutBinding
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import javax.inject.Inject

class PostsAdapter @Inject constructor(private val postListener: (postId: Int) -> Unit,
                                       private val likeListener: (post: Post) -> Unit):
    PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(PostComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder(postListener,
            likeListener,
            PostLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class PostViewHolder(private val postListener: (postId: Int) -> Unit,
                         private val likeListener: (post: Post) -> Unit,
                         private val binding: PostLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post?) {
            item?.let {
                binding.post = item
                binding.postImage.load(item.contentUrl)
                if (item.favorite) {
                    binding.icFavorite.setImageResource(R.drawable.ic_redheart)
                } else {
                    binding.icFavorite.setImageResource(R.drawable.ic_emptyheart)
                }
                binding.icFavorite.setOnClickListener {
                    if (item.favorite) {
                        Log.d(TAG, "unliked a post")
                        likeListener(item.copy(likesCount = item.likesCount - 1, favorite = false))
                        binding.icFavorite.setImageResource(R.drawable.ic_emptyheart)
                    } else {
                        Log.d(TAG, "liked a post")
                        likeListener(item.copy(likesCount = item.likesCount + 1, favorite = true))
                        binding.icFavorite.setImageResource(R.drawable.ic_redheart)
                    }
                }
                binding.postImage.setOnClickListener {
                    postListener(item.id)
                }
            }
        }
    }

    object PostComparator: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}