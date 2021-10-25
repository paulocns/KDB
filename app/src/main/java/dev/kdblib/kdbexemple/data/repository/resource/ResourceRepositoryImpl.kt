package dev.kdblib.kdbexemple.data.repository.resource

import android.app.Application
import dev.kdblib.kdbexemple.R

/**
 * Created by psato on 29/06/16.
 */

class ResourceRepositoryImpl(var devCampApplication: Application) : ResourceRepository {


    override val notFoundShow: String
        get() = devCampApplication.resources.getString(R.string.error)
}
