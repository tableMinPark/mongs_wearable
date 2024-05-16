package com.paymong.wear.domain.refac.repositroy

interface SlotRepository {
    suspend fun setSlots(accountId: Long)
}