package com.mongs.wear.data.client

interface RealTimeRepository {
    suspend fun memberStarPointCallback(dataJson: String)
    suspend fun mongCodeCallback(dataJson: String)
    suspend fun mongExpCallback(dataJson: String)
    suspend fun mongIsSleepingCallback(dataJson: String)
    suspend fun mongPayPointCallback(dataJson: String)
    suspend fun mongPoopCountCallback(dataJson: String)
    suspend fun mongShiftCallback(dataJson: String)
    suspend fun mongStateCallback(dataJson: String)
    suspend fun mongStatusCallback(dataJson: String)
}