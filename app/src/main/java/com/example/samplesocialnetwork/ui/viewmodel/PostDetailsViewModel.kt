package com.example.samplesocialnetwork.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.samplesocialnetwork.datasource.local.db.model.Comment
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private var postId: Int? = null
    private lateinit var post: Post

    fun setPostId(postId: Int?) {
        postId?.let {
            this.postId = postId
        }
    }

    val postFlow: Flow<Post> = flow {
        postId?.let {
            repository.getSinglePost(postId!!).collect {
                post = it
                emit(it)
            }
        }
    }

    val commentsFlow: Flow<PagingData<Comment>> = Pager(
        config = PagingConfig(
            pageSize = 100,
            enablePlaceholders = false
        )
    ) {
        repository.getPostComments(postId!!)
    }.flow.cachedIn(viewModelScope)

    val likeListener: (post: Post) -> Unit = { post ->
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }

    fun addComment(text: String) {

        // Since we don't track users at this time, we assume it's a default userId like 1
        val comment = Comment(postId = post.id, userId = 1, content = text)
        viewModelScope.launch {
            post?.let {
                repository.addSingleComment(post, comment)
            }
        }
    }
}