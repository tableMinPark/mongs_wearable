package com.mongs.wear.core.errors

interface ErrorCode {

    fun getMessage() : String

    fun isMessageShow() : Boolean
}