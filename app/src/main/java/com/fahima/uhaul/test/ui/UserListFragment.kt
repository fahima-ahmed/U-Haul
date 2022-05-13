package com.fahima.uhaul.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahima.uhaul.test.R
import com.fahima.uhaul.test.adapter.UserAdapter
import com.fahima.uhaul.test.databinding.FragmentUserListBinding
import com.fahima.uhaul.test.util.Constants
import com.fahima.uhaul.test.util.Resource
import com.fahima.uhaul.test.viewmodel.UserViewModel


class UserListFragment : Fragment(R.layout.fragment_user_list) {

    lateinit var viewModel: UserViewModel
    lateinit var userAdapter: UserAdapter
    lateinit var binding: FragmentUserListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }


    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner, { users ->
            when (users) {
                is Resource.Success -> {
                    userAdapter.differ.submitList(users.data)
                    hideProgressBar()
                    hideErrorMessage()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    users.message?.let { message ->
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

        userAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.USER_ID, it.id)
            }
            findNavController().navigate(
                R.id.action_userListFragment_to_userPostsFragment,
                bundle
            )
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
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








