package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException

class ConsumeProductOrderException(
    productId: String,
    orderId: String,
    purchaseToken: String,
) : ErrorException(
    code = UserErrorCode.DATA_USER_CONSUME_PRODUCT_ORDER,
    result = mapOf("productId" to productId, "orderId" to orderId, "purchaseToken" to purchaseToken),
)
