package com.example.samplesocialnetwork.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplesocialnetwork.datasource.local.db.dao.MainDao
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.datasource.local.db.model.User

@Database(entities = [User::class, Post::class, Comment::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract val mainDao: MainDao
}