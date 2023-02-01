package com.example.samplesocialnetwork.repository

import androidx.paging.PagingSource
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post

interface Repository {

    fun getPosts(): PagingSource<Int, Post>
    fun getPostComments(postId: Int): PagingSource<Int, Comment>
    suspend fun likeAPost(post: Post)
    suspend fun unLikeAPost(post: Post)
    suspend fun addSingleComment(post: Post, comment: Comment)
}