package devandroid.felipe.usergithubsearch.model

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String
)