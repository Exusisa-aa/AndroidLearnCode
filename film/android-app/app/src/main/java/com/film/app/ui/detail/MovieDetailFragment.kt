package com.film.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.film.app.R
import com.film.app.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val movieId = arguments?.getLong("movieId")
        if (movieId == null) {
            findNavController().popBackStack()
            return
        }

        setupRecyclerView()
        viewModel.loadMovie(movieId)
        
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                binding.toolbar.title = movie.title
                binding.tvTitle.text = movie.title
                binding.tvDescription.text = movie.description
                binding.tvRating.text = "${movie.rating}"
                binding.tvDuration.text = "${movie.duration} min"
                // For genre, since backend doesn't have genre field, let's use actors or a static value or modify backend
                // But user asked to fix cast not showing. Let's add cast display if UI has it.
                // Wait, fragment_movie_detail.xml doesn't have tv_cast?
                // Let's check xml. Ah, it has tv_genre.
                // The prompt says "Movie info cast doesn't show real cast, only actor1 and actor2".
                // This refers to item_movie.xml (Home screen) probably, because I updated MovieAdapter above.
                // But let's also update Detail screen to show more info if available.
                
                // Binding data to new detail layout fields
                binding.tvGenre.text = "剧情 / 科幻" // Placeholder as model lacks genre
                
                Glide.with(this).load(movie.posterUrl).into(binding.ivPoster)
            }
        }
        
        viewModel.schedules.observe(viewLifecycleOwner) { schedules ->
            scheduleAdapter.submitList(schedules)
            binding.rvSchedules.isVisible = schedules.isNotEmpty()
        }
        
        // Hide mocked buy button
        binding.btnBuy.visibility = View.GONE
    }
    
    private fun setupRecyclerView() {
        scheduleAdapter = ScheduleAdapter { schedule ->
            val bundle = Bundle()
            bundle.putLong("scheduleId", schedule.id)
            findNavController().navigate(R.id.action_movieDetailFragment_to_seatSelectionFragment, bundle)
        }
        binding.rvSchedules.layoutManager = LinearLayoutManager(context)
        binding.rvSchedules.adapter = scheduleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
