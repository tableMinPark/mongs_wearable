package com.mongs.wear.core.exception

import com.mongs.wear.core.errors.ErrorCode

open class ErrorException(

    open val code: ErrorCode,

    open val result: Map<String, Any>,

    override val message: String = code.getMessage()

) : RuntimeException()