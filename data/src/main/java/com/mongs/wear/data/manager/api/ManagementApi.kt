package com.mongs.wear.data.manager.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.manager.dto.request.CreateMongRequestDto
import com.mongs.wear.data.manager.dto.request.FeedMongRequestDto
import com.mongs.wear.data.manager.dto.response.GetFeedItemResponseDto
import com.mongs.wear.data.manager.dto.response.GetMongResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ManagementApi {

    @GET("/manager/management")
    suspend fun getMongs() : Response<ResponseDto<List<GetMongResponseDto>>>

    @GET("/manager/management/{mongId}")
    suspend fun getMong(@Path("mongId") mongId: Long) : Response<ResponseDto<GetMongResponseDto>>

    @GET("/manager/management/feed/{mongId}")
    suspend fun getFeedItems(@Path("mongId") mongId: Long, @Query("foodTypeGroupCode") foodTypeGroupCode: String) : Response<ResponseDto<GetFeedItemResponseDto>>

    @POST("/manager/management/{mongId}")
    suspend fun createMong(@Body createMongRequestDto: CreateMongRequestDto) : Response<ResponseDto<Void>>

    @DELETE("/manager/management/{mongId}")
    suspend fun deleteMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>

    @POST("/manager/management/feed/{mongId}")
    suspend fun feedMong(@Path("mongId") mongId: Long, @Body feedMongRequestDto: FeedMongRequestDto) : Response<ResponseDto<Void>>

    @POST("/manager/management/stroke/{mongId}")
    suspend fun strokeMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>

    @PUT("/manager/management/sleep/{mongId}")
    suspend fun sleepMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>

    @POST("/manager/management/poopClean/{mongId}")
    suspend fun poopCleanMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>

    @PUT("/manager/management/evolution/{mongId}")
    suspend fun evolutionMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>

    @PUT("/manager/management/graduate/{mongId}")
    suspend fun graduateMong(@Path("mongId") mongId: Long) : Response<ResponseDto<Void>>
}