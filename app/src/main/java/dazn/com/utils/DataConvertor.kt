package dazn.com.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dazn.com.data.model.Video

object DataConvertor {
    fun toVideList(json: String): List<Video> {
        val typeToken = object : TypeToken<List<Video>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

}