package com.annti.surfgallery.utils

class AppError public constructor(code: Code, message: String) : RuntimeException()  {
    fun code(): Code{
        return Code.valueOf(message!!)
    }

    enum class Code{
        SERVER_ERROR,

        INTERNAL_ERROR,

        TIMEOUT,

        NETWORK_CONNECTION,

        INVALID_LOGIN_OR_PASSWORD,

        SESSION_EXPIRED,

        MESSAGE
    }
}