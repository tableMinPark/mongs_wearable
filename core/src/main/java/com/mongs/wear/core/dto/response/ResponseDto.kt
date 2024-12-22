package com.mongs.wear.core.dto.response

/**
 * Base Response Dto
 */
data class ResponseDto<T>(

    val code: String?,

    val message: String?,

    val httpStatus: Int?,

    val result: T,
)
