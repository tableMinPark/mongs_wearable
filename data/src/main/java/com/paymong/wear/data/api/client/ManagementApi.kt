package com.paymong.wear.data.api.client

import com.paymong.wear.data.dto.management.request.FeedMongReqDto
import com.paymong.wear.data.dto.management.response.FindFeedLogResDto
import com.paymong.wear.data.dto.management.request.RegisterMongReqDto
import com.paymong.wear.data.dto.management.request.TrainingMongReqDto
import com.paymong.wear.data.dto.management.response.DeleteMongResDto
import com.paymong.wear.data.dto.management.response.EvolutionMongResDto
import com.paymong.wear.data.dto.management.response.FeedMongResDto
import com.paymong.wear.data.dto.management.response.FindMongResDto
import com.paymong.wear.data.dto.management.response.GraduateMongResDto
import com.paymong.wear.data.dto.management.response.PoopCleanMongResDto
import com.paymong.wear.data.dto.management.response.RegisterMongResDto
import com.paymong.wear.data.dto.management.response.SleepingMongResDto
import com.paymong.wear.data.dto.management.response.StrokeMongResDto
import com.paymong.wear.data.dto.management.response.TrainingMongResDto
import com.paymong.wear.data.dto.management.response.ValidationTrainingMongResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ManagementApi {
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
    @GET("/management/validationTraining/{mongId}")
    suspend fun validationTrainingMong(@Path("mongId") mongId: Long, @Query("trainingCode") trainingCode: String) : Response<ValidationTrainingMongResDto>
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