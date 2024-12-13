package com.mongs.wear.core.exception

class ApiException(

    val code: String,

    val httpStatus: Int,

    override val message: String,

) : RuntimeException() {
}