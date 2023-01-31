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
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "content_url")
    val contentUrl: String,

    @ColumnInfo(name = "caption")
    val caption: String,

    @ColumnInfo(name = "likes_count")
    val likesCount: Long,

    @ColumnInfo(name = "comments_count")
    val commentsCount: Long,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean
)
