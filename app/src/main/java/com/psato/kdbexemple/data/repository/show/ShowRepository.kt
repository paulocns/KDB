package com.psato.kdbexemple.data.repository.show

import com.psato.kdbexemple.data.entity.Rating
import com.psato.kdbexemple.data.entity.ShowInfo

import java.io.IOException

/**
 * Created by psato on 29/06/16.
 */

interface ShowRepository {
    @Throws(IOException::class)
    suspend fun searchShow(query: String): List<ShowInfo>

    @Throws(IOException::class)
    suspend fun showRating(id: String): Rating
}
