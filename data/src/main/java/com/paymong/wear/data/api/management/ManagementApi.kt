package com.paymong.wear.data.api.management

import com.paymong.wear.data.api.management.dto.request.FeedMongReqDto
import com.paymong.wear.data.api.management.dto.response.FindFeedHistoryResDto
import com.paymong.wear.data.api.management.dto.request.RegisterMongReqDto
import com.paymong.wear.data.api.management.dto.response.DeleteMongResDto
import com.paymong.wear.data.api.management.dto.response.EvolutionMongResDto
import com.paymong.wear.data.api.management.dto.response.FeedMongResDto
import com.paymong.wear.data.api.management.dto.response.FindMongResDto
import com.paymong.wear.data.api.management.dto.response.GraduateMongResDto
import com.paymong.wear.data.api.management.dto.response.PoopCleanResDto
import com.paymong.wear.data.api.management.dto.response.RegisterMongResDto
import com.paymong.wear.data.api.management.dto.response.SleepingMongResDto
import com.paymong.wear.data.api.management.dto.response.StrokeMongResDto
import com.paymong.wear.data.api.management.dto.response.TrainingMongResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ManagementApi {
    @GET("/management/feed/{mongId}")
    suspend fun findFeedHistory(@Path("mongId") mongId: Long, @Query("buildVersion") buildVersion: String) : Response<List<FindFeedHistoryResDto>>
    @PUT("/management/feed/{mongId}")
    suspend fun feedMong(@Path("mongId") mongId: Long, @Body feedMongReqDto: FeedMongReqDto) : Response<FeedMongResDto>

    @GET("/management")
    suspend fun findAllMong() : Response<List<FindMongResDto>>
    @POST("/management")
    suspend fun registerMong(@Body registerMongReqDto: RegisterMongReqDto) : Response<RegisterMongResDto>
    @DELETE("/management/{mongId}")
    suspend fun deleteMong(@Path("mongId") mongId: Long) : Response<DeleteMongResDto>
    @PUT("/management/stroke/{mongId}")
    suspend fun strokeMong(@Path("mongId") mongId: Long) : Response<StrokeMongResDto>
    @PUT("/management/sleep/{mongId}")
    suspend fun sleepingMong(@Path("mongId") mongId: Long) : Response<SleepingMongResDto>
    @PUT("/management/poop/{mongId}")
    suspend fun poopClean(@Path("mongId") mongId: Long) : Response<PoopCleanResDto>
    @PUT("/management/training/{mongId}")
    suspend fun trainingMong(@Path("mongId") mongId: Long) : Response<TrainingMongResDto>
    @PUT("/management/graduation/{mongId}")
    suspend fun graduateMong(@Path("mongId") mongId: Long) : Response<GraduateMongResDto>
    @PUT("/management/evolution/{mongId}")
    suspend fun evolutionMong(@Path("mongId") mongId: Long) : Response<EvolutionMongResDto>
}