package devandroid.felipe.usergithubsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devandroid.felipe.usergithubsearch.databinding.RepositoryItemBinding
import devandroid.felipe.usergithubsearch.listener.UserSearchListener
import devandroid.felipe.usergithubsearch.model.RepositoryModel
import devandroid.felipe.usergithubsearch.viewholder.RepositoryViewHolder

class RepositoryAdapter : RecyclerView.Adapter<RepositoryViewHolder>() {

    private var listRepository: List<RepositoryModel> = arrayListOf()
    private lateinit var itemListener: UserSearchListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(view, itemListener)
    }

    override fun getItemCount(): Int = listRepository.count()

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(listRepository[position])
    }

    fun getList(list: List<RepositoryModel>) {
        listRepository = list
        notifyDataSetChanged()
    }

    fun getListener(listener: UserSearchListener) {
        itemListener = listener
    }
}