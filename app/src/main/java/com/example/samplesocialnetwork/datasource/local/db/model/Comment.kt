package com.example.samplesocialnetwork.datasource.local.db.model

import androidx.room.*

@Entity(tableName = "comments", foreignKeys = [
    ForeignKey(
        entity = Post::class,
        parentColumns = ["id"],
        childColumns = ["post_id"],
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index(value = ["post_id"]),
        Index(value = ["id"], unique = true)
    ])
data class Comment(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "post_id")
    val postId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "content")
    val content: String
)
