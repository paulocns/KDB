package com.psato.kdbexemple.interactor.usecase.show

import com.psato.kdbexemple.data.entity.Rating
import com.psato.kdbexemple.data.entity.ShowResponse
import com.psato.kdbexemple.data.repository.show.ShowRepository
import com.psato.kdbexemple.interactor.usecase.UseCase

/**
 * Created by psato on 29/10/16.
 */

open class SearchShows
constructor(private val showRepository: ShowRepository) :
        UseCase<List<ShowResponse>>() {

    lateinit var query: String

    public override suspend fun executeOnBackground(): List<ShowResponse> {
        return showRepository.searchShow(query).map {
            runAsync {
                val rating: Rating = showRepository.showRating(it.show!!.ids!!.trakt!!)
                ShowResponse(it.show!!.title!!, rating.rating)
            }
        }.map {
            it.await()
        }
    }
}
