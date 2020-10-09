package com.zh.common.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.*

object GsonTools {
    fun createGsonString(`object`: Any): String {
        val gson = Gson()
        return gson.toJson(`object`)
    }

    fun <T> changeGsonToBean(gsonString: String, cls: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(gsonString, cls)
    }

    fun <T> changeGsonToList(
        json: String,
        cls: Class<T>
    ): ArrayList<T> {
        val type =
            object : TypeToken<ArrayList<JsonObject?>?>() {}.type
        val jsonObjs =
            Gson().fromJson<ArrayList<JsonObject>>(json, type)
        val listOfT = ArrayList<T>()
        for (jsonObj in jsonObjs) {
            listOfT.add(Gson().fromJson(jsonObj, cls))
        }
        return listOfT
    }

    fun <T> changeGsonToListMaps(
        gsonString: String
    ): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        val gson = Gson()
        list = gson.fromJson(
            gsonString,
            object :
                TypeToken<List<Map<String?, T>?>?>() {}.type
        )
        return list
    }

    fun <T> changeGsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        val gson = Gson()
        map = gson.fromJson(
            gsonString,
            object : TypeToken<Map<String?, T>?>() {}.type
        )
        return map
    }
}