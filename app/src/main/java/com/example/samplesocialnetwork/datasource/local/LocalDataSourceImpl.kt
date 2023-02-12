package com.example.samplesocialnetwork.datasource.local

import com.example.samplesocialnetwork.datasource.local.db.MyDatabase
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val myDatabase: MyDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): LocalDataSource {

    override fun getSinglePost(postId: Int) = myDatabase.mainDao.getSinglePost(postId)

    override fun getPosts(start: Int, end: Int) = myDatabase.mainDao.getPosts(start, end)

    override fun getPostComments(postId: Int, start: Int, end: Int) = myDatabase.mainDao
        .getPostComments(postId, start, end)

    override fun getPostComments(postId: Int) = myDatabase.mainDao.getPostComments(postId)

    override suspend fun updatePost(post: Post) {
        withContext(dispatcher) {
            myDatabase.mainDao.updatePost(post)
        }
    }

    override suspend fun addSingleComment(post: Post, comment: Comment) {
        withContext(dispatcher) {
            val updatedPost = post.copy(commentsCount = post.commentsCount + 1)
            myDatabase.mainDao.addSingleComment(comment)
            myDatabase.mainDao.updatePost(updatedPost)
        }
    }
}