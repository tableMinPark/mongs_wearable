package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.domain.auth.exception.NotExistsEmailException
import com.mongs.wear.domain.auth.exception.NotExistsGoogleAccountIdException
import com.mongs.wear.domain.auth.exception.NotExistsNameException
import com.mongs.wear.domain.auth.repository.AuthRepository
import javax.inject.Inject

class JoinUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(googleAccountId: String?, email: String?, name: String?) {

        if (email.isNullOrEmpty()) throw NotExistsEmailException()

        if (name.isNullOrEmpty()) throw NotExistsNameException()

        if (googleAccountId.isNullOrEmpty()) throw NotExistsGoogleAccountIdException()

        authRepository.join(email = email, name = name, googleAccountId = googleAccountId)
    }
}