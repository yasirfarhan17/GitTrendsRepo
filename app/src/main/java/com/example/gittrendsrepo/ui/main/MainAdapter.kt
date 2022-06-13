package com.example.gittrendsrepo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.gittrendsrepo.databinding.InidiviewRepoBinding
import com.example.networkmodule.data.NewRepo
import com.example.networkmodule.data.model.Repo
import com.example.networkmodule.model.RepoModel
import com.google.android.material.card.MaterialCardView

class MainAdapter(
    val callBack: MainCallBack,
    val context: Context
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(),Filterable{

    val items = ArrayList<NewRepo>()
    val itemsfilter = ArrayList<NewRepo>()

    fun SubmitList(list:ArrayList<NewRepo>){
        items.clear()
        itemsfilter.clear()
        items.addAll(list)
        itemsfilter.addAll(list)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun restoreAllList() {
        itemsfilter.clear()
        itemsfilter.addAll(items)
        notifyDataSetChanged()
    }

    inner  class MainViewHolder(private val binding: InidiviewRepoBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(item:NewRepo){
                with(binding){

                    if(item.colorList == true){
                        constraint.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
                    }else{
                        constraint.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }
                    tvWord.text=item.name
                    image.load(item.owner.avatar_url){
                        transformations(CircleCropTransformation())
                    }
                    card.setOnClickListener {
                        callBack.onClick(item,adapterPosition)
                    }
                    tvDetail.text=item.description
                    tvLanguage.text=item.language
                }
            }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {

        val binding=InidiviewRepoBinding.inflate(LayoutInflater.from(parent.context),parent,false,)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.bind(itemsfilter[position])
    }

    override fun getItemCount(): Int =itemsfilter.size

    val filter1 =object:Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val localList=ArrayList<NewRepo>()
            itemsfilter.clear()
            itemsfilter.addAll(items)
            if(p0 ==null || p0.isEmpty())
            {
                localList.addAll(itemsfilter)
            }
            else{
                for(item in itemsfilter){
                    if(item.name!!.contains(p0.toString().lowercase())){
                        localList.add(item)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = localList
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            itemsfilter.clear()
            if(p1!=null){
                itemsfilter.addAll(p1.values as Collection<NewRepo>)
            }
            notifyDataSetChanged()
        }

    }
    override fun getFilter(): Filter {
        return filter1
    }
}

interface MainCallBack{
    fun onClick(newRepo:NewRepo,pos:Int)
}