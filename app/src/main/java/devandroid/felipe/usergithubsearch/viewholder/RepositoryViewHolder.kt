package devandroid.felipe.usergithubsearch.viewholder

import androidx.recyclerview.widget.RecyclerView
import devandroid.felipe.usergithubsearch.databinding.RepositoryItemBinding
import devandroid.felipe.usergithubsearch.listener.UserSearchListener
import devandroid.felipe.usergithubsearch.model.RepositoryModel

class RepositoryViewHolder(
    private val itemBinding: RepositoryItemBinding,
    private val listener: UserSearchListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(repository: RepositoryModel) {
        itemBinding.repositoryTextProject.text = repository.name

        itemBinding.repositoryImageShare.setOnClickListener {
            listener.shareRepository(repository.htmlUrl)
        }

        itemBinding.repositoryImageProject.setOnClickListener {
            listener.openRepository(repository.htmlUrl)
        }
    }
}