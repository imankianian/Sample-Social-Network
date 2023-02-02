package com.example.samplesocialnetwork.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplesocialnetwork.databinding.CommentLayoutBinding
import com.example.samplesocialnetwork.datasource.local.db.model.Comment

class PostCommentsAdapter:
    PagingDataAdapter<Comment, PostCommentsAdapter.CommentViewHolder>(CommentComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommentViewHolder(CommentLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CommentViewHolder(private val binding: CommentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment?) {
            item?.let {
                binding.commentData = item
            }
        }
    }

    object CommentComparator: DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem == newItem
    }
}