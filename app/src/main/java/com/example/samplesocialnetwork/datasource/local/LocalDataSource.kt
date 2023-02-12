package com.example.samplesocialnetwork.datasource.local

import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getSinglePost(postId: Int): Flow<Post>
    fun getPosts(): Flow<List<Post>>
    fun getPostComments(postId: Int): Flow<List<Comment>>
    suspend fun updatePost(post: Post)
    suspend fun addSingleComment(post: Post, comment: Comment)
}