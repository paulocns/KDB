package dev.kdblib.kdbexemple.data.repository.show

import dev.kdblib.kdbexemple.data.entity.Rating
import dev.kdblib.kdbexemple.data.entity.ShowInfo
import dev.kdblib.kdbexemple.data.remote.TraktAPI
import java.io.IOException

/**
 * Created by psato on 29/06/16.
 */

class ShowRepositoryImpl(private val traktAPI: TraktAPI) :
    ShowRepository {
    override suspend fun showRating(id: String): Rating {
        return traktAPI.getShowRating(id)
    }

    @Throws(IOException::class)
    override suspend fun searchShow(query: String): List<ShowInfo> {
        return traktAPI.searchForShows(query, 200)
    }
}
