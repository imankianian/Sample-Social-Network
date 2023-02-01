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


    override suspend fun likeAPost(post: Post) {
        withContext(dispatcher) {
            val updatedPost = post.copy(likesCount = post.likesCount + 1, favorite = true)
            myDatabase.mainDao.updatePost(updatedPost)
        }
    }

    override suspend fun unLikeAPost(post: Post) {
        withContext(dispatcher) {
            val updatedPost = post.copy(likesCount = post.likesCount - 1, favorite = false)
            myDatabase.mainDao.updatePost(updatedPost)
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