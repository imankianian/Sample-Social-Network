package com.example.samplesocialnetwork.datasource.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.samplesocialnetwork.datasource.local.LocalDataSource
import com.example.samplesocialnetwork.datasource.local.db.model.Post
import com.example.samplesocialnetwork.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max

const val START_KEY = 0
const val DELAY = 3000L

class PostPagingSource @Inject constructor(private val localDataSource: LocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): PagingSource<Int, Post>() {

    private fun ensureValidKey(key: Int) = max(key, START_KEY)

    override suspend fun load(params: LoadParams<Int>) = withContext(dispatcher) {
        val start = params.key ?: START_KEY
        val end = start + params.loadSize
        if (start != START_KEY) delay(DELAY)
        val result = localDataSource.getPosts(start, end)
        LoadResult.Page(
            data = result,
            prevKey = when (start) {
                START_KEY -> null
                else -> ensureValidKey(key = start - params.loadSize)
            },
            nextKey = if (result.isNotEmpty()) {
                end + 1
            } else {
                null
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val post = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = post.id - (state.config.pageSize / 2))
    }
}