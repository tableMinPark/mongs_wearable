package com.mongs.wear.data_.manager.dto.response

import com.mongs.wear.data_.manager.dto.etc.GetFeedItemDto

data class GetFeedItemResponseDto(

    val mongId: Long,

    val feedItems: List<GetFeedItemDto>,
)
