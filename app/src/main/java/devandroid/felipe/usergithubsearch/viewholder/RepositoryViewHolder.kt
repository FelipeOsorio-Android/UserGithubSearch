package devandroid.felipe.usergithubsearch.viewholder

import androidx.recyclerview.widget.RecyclerView
import devandroid.felipe.usergithubsearch.databinding.RepositoryItemBinding
import devandroid.felipe.usergithubsearch.model.RepositoryModel

class RepositoryViewHolder(
    private val view: RepositoryItemBinding
) : RecyclerView.ViewHolder(view.root) {

    fun bind(repository: RepositoryModel) {
        view.repositoryTextProject.text = repository.nameRepository
    }
}