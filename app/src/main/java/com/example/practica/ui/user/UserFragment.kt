package com.example.practica.ui.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practica.R
import com.example.practica.data.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.row_user.*
import kotlinx.android.synthetic.main.user_fragment.*


@AndroidEntryPoint
class UserFragment : Fragment(R.layout.user_fragment) {


    companion object {
        const val TAG = "UserFragment"
        fun newInstance() = UserFragment()
    }

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.save(User("testfff", 12))


        val adapter = UserListAdapter(layoutInflater)
        user_list.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.user.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.toMutableList())
        })
    }

    inner class UserListAdapter(private val inflater: LayoutInflater) :
        ListAdapter<User, UserListAdapter.UserListViewHolder>(Diff()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
            return UserListViewHolder(layoutInflater.inflate(R.layout.row_user, parent, false))
        }

        override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        //        private val diff = object : DiffUtil.ItemCallback<User>() {
//            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
//                return oldItem == newItem
//            }
//
//        }
        inner class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var name: TextView = view.findViewById(R.id.user_name)
            fun bind(user: User) {
                name.text = user.name
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}