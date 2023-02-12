package com.example.samplesocialnetwork.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun getPosts() = repository.getPosts()

    val likeListener: (post: Post) -> Unit = { post ->
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }
}