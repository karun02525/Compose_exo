package dazn.com.network.entity.product


import com.google.gson.annotations.SerializedName

data class ApiVideoItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("uri")
    val uri: String,

)