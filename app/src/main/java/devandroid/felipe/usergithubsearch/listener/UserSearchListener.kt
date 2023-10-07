package devandroid.felipe.usergithubsearch.listener

interface UserSearchListener {

    fun shareRepository(url: String)

    fun openRepository(url: String)
}