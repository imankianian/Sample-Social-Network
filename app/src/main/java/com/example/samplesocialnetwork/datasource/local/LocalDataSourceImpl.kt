package com.example.samplesocialnetwork.datasource.local

import com.example.samplesocialnetwork.datasource.local.db.MyDatabase
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val myDatabase: MyDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): LocalDataSource {

    override fun getPosts() = myDatabase.mainDao.getPosts()

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