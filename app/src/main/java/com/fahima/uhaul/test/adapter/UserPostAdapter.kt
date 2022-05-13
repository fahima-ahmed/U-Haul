package com.fahima.uhaul.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahima.uhaul.test.R
import com.fahima.uhaul.test.model.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserPostAdapter : RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder>() {

    inner class UserPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemDeleteClickListener: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        return UserPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_post_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        val userPost = differ.currentList[position]
        holder.itemView.apply {
            this.findViewById<TextView>(R.id.tvTitle).text = userPost.title
            this.findViewById<TextView>(R.id.tvBody).text = userPost.body
            this.findViewById<FloatingActionButton>(R.id.buttonDelete).setOnClickListener {
                onItemDeleteClickListener?.let { it(userPost) }
            }
        }
    }

    fun setOnItemDeleteClickListener(listener: (Post) -> Unit) {
        onItemDeleteClickListener = listener
    }
}













