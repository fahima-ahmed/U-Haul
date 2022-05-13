package com.fahima.uhaul.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahima.uhaul.test.R
import com.fahima.uhaul.test.adapter.UserPostAdapter
import com.fahima.uhaul.test.databinding.FragmentUserPostBinding
import com.fahima.uhaul.test.util.Resource
import com.fahima.uhaul.test.viewmodel.UserPostViewModel


class UserPostFragment : Fragment(R.layout.fragment_user_post) {

    lateinit var viewModel: UserPostViewModel
    lateinit var userPostAdapter: UserPostAdapter
    lateinit var binding: FragmentUserPostBinding
    private val args: UserPostFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, (activity as MainActivity).viewModelProviderFactory)[UserPostViewModel::class.java]
        setupRecyclerView()
        setupObservers(args.userId)
        binding.buttonSort.setOnClickListener {
            viewModel.sortPost()
        }
        userPostAdapter.setOnItemDeleteClickListener {
            viewModel.deletePost(it)
        }
    }

    private fun setupRecyclerView() {
        userPostAdapter = UserPostAdapter()
        binding.rvUserPost.apply {
            adapter = userPostAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupObservers(userId: Int) {
        viewModel.getUserPosts(userId)
        viewModel.userPosts.observe(viewLifecycleOwner, { userPosts ->
            when (userPosts) {
                is Resource.Success -> {
                    userPostAdapter.differ.submitList(userPosts.data)
                    hideProgressBar()
                    hideErrorMessage()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    userPosts.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.tvError.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }

}








