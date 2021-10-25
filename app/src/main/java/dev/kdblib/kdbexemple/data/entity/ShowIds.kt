package dev.kdblib.kdbexemple.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by psato on 29/10/16.
 */

class ShowIds {
    @SerializedName("trakt")
    var trakt: String? = null
    @SerializedName("slug")
    val slug: String? = null
    @SerializedName("tvdb")
    val tvdb: String? = null
    @SerializedName("imdb")
    val imdb: String? = null
    @SerializedName("tmdb")
    val tmdb: String? = null
    @SerializedName("tvrage")
    val tvrage: String? = null

}
