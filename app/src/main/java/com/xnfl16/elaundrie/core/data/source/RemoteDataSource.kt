package com.xnfl16.elaundrie.core.data.source

import com.xnfl16.elaundrie.core.data.source.network.ApiService

class RemoteDataSource(private val api: ApiService) {
    suspend fun getCopyright() = api.getCopyright()
}