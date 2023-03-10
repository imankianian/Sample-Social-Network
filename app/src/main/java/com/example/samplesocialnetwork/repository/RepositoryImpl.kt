package com.example.samplesocialnetwork.repository

import com.example.samplesocialnetwork.datasource.local.LocalDataSource
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): Repository {

    override fun getSinglePost(postId: Int) = localDataSource.getSinglePost(postId).flowOn(dispatcher)

    override fun getPosts() = localDataSource.getPosts().flowOn(dispatcher)

    override fun getPostComments(postId: Int) = localDataSource.getPostComments(postId).flowOn(dispatcher)

    override suspend fun updatePost(post: Post) {
        withContext(dispatcher) {
            localDataSource.updatePost(post)
        }
    }

    override suspend fun addSingleComment(post: Post, comment: Comment) {
        withContext(dispatcher) {
            localDataSource.addSingleComment(post, comment)
        }
    }
}