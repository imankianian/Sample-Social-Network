package com.example.samplesocialnetwork.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val postFlow: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ) {
        repository.getPosts()
    }.flow.cachedIn(viewModelScope)

    val likeListener: (post: Post) -> Unit = { post ->
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }
}