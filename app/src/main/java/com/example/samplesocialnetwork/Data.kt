package com.example.samplesocialnetwork

import android.util.Log
import com.example.samplesocialnetwork.datasource.local.db.MyDatabase
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.datasource.local.db.model.User

const val TAG = "SSN===>"

object Data {

    private val users = listOf(
        User(username = "user1", alias = "alias1", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user2", alias = "alias2", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user3", alias = "alias3", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user4", alias = "alias4", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user5", alias = "alias5", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user6", alias = "alias6", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user7", alias = "alias7", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user8", alias = "alias8", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user9", alias = "alias9", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user10", alias = "alias10", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
        User(username = "user11", alias = "alias11", avatarUrl = "https://fake_url.com/avatar", bio = "Nobody"),
    )

    private val posts = listOf(
        Post(userId = 1, contentUrl = R.drawable.p1, caption = "picture 1", likesCount = 32, commentsCount = 15, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p2, caption = "picture 2", likesCount = 323, commentsCount = 3, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p3, caption = "picture 3", likesCount = 34, commentsCount = 0, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p4, caption = "picture 4", likesCount = 243, commentsCount = 0, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p5, caption = "picture 5", likesCount = 64, commentsCount = 0, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p6, caption = "picture 6", likesCount = 543, commentsCount = 0, favorite = false),
        Post(userId = 1, contentUrl = R.drawable.p7, caption = "picture 7", likesCount = 123, commentsCount = 0, favorite = false),
        Post(userId = 2, contentUrl = R.drawable.p8, caption = "picture 8", likesCount = 780, commentsCount = 0, favorite = false),
        Post(userId = 3, contentUrl = R.drawable.p9, caption = "picture 9", likesCount = 869, commentsCount = 0, favorite = false),
        Post(userId = 4, contentUrl = R.drawable.p10, caption = "picture 10", likesCount = 3752, commentsCount = 0, favorite = false),
        Post(userId = 5, contentUrl = R.drawable.p11, caption = "picture 11", likesCount = 322, commentsCount = 0, favorite = false),
        Post(userId = 6, contentUrl = R.drawable.p12, caption = "picture 12", likesCount = 302, commentsCount = 0, favorite = false),
        Post(userId = 7, contentUrl = R.drawable.p13, caption = "picture 13", likesCount = 132, commentsCount = 0, favorite = false),
        Post(userId = 8, contentUrl = R.drawable.p14, caption = "picture 14", likesCount = 3, commentsCount = 0, favorite = false),
        Post(userId = 9, contentUrl = R.drawable.p15, caption = "picture 15", likesCount = 362, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p16, caption = "picture 16", likesCount = 407, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p17, caption = "picture 17", likesCount = 400, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p18, caption = "picture 18", likesCount = 410, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p19, caption = "picture 19", likesCount = 140, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p20, caption = "picture 20", likesCount = 420, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p21, caption = "picture 21", likesCount = 440, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p22, caption = "picture 22", likesCount = 12340, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p23, caption = "picture 23", likesCount = 42130, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p24, caption = "picture 24", likesCount = 41230, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p25, caption = "picture 25", likesCount = 400, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p26, caption = "picture 26", likesCount = 480, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p27, caption = "picture 27", likesCount = 470, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p28, caption = "picture 28", likesCount = 480, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p29, caption = "picture 29", likesCount = 440, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p30, caption = "picture 30", likesCount = 240, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p31, caption = "picture 31", likesCount = 430, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p32, caption = "picture 32", likesCount = 340, commentsCount = 0, favorite = false),
        Post(userId = 10, contentUrl = R.drawable.p33, caption = "picture 33", likesCount = 640, commentsCount = 0, favorite = false),
    )

    private val comments = listOf(
        Comment(postId = 1, userId = 201, content = "Nice photo"),
        Comment(postId = 1, userId = 202, content = "Wow!"),
        Comment(postId = 1, userId = 203, content = "Fascinating"),
        Comment(postId = 1, userId = 204, content = "Excellent"),
        Comment(postId = 1, userId = 205, content = "Nice shot"),
        Comment(postId = 1, userId = 206, content = "Good lighting"),
        Comment(postId = 1, userId = 207, content = "wowwww"),
        Comment(postId = 1, userId = 208, content = "Good"),
        Comment(postId = 1, userId = 209, content = "Not bad"),
        Comment(postId = 1, userId = 210, content = "Where did u learn to take such a picture"),
        Comment(postId = 1, userId = 211, content = "I wish I was there"),
        Comment(postId = 1, userId = 212, content = "cooool"),
        Comment(postId = 1, userId = 213, content = "Exotic"),
        Comment(postId = 1, userId = 214, content = "Jesus ..."),
        Comment(postId = 1, userId = 215, content = "well done"),
        Comment(postId = 2, userId = 201, content = "yeah"),
        Comment(postId = 2, userId = 202, content = "That's it"),
        Comment(postId = 2, userId = 203, content = "Nice")
    )

    suspend fun initDB(myDatabase: MyDatabase) {
        val user1 = myDatabase.mainDao.getFirstUser()
        if (user1 == null) {
            Log.d(TAG, "DB is empty, initializing ...")
            myDatabase.mainDao.addUsers(users)
            myDatabase.mainDao.addPosts(posts)
            myDatabase.mainDao.addComments(comments)
            Log.d(TAG, "DB initialization done")
        } else {
            Log.d(TAG, "DB was initialized before")
        }
    }
}