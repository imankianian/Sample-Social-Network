package com.example.samplesocialnetwork.repository

import com.example.samplesocialnetwork.datasource.local.LocalDataSource
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): Repository {

    override fun getPosts() = localDataSource.getPosts()

    override fun getPostComments(postId: Int) = localDataSource.getPostComments(postId)

    override suspend fun likeAPost(post: Post) {
        withContext(dispatcher) {
            localDataSource.likeAPost(post)
        }
    }

    override suspend fun unLikeAPost(post: Post) {
        withContext(dispatcher) {
            localDataSource.likeAPost(post)
        }
    }

    override suspend fun addSingleComment(post: Post, comment: Comment) {
        withContext(dispatcher) {
            localDataSource.addSingleComment(post, comment)
        }
    }
}