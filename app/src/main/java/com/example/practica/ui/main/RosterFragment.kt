package com.example.practica.ui.main


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practica.R
import com.example.practica.data.entity.User
import com.example.practica.databinding.RowUserBinding
import com.example.practica.ui.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.roster_fragment.*


@AndroidEntryPoint
//class UserFragment constructor(private val userId: Int) : Fragment(R.layout.roster_fragment) {
class RosterFragment: Fragment(R.layout.roster_fragment) {

    companion object {
        const val TAG = "UserFragment"
        const val USER_ID = "id"
        fun newInstance() = RosterFragment()
    }

    private val viewModel: RosterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserListAdapter { user -> replace(user) }
        user_list.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.listUser.observe(viewLifecycleOwner) { adapter.submitList(it.toMutableList()) }

        fb_add_user.setOnClickListener { replace(null) }
    }

    private fun replace(user: User?) {
        activity?.supportFragmentManager?.commit {
            replace(R.id.container, UserFragment.newInstance(user))
        }
    }

    inner class UserListAdapter(
        private val action: (User) -> Unit
    ): ListAdapter<User, UserListAdapter.UserListViewHolder>(Diff()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
            return UserListViewHolder(RowUserBinding.inflate(layoutInflater, parent, false), action)
        }

        override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        inner class UserListViewHolder(
            private val binding: RowUserBinding,
            val action: (User) -> Unit
        ): RecyclerView.ViewHolder(binding.root) {
            fun bind(user: User) {
                binding.user = user
                binding.holder = this
                binding.executePendingBindings()
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}