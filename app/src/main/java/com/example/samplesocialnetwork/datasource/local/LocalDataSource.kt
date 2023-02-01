package com.example.samplesocialnetwork.datasource.local

import androidx.paging.PagingSource
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post

interface LocalDataSource {

    fun getPosts(): PagingSource<Int, Post>
    fun getPostComments(postId: Int): PagingSource<Int, Comment>
    suspend fun updatePost(post: Post)
    suspend fun addSingleComment(post: Post, comment: Comment)
}