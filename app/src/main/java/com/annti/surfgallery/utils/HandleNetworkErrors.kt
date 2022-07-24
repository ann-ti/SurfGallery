package com.annti.surfgallery.utils

import android.util.Log
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> handleNetworkErrors(block: suspend () -> T): T =
    try {
        block()
    } catch (e: UnknownHostException) {
        throw AppError(AppError.Code.NETWORK_CONNECTION, "Нет подключения к интернету")
    } catch (e: SocketTimeoutException) {
        throw AppError(AppError.Code.TIMEOUT, "Превышено время ожидания ответа")
    } catch (e: HttpException) {
        when (e.code()) {
            404 -> throw AppError(AppError.Code.SERVER_ERROR, "Ресурс не найден")
            else -> {
                Log.e("LK", "Network error", e)
                throw AppError(AppError.Code.SERVER_ERROR, "Сетевая ошибка: $e")
            }
        }
    } catch (e: AppError) {
        throw e
    } catch (e: Throwable) {
        Log.e("LK", "Unknown error", e)
        throw AppError(AppError.Code.SERVER_ERROR, "Неизвестная ошибка: $e")
    }