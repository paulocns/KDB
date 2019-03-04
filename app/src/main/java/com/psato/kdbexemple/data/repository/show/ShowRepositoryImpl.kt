package com.psato.kdbexemple.data.repository.show

import com.psato.kdbexemple.data.entity.Rating
import com.psato.kdbexemple.data.entity.ShowInfo
import com.psato.kdbexemple.data.remote.TraktAPI
import java.io.IOException

/**
 * Created by psato on 29/06/16.
 */

class ShowRepositoryImpl(private val traktAPI: TraktAPI) : ShowRepository {
    override suspend fun showRating(id: String): Rating {
        return traktAPI.getShowRating(id).execute().body()!!
    }

    @Throws(IOException::class)
    override suspend fun searchShow(query: String): List<ShowInfo> {
        return traktAPI.searchForShows(query, 200).execute().body()!!
    }
}
