package dev.kdblib.kdbexemple.data.remote

import dev.kdblib.kdbexemple.data.entity.Rating
import dev.kdblib.kdbexemple.data.entity.ShowInfo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by psato on 29/10/16.
 */

interface TraktAPI {
    @Headers(
        APIConstants.HEADER_API_VERSION + ": 2",
            APIConstants.HEADER_CLIENT_ID + ": " + APIConstants.CLIENT_ID
    )
    @GET("search/show")
    suspend fun searchForShows(@Query("query") query: String, @Query("limit") limit: Int): List<ShowInfo>

    @Headers(
        APIConstants.HEADER_API_VERSION + ": 2",
            APIConstants.HEADER_CLIENT_ID + ": " + APIConstants.CLIENT_ID
    )
    @GET("shows/{id}/ratings")
    suspend fun getShowRating(@Path("id") id: String): Rating
}
