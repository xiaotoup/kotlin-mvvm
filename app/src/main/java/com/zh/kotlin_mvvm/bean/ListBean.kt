package com.zh.kotlin_mvvm.bean
import com.google.gson.annotations.SerializedName

data class ListBean(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String

)