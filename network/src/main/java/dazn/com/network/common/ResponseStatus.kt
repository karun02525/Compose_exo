package dazn.com.network.common

sealed class ResponseStatus<T>(val data: T? = null, val message: String? = null,val status: Boolean? = null) {
    class Success<T>(data: T) : ResponseStatus<T>(data)
    class Error<T>(message: String?, data: T? = null) : ResponseStatus<T>(data, message)
    class Loading<T>(status:Boolean) : ResponseStatus<T>(status=status)
}
