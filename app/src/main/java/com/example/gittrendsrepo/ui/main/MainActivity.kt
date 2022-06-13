package com.example.gittrendsrepo.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gittrendsrepo.MainAdapter
import com.example.gittrendsrepo.MainCallBack
import com.example.gittrendsrepo.R
import com.example.gittrendsrepo.base.BaseActivity
import com.example.gittrendsrepo.databinding.ActivityMainBinding
import com.example.networkmodule.data.NewRepo
import com.example.networkmodule.data.model.Repo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainCallBack {

    override val viewModel: MainViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getApi()

        iniUi()
        addListener()
    }

    fun iniUi(){
        with(binding){
            rvRepo.layoutManager=LinearLayoutManager(this@MainActivity)
            rvRepo.setItemViewCacheSize(50)
            rvRepo.adapter= MainAdapter(this@MainActivity,this@MainActivity)

        }
    }

    private fun addListener(){
        with(binding){
            txtInputEtSearch.addTextChangedListener{
                (binding.rvRepo.adapter as MainAdapter).filter.filter(it.toString())
            }
            txtInputSearch.setEndIconOnClickListener {
                (rvRepo.adapter as MainAdapter).restoreAllList()
                txtInputEtSearch.text?.clear()
            }
        }
    }



    override fun addObservers() {
        viewModel.repoList.observe(this){
            it.sortedBy { itt->
                itt.stars
            }
            Log.d("insideObserver","${it.forEach { it.stars }}")
            (binding.rvRepo.adapter as MainAdapter).SubmitList(it as ArrayList<NewRepo>)
        }
    }

    override fun onClick(newRepo:NewRepo,pos:Int) {
            viewModel.updateList(newRepo,pos)
    }
}