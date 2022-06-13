package com.example.networkmodule.data

import android.os.Parcelable
import com.example.networkmodule.data.model.Owner
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewRepo(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("stargazers_count")
    val stars: Int,

    @SerializedName("language")
    val language: String?,

    @SerializedName("owner")
    val owner: Owner,

    var colorList:Boolean?=null


) : Parcelable