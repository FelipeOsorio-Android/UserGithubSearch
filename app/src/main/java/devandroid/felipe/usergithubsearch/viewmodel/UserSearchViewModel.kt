package devandroid.felipe.usergithubsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserSearchViewModel : ViewModel() {

    private val _user =  MutableLiveData<String>()
    val user: LiveData<String> = _user

    fun getUserGitHub(user: String) {
        _user.value = user
    }
}