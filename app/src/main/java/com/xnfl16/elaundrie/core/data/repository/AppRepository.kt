package com.xnfl16.elaundrie.core.data.repository

import com.xnfl16.elaundrie.core.data.source.RemoteDataSource

class AppRepository(private val remote: RemoteDataSource) {
    suspend fun getCopyright() = remote.getCopyright()
}