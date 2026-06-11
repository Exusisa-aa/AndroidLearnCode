package com.film.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.film.app.R
import com.film.app.databinding.FragmentHomeBinding
import com.film.app.ui.adapter.MovieAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

import com.film.app.utils.UserSession
import androidx.appcompat.widget.PopupMenu

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_login -> {
                    handleLoginAction(binding.toolbar.findViewById(R.id.action_login) ?: binding.toolbar)
                    true
                }
                else -> false
            }
        }

        setupViewPager()
    }
    
    private fun handleLoginAction(view: View) {
        if (UserSession.isLoggedIn()) {
            // Show popup menu for logged in user
            val popup = PopupMenu(requireContext(), view)
            popup.menu.add("我的订单")
            popup.menu.add("退出登录")
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.title) {
                    "我的订单" -> {
                        findNavController().navigate(R.id.action_homeFragment_to_orderHistoryFragment)
                        true
                    }
                    "退出登录" -> {
                        UserSession.logout()
                        android.widget.Toast.makeText(requireContext(), "已退出登录", android.widget.Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        } else {
            // Navigate to Login
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun setupViewPager() {
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2
            override fun createFragment(position: Int): Fragment {
                return MovieListFragment.newInstance(if (position == 0) 1 else 2)
            }
        }
        binding.viewPager.adapter = adapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_hot)
                1 -> getString(R.string.tab_coming)
                else -> ""
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private lateinit var movieAdapter: MovieAdapter
    private var status: Int = 1

    companion object {
        fun newInstance(status: Int): MovieListFragment {
            val fragment = MovieListFragment()
            val args = Bundle()
            args.putInt("status", status)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter { movie ->
            val bundle = Bundle()
            bundle.putLong("movieId", movie.id)
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
        }
        recyclerView.adapter = movieAdapter
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        status = arguments?.getInt("status") ?: 1

        if (status == 1) {
            viewModel.hotMovies.observe(viewLifecycleOwner) { movies ->
                movieAdapter.submitList(movies)
            }
        } else {
            viewModel.comingMovies.observe(viewLifecycleOwner) { movies ->
                movieAdapter.submitList(movies)
            }
        }
    }
}
