package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class NeedUpdateAppException(buildVersion: String) : ErrorException(
    code = AuthErrorCode.DATA_AUTH_NEED_UPDATE_APP,
    result = Collections.singletonMap("buildVersion", buildVersion),
)
