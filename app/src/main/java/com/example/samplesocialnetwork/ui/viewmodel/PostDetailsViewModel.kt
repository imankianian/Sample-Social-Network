package com.example.samplesocialnetwork.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun setPostId(postId: Int?) {
        postId?.let {
            this.postId = postId
        }
    }

    val postFlow: Flow<Post> = flow {
        postId?.let {
            repository.getSinglePost(postId!!).collect {
                emit(it)
            }
        }
    }

    val commentsFlow: Flow<PagingData<Comment>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            prefetchDistance = 1,
            initialLoadSize = 10
        )
    ) {
        repository.getPostComments(postId!!)
    }.flow.cachedIn(viewModelScope)

    val likeListener: (post: Post) -> Unit = { post ->
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }
}