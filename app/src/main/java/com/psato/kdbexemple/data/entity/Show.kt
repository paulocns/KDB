package com.psato.kdbexemple.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by psato on 29/10/16.
 */

class Show {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("year")
    val year: Int? = null
    @SerializedName("ids")
    var ids: ShowIds? = null
}
