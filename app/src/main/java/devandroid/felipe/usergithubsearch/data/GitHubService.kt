package devandroid.felipe.usergithubsearch.data

import devandroid.felipe.usergithubsearch.model.RepositoryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{user}/repos")
    fun getAllRepositoryByUser(@Path("user") user: String) : Call<List<RepositoryModel>>
}