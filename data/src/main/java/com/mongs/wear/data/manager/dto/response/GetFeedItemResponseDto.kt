package com.mongs.wear.data.manager.dto.response

import com.mongs.wear.data.manager.dto.etc.GetFeedItemDto

data class GetFeedItemResponseDto(

    val mongId: Long,

    val feedItems: List<GetFeedItemDto>,
)
