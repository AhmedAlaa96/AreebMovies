package com.ahmed.areebmovies.data.models

sealed class Status<T>(
    val statusCode: StatusCode = StatusCode.IDLE,
    val data: T? = null,
    val error: String? = null
) {

    class Success<T>(data: T?, error: String? = null) : Status<T>(StatusCode.SUCCESS, data, error)
    class Error<T>(data: T? = null, error: String?) : Status<T>(StatusCode.ERROR, data, error)

    class ServerError<T>(data: T? = null, error: String?) :
        Status<T>(StatusCode.SERVER_ERROR, data, error)

    class NoNetwork<T>(data: T? = null, error: String?) :
        Status<T>(StatusCode.NO_NETWORK, data, error)

    class NoData<T>(data: T? = null, error: String?) :
        Status<T>(StatusCode.NO_DATA, data, error)

    class OfflineData<T>(data: T? = null, error: String?) :
        Status<T>(StatusCode.OFFLINE_DATA, data, error)

    class Idle<T>(data: T? = null, error: String? = null) :
        Status<T>(StatusCode.IDLE, data, error)

    class CopyStatus<T, R>(status: Status<T>, newData: R?) :
        Status<R>(status.statusCode, newData, status.error)


    fun isSuccess(): Boolean {
        return statusCode == StatusCode.SUCCESS
    }

    fun isError(): Boolean {
        return statusCode == StatusCode.ERROR
    }

    fun isServerError(): Boolean {
        return statusCode == StatusCode.SERVER_ERROR
    }

    fun isOfflineData(): Boolean {
        return statusCode == StatusCode.OFFLINE_DATA
    }

    fun isNoNetwork(): Boolean {
        return statusCode == StatusCode.NO_NETWORK
    }

    fun isNoData(): Boolean {
        return statusCode == StatusCode.NO_DATA
    }

    fun isValid(): Boolean {
        return statusCode == StatusCode.VALID
    }

    fun isIdle(): Boolean {
        return statusCode == StatusCode.IDLE
    }

    override fun toString(): String {
        return "Status(statusCode=$statusCode, data=$data, error=$error)"
    }


}
