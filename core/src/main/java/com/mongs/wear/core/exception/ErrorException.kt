package com.mongs.wear.core.exception

open class ErrorException(

    override val message: String,

    open val result: Map<String, String>

) : RuntimeException()