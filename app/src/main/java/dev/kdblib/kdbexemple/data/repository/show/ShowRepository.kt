package dev.kdblib.kdbexemple.data.repository.show

import dev.kdblib.kdbexemple.data.entity.Rating
import dev.kdblib.kdbexemple.data.entity.ShowInfo
import java.io.IOException

/**
 * Created by psato on 29/06/16.
 */

interface ShowRepository {
    suspend fun searchShow(query: String): List<ShowInfo>

    suspend fun showRating(id: String): Rating
}
