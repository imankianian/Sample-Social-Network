package com.example.samplesocialnetwork.datasource.local.db.dao

import androidx.room.*
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.datasource.local.db.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Insert
    suspend fun addSingleComment(comment: Comment)

    @Insert
    suspend fun addUsers(users: List<User>)

    @Insert
    suspend fun addPosts(posts: List<Post>)

    @Insert
    suspend fun addComments(comments: List<Comment>)

    @Update
    suspend fun updatePost(post: Post)

    @Query("SELECT * FROM users WHERE id = 1")
    suspend fun getFirstUser(): User

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getSinglePost(postId: Int): Flow<Post>

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    fun getPostComments(postId: Int): Flow<List<Comment>>
}