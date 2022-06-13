package com.example.networkmodule.usecase

import com.example.networkmodule.data.model.Repo
import com.example.networkmodule.network.Resources
import com.example.networkmodule.repository.GitTrendsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubUseCase @Inject constructor(
    private val repository: GitTrendsRepo
) {
    operator fun invoke(): Flow<Resources<List<Repo>>> = flow {
        emit(Resources.Loading())
        val result = repository.getTrendsData()
        if (result.isSuccessful) {
            val list = result.body()?.items ?: emptyList()
            emit(Resources.Success(list, httpCode = result.code().toString()))

        } else {
            emit(Resources.Error(result.message().toString(), httpCode = result.code().toString()))
        }
    }
}