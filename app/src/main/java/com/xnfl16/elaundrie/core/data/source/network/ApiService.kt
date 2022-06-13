package com.xnfl16.elaundrie.core.data.source.network

import com.xnfl16.elaundrie.core.data.source.network.response.Copyright
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("copyright.json")
    suspend fun getCopyright(): Response<Copyright>
}