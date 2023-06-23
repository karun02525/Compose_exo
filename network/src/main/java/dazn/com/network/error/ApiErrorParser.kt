package dazn.com.network.error

import com.google.gson.Gson
import retrofit2.Response

open class ApiErrorParser(private val gson: Gson) {

    fun extractError(apiResponse: Response<*>): Exception {
        return try {
            if (apiResponse.errorBody() == null) {
                return ApiException()
            }
            val errorBody = apiResponse.errorBody()
            val rawResponse = errorBody!!.string()
            val errorResponse = gson.fromJson(rawResponse, ErrorResponse::class.java)
            val exception = ApiException(errorResponse.errorMessage)
            exception.code = apiResponse.code()
            exception
        } catch (error: Throwable) {
            ApiException(error)
        }
    }
}