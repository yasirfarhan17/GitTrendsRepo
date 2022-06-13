package com.example.gittrendsrepo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gittrendsrepo.base.BaseViewModel
import com.example.gittrendsrepo.base.ViewState
import com.example.gittrendsrepo.util.toLiveData
import com.example.networkmodule.data.NewRepo
import com.example.networkmodule.data.model.Repo
import com.example.networkmodule.network.Resources
import com.example.networkmodule.usecase.GitHubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GitHubUseCase
) : BaseViewModel() {


    private val _repoList=MutableLiveData<List<NewRepo>>()
    val repoList=_repoList.toLiveData()

    init {

        getApi()
    }




    fun getApi(){
        launch {
            _viewState.postValue(ViewState.Loading)
           useCase.invoke().collect(){
               when(it){
                   is Resources.Error ->  {
                       Log.d("insideError",it.httpCode + it.message.toString())
                       _viewState.postValue(ViewState.Error(it.httpCode +"\n"+ it.message.toString()))
                   }
                   is Resources.Loading ->  Log.d("insideLoading",it.httpCode.toString())
                   is Resources.Success -> {
                       val newList=it.data?.map { repo -> repo.toNewRepo() }
                       _repoList.postValue(newList)
                       _viewState.postValue(ViewState.Success())

                       Log.d("insideSuccess",it.httpCode + it.data)
                   }
               }
           }
        }
    }
    fun updateList(repo: NewRepo,pos:Int){
        val rest=_repoList.value
        rest.apply {
         this?.get(pos)?.colorList   = this?.get(pos)?.colorList?.not()

        }
        _repoList.postValue(rest)
    }

}