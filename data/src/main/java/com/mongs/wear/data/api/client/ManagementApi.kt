package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.management.req.FeedMongReqDto
import com.mongs.wear.data.dto.management.res.FindFeedLogResDto
import com.mongs.wear.data.dto.management.req.RegisterMongReqDto
import com.mongs.wear.data.dto.management.req.TrainingMongReqDto
import com.mongs.wear.data.dto.management.res.DeleteMongResDto
import com.mongs.wear.data.dto.management.res.EvolutionMongResDto
import com.mongs.wear.data.dto.management.res.FeedMongResDto
import com.mongs.wear.data.dto.management.res.FindMongResDto
import com.mongs.wear.data.dto.management.res.GraduateMongResDto
import com.mongs.wear.data.dto.management.res.PoopCleanMongResDto
import com.mongs.wear.data.dto.management.res.RegisterMongResDto
import com.mongs.wear.data.dto.management.res.SleepingMongResDto
import com.mongs.wear.data.dto.management.res.StrokeMongResDto
import com.mongs.wear.data.dto.management.res.TrainingMongResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagementApi {
    @GET("/management/{mongId}")
    suspend fun findMong(@Path("mongId") mongId: Long) : Response<FindMongResDto>
    @GET("/management")
    suspend fun findMong() : Response<List<FindMongResDto>>
    @POST("/management")
    suspend fun registerMong(@Body registerMongReqDto: RegisterMongReqDto) : Response<RegisterMongResDto>
    @DELETE("/management/{mongId}")
    suspend fun deleteMong(@Path("mongId") mongId: Long) : Response<DeleteMongResDto>
    @PUT("/management/stroke/{mongId}")
    suspend fun strokeMong(@Path("mongId") mongId: Long) : Response<StrokeMongResDto>
    @PUT("/management/sleep/{mongId}")
    suspend fun sleepingMong(@Path("mongId") mongId: Long) : Response<SleepingMongResDto>
    @PUT("/management/poopClean/{mongId}")
    suspend fun poopCleanMong(@Path("mongId") mongId: Long) : Response<PoopCleanMongResDto>
    @PUT("/management/training/{mongId}")
    suspend fun trainingMong(@Path("mongId") mongId: Long, @Body trainingMongReqDto: TrainingMongReqDto) : Response<TrainingMongResDto>
    @PUT("/management/graduate/{mongId}")
    suspend fun graduateMong(@Path("mongId") mongId: Long) : Response<GraduateMongResDto>
    @PUT("/management/evolution/{mongId}")
    suspend fun evolutionMong(@Path("mongId") mongId: Long) : Response<EvolutionMongResDto>
    @GET("/management/feed/{mongId}")
    suspend fun findFeedLog(@Path("mongId") mongId: Long) : Response<List<FindFeedLogResDto>>
    @POST("/management/feed/{mongId}")
    suspend fun feedMong(@Path("mongId") mongId: Long, @Body feedMongReqDto: FeedMongReqDto) : Response<FeedMongResDto>
}