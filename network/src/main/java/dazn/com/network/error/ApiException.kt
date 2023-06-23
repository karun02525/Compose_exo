package dazn.com.network.error

class ApiException : Exception {
    var apiErrorMessage: String? = null
        private set
    var code = 0

    constructor() : super() {}
    constructor(message: String?) : super(message) {
        apiErrorMessage = message
    }
    constructor(cause: Throwable?) : super(cause) {}
}