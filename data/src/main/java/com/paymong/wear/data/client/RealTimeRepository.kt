package com.paymong.wear.data.client

interface RealTimeRepository {
    suspend fun memberStarPointCallback(json: String)
    suspend fun mongCodeCallback(json: String)
    suspend fun mongExpCallback(json: String)
    suspend fun mongIsSleepingCallback(json: String)
    suspend fun mongPayPointCallback(json: String)
    suspend fun mongPoopCountCallback(json: String)
    suspend fun mongShiftCallback(json: String)
    suspend fun mongStateCallback(json: String)
    suspend fun mongStatusCallback(json: String)
}