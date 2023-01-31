package com.example.samplesocialnetwork.datasource.local.db.model

import androidx.room.*

@Entity(tableName = "posts", foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index(value = ["user_id"]),
        Index(value = ["id"], unique = true)
    ])
data class Post(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "content_url")
    val contentUrl: Int,

    @ColumnInfo(name = "caption")
    val caption: String,

    @ColumnInfo(name = "likes_count")
    val likesCount: Int,

    @ColumnInfo(name = "comments_count")
    val commentsCount: Int,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean
)
