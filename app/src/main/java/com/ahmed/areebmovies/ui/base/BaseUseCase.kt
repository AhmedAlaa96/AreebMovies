package com.ahmed.areebmovies.ui.base

import androidx.annotation.CallSuper
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode


open class BaseUseCase(private val mBaseRepository: IBaseRepository) : IBaseUseCase {

    @CallSuper
    protected fun validateResponse(statusResponse: Status<*>?): StatusCode {
        if (statusResponse == null)
            return StatusCode.ERROR

        if (statusResponse.isOfflineData())
            return StatusCode.OFFLINE_DATA

        if (statusResponse.isIdle())
            return StatusCode.IDLE

        if (statusResponse.isNoNetwork())
            return StatusCode.NO_NETWORK

        if (statusResponse.isError())
            return StatusCode.ERROR

        if (statusResponse.data == null)
            return StatusCode.ERROR

        if (statusResponse.isServerError())
            return StatusCode.SERVER_ERROR

        return StatusCode.VALID
    }

    fun <T> onServerError(status: Status<T>): Status<T> {
        return if (status.error.isNullOrBlank())
            Status.Error(error = "")
        else
            Status.ServerError(
                error = status.error
            )
    }

}
