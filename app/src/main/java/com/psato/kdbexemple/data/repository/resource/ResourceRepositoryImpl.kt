package com.psato.kdbexemple.data.repository.resource

import android.app.Application
import com.psato.kdbexemple.R

/**
 * Created by psato on 29/06/16.
 */

class ResourceRepositoryImpl(var devCampApplication: Application) : ResourceRepository {


    override val notFoundShow: String
        get() = devCampApplication.resources.getString(R.string.error)
}
