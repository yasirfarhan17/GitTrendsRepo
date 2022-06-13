package com.example.networkmodule.repository

import com.example.networkmodule.api.RepoSearchResponse
import com.example.networkmodule.data.model.Repo
import com.example.networkmodule.network.Resources
import retrofit2.Response

interface GitTrendsRepo {

    suspend fun getTrendsData(keyword:String ="android",page:Int=1,itemPerPage:Int=50) : Response<RepoSearchResponse>
}