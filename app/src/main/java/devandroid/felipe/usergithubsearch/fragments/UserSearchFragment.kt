package devandroid.felipe.usergithubsearch.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import devandroid.felipe.usergithubsearch.R
import devandroid.felipe.usergithubsearch.adapter.RepositoryAdapter
import devandroid.felipe.usergithubsearch.constants.BASE_URL
import devandroid.felipe.usergithubsearch.data.GitHubService
import devandroid.felipe.usergithubsearch.databinding.FragmentUserSearchBinding
import devandroid.felipe.usergithubsearch.listener.UserSearchListener
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
    private lateinit var listener: UserSearchListener
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
        setupListener()
        observer()
    }


    override fun onResume() {
        super.onResume()

        editUser.addTextChangedListener(this)

        buttonSearch.setOnClickListener {
            hideKey(it)
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

    override fun afterTextChanged(s: Editable?) {
        recyclerView.isVisible = false
    }

    private fun setupListener() {
        listener = object : UserSearchListener {
            override fun shareRepository(url: String) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            override fun openRepository(url: String) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                    )
                )
            }

        }
        adapter.getListener(listener)
    }

    private fun observer() {
        viewModel.user.observe(viewLifecycleOwner) {
            getListRepository(it)
        }
    }

    private fun hideKey(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupAdapter() {
        recyclerView.adapter = adapter
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
                        adapter.getList(it)
                    } ?: Snackbar.make(
                        binding.root,
                        getString(R.string.user_null),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()

                    false -> {
                        recyclerView.isVisible = false
                        Snackbar.make(
                            binding.root,
                            getString(R.string.not_found),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<List<RepositoryModel>>, t: Throwable) {
                Snackbar.make(binding.root, getString(R.string.server_down), Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
    }

}