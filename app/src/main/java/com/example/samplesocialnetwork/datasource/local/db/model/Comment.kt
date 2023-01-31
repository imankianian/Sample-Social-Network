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
    val id: Long = 0,

    @ColumnInfo(name = "post_id")
    val postId: Long,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "content")
    val content: String
)
