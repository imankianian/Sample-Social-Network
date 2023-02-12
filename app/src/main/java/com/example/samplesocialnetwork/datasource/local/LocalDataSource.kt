package com.example.samplesocialnetwork.datasource.local

import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getSinglePost(postId: Int): Flow<Post>
    fun getPosts(start: Int, end: Int): List<Post>
    fun getPostComments(postId: Int, start: Int, end: Int): List<Comment>
    fun getPostComments(postId: Int): Flow<List<Comment>>
    suspend fun updatePost(post: Post)
    suspend fun addSingleComment(post: Post, comment: Comment)
}