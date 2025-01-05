package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class ConsumeProductOrderException(
    productId: String,
    orderId: String,
    purchaseToken: String,
) : ErrorException(
    code = DataErrorCode.DATA_USER_STORE_CONSUME_PRODUCT_ORDER,
    result = mapOf("productId" to productId, "orderId" to orderId, "purchaseToken" to purchaseToken),
)
