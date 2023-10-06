package devandroid.felipe.usergithubsearch.model

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    val nameRepository: String,
    @SerializedName("html_url")
    val url: String
)