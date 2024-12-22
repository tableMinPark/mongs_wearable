package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode
import java.util.Collections

class EvolutionMongException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.MANAGER_EVOLUTION_MONG,
    result = Collections.singletonMap("mongId", mongId),
)