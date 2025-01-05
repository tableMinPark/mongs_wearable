package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class NeedUpdateAppException(buildVersion: String) : ErrorException(
    code = DataErrorCode.DATA_AUTH_NEED_UPDATE_APP,
    result = Collections.singletonMap("buildVersion", buildVersion),
)
