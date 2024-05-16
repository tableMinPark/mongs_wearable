package com.paymong.wear.domain.refac.repositroy

interface CodeRepository {
    suspend fun setCodes(codeIntegrity: String, buildVersion: String)
}