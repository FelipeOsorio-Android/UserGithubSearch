package devandroid.felipe.usergithubsearch.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import devandroid.felipe.usergithubsearch.adapter.RepositoryAdapter
import devandroid.felipe.usergithubsearch.data.GitHubService
import devandroid.felipe.usergithubsearch.databinding.FragmentUserSearchBinding
import devandroid.felipe.usergithubsearch.model.RepositoryModel
import devandroid.felipe.usergithubsearch.viewmodel.UserSearchViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserSearchFragment : Fragment(), TextWatcher {

    private var _binding: FragmentUserSearchBinding? = null
    private val binding get() = _binding!!

    private val recyclerView by lazy { binding.fragmentUserRecyclerview }
    private val editUser by lazy { binding.fragmentUserEditText }
    private val buttonSearch by lazy { binding.fragmentUserButtonSearch }
    private val viewModel by viewModels<UserSearchViewModel>()

    private val adapter = RepositoryAdapter()
    private lateinit var githubApi: GitHubService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRetrofit()
        setupAdapter()
        observer()

    }


    override fun onResume() {
        super.onResume()


        editUser.addTextChangedListener(this)

        buttonSearch.setOnClickListener {
            viewModel.getUserGitHub(editUser.text.toString())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val username = editUser.text.toString()
        buttonSearch.isEnabled = username.isNotEmpty() && username.isNotBlank()
    }

    override fun afterTextChanged(s: Editable?) {}

    private fun observer() {
        viewModel.user.observe(viewLifecycleOwner) {
            getListRepository(it)

        }
    }

    private fun setupAdapter() {
        recyclerView.adapter = adapter
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubApi = retrofit.create(GitHubService::class.java)
    }

    private fun getListRepository(user: String) {
        githubApi.getAllRepositoriesByUser(user).enqueue(object : Callback<List<RepositoryModel>> {
            override fun onResponse(
                call: Call<List<RepositoryModel>>, response: Response<List<RepositoryModel>>
            ) {
                when (response.isSuccessful) {
                    true -> response.body()?.let {
                        recyclerView.isVisible = true
                        adapter.getListRepository(it)
                        Log.e("cade2", "observer: $it")
                    }

                    false -> {
                        Snackbar.make(
                            binding.root,
                            "Não Encontrado",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<List<RepositoryModel>>, t: Throwable) {
                Snackbar.make(binding.root, "Erro no Servidor", Snackbar.LENGTH_SHORT).show()
            }

        })
    }


}