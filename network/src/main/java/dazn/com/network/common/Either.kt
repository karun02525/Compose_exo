package dazn.com.network.common

class Either<out T>(val value: Any?) {

    val isSuccess: Boolean get() = value !is Failure

    fun exceptionOrNull(): Throwable? = when (value) {
        is Failure -> value.exception
        else -> null
    }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    companion object {
        fun <T> success(value: T): Either<T> = Either(value)

        fun <T> failure(exception: Throwable): Either<T> = Either(createFailure(exception))
    }

    class Failure(val exception: Throwable) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

private fun createFailure(exception: Throwable): Any = Either.Failure(exception)

inline fun <R, T> Either<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(exception)
    }
}


inline fun <R, T> Either<T>.map(transform: (value: T) -> R): Either<R> {
    return when {
        isSuccess -> Either.success(transform(value as T))
        else -> Either(value)
    }
}

inline fun <T> Either<T>.onFailure(action: (exception: Throwable) -> Unit): Either<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <T> Either<T>.onSuccess(action: (value: T) -> Unit): Either<T> {
    if (isSuccess) action(value as T)
    return this
}
