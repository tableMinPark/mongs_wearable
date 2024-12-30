package com.mongs.wear.presentation.common.manager

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryProductDetails
import com.mongs.wear.presentation.common.exception.BillingConnectException
import com.mongs.wear.presentation.common.exception.BillingNotSupportException
import com.mongs.wear.presentation.common.exception.GetProductException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Collections
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PurchasesUpdatedListener {

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .build())
        .build()

    /**
     * 구매 성공 콜백
     */
    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        Log.d("test", "${billingResult}")
        if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
            CoroutineScope(Dispatchers.IO).launch {
                for (purchase in purchases) {
                    Log.d("TEST", "billing: ${purchase.purchaseToken}")

                    val consumeResult = this@BillingManager.consumeProduct(purchase)
                    Log.d("TEST", "consume: ${consumeResult.purchaseToken}")
                }
            }
        }
    }

    /**
     * BillingClient 연결
     */
    suspend fun connect(): BillingResult {
        return suspendCancellableCoroutine { cont ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingResponseCode.OK) {
                        cont.resume(billingResult)
                    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        cont.resumeWithException(BillingNotSupportException())
                    } else {
                        cont.resumeWithException(BillingConnectException())
                    }
                }

                override fun onBillingServiceDisconnected() {}
            })
        }
    }

    /**
     * 상품 목록 조회 (Google)
     */
    suspend fun getProductList(productIds: List<String>) : List<ProductVo> {

        val productList = productIds.map { productId ->
            Product.newBuilder()
                .setProductId(productId)
                .setProductType(ProductType.INAPP)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        return withContext(Dispatchers.IO) {
            val productDetailsResult = billingClient.queryProductDetails(params)
            if (productDetailsResult.billingResult.responseCode == BillingResponseCode.OK) {
                productDetailsResult.productDetailsList?.let { productDetailsList ->
                    productDetailsList.map { productDetail ->
                        ProductVo(
                            productId = productDetail.productId,
                            productName = productDetail.name,
                            price = productDetail.oneTimePurchaseOfferDetails?.formattedPrice ?: ""
                        )
                    }
                } ?: run {
                    Collections.emptyList()
                }
            } else {
                throw GetProductException()
            }
        }
    }

    /**
     * 상품 구매 처리
     */
    suspend fun payProduct(activity: Activity, productId: String) {

        val productList = listOf(
            Product.newBuilder()
                .setProductId(productId)
                .setProductType(ProductType.INAPP)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        val productDetailsResult = billingClient.queryProductDetails(params)

        if (productDetailsResult.billingResult.responseCode == BillingResponseCode.OK) {
            productDetailsResult.productDetailsList?.let { productDetailsList ->

                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                        .setProductDetails(productDetailsList.get(0))
                        // For One-time products, "setOfferToken" method shouldn't be called.
                        // For subscriptions, to get an offer token, call ProductDetails.subscriptionOfferDetails()
                        // for a list of offers that are available to the user
                        //                        .setOfferToken(selectedOfferToken)
                        .build()
                )

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()

                billingClient.launchBillingFlow(activity, billingFlowParams)
            }
        }
    }

    /**
     * 상품 소비 처리
     */
    private suspend fun consumeProduct(purchase : Purchase): ConsumeResult {

        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        return withContext(Dispatchers.IO) {
            billingClient.consumePurchase(consumeParams)
        }
    }

    data class ProductVo(

        val productId: String,

        val productName: String,

        val price: String,
    )
}