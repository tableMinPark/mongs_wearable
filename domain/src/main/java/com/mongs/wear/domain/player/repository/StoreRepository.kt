package com.mongs.wear.domain.player.repository

interface StoreRepository {

    suspend fun getProductIds(): List<String>
}