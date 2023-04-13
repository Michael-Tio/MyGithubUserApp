package com.michael.mygithubuserapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.mygithubuserapp.ui.main.ListUserAdapter
import com.michael.mygithubuserapp.databinding.FragmentFollowerFollowingBinding

class FollowerFollowingFragment : Fragment() {

    private var _binding: FragmentFollowerFollowingBinding? = null
    private val binding get() = _binding!!

    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = DetailUserActivity.UNAME
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
        getFolls()
    }

    private fun getFolls(){
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        viewModel.listFollowing(username.toString())
        viewModel.listFollower(username.toString())
        if (position == 1){
            viewModel.follower.observe(viewLifecycleOwner) {
                binding.rvFolls.adapter = ListUserAdapter(it)
            }
        }
        else {
            viewModel.following.observe(viewLifecycleOwner) {
                binding.rvFolls.adapter = ListUserAdapter(it)
            }
        }

        viewModel.isLoadingFolls.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        showRecycleView()
    }

    private fun showRecycleView(){
        binding.rvFolls.apply{
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvFolls.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            binding.rvFolls.addItemDecoration(itemDecoration)
            setHasFixedSize(true)
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBarFolls.visibility = if (state) View.VISIBLE else View.GONE }

    companion object{
        const val ARG_POSITION = "POSITION"
    }
}