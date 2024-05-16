package com.paymong.wear.data.repository_.notification.callback

interface CallbackRepository {
    suspend fun createCallback(json: String)
    suspend fun deadCallback(json: String)
    suspend fun deleteCallback(json: String)
    suspend fun evolutionReadyCallback(json: String)
    suspend fun evolutionCallback(json: String)
    suspend fun feedCallback(json: String)
    suspend fun graduationReadyCallback(json: String)
    suspend fun graduationCallback(json: String)
    suspend fun healthyCallback(json: String)
    suspend fun poopCallback(json: String)
    suspend fun satietyCallback(json: String)
    suspend fun sleepingCallback(json: String)
    suspend fun sleepCallback(json: String)
    suspend fun stateCallback(json: String)
    suspend fun strengthCallback(json: String)
    suspend fun strokeCallback(json: String)
    suspend fun trainingCallback(json: String)
    suspend fun weightCallback(json: String)
    suspend fun attendanceCallback(json: String)
}