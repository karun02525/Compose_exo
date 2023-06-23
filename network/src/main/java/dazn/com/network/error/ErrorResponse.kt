package dazn.com.network.error

import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @SerializedName("message")
    val errorMessage: String? = null
}