package com.psato.kdbexemple.interactor.usecase.show

import com.psato.kdbexemple.data.entity.Rating
import com.psato.kdbexemple.data.entity.ShowResponse
import com.psato.kdbexemple.data.repository.show.ShowRepository
import com.psato.kdbexemple.usecase.UseCase

/**
 * Created by psato on 29/10/16.
 */

open class SearchShows
constructor(private val showRepository: ShowRepository) :
        UseCase<String,List<ShowResponse>>() {
    override suspend fun executeOnBackground(request: String): List<ShowResponse> {
        return showRepository.searchShow(request).map {
            runAsync {
                val rating: Rating = showRepository.showRating(it.show!!.ids!!.trakt!!)
                ShowResponse(it.show!!.title!!, rating.rating)
            }
        }.map {
            it.await()
        }
    }
}
