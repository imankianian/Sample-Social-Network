package com.example.samplesocialnetwork.repository

import androidx.paging.PagingSource
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getSinglePost(postId: Int): Flow<Post>
    fun getPosts(): PagingSource<Int, Post>
//    fun getPostComments(postId: Int): PagingSource<Int, Comment>
    fun getPostComments(postId: Int): Flow<List<Comment>>
    suspend fun updatePost(post: Post)
    suspend fun addSingleComment(post: Post, comment: Comment)
}