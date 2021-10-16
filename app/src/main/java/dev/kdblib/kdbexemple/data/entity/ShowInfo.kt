package dev.kdblib.kdbexemple.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by psato on 29/10/16.
 */

class ShowInfo {

    @SerializedName("type")
    val type: String? = null
    @SerializedName("score")
    val score: String? = null
    @SerializedName("show")
    var show: Show? = null
}
