package devandroid.felipe.usergithubsearch.viewholder

import androidx.recyclerview.widget.RecyclerView
import devandroid.felipe.usergithubsearch.databinding.RepositoryItemBinding
import devandroid.felipe.usergithubsearch.model.RepositoryModel

class RepositoryViewHolder(
    private val item: RepositoryItemBinding
) : RecyclerView.ViewHolder(item.root) {

    fun bind(repository: RepositoryModel) {
        item.repositoryTextProject.text = repository.name
    }
}