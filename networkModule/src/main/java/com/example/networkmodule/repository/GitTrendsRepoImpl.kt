package com.example.networkmodule.repository

import com.example.networkmodule.api.GithubApi
import com.example.networkmodule.api.RepoSearchResponse
import com.example.networkmodule.data.model.Repo
import com.example.networkmodule.network.Resources
import retrofit2.Response
import javax.inject.Inject

class GitTrendsRepoImpl @Inject constructor(
    private val api:GithubApi
) :GitTrendsRepo{
    override suspend fun getTrendsData(keyword: String, page: Int, itemPerPage: Int): Response<RepoSearchResponse> {
        val result=api.getTrendingRepos(keyword,page,itemPerPage)
        return result
    }


}