package com.example.teamedup.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentHomeBinding
import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GameRecyclerViewClickListener
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), GameRecyclerViewClickListener{
    private lateinit var _binding : FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var gameAdapter : GameAdapter
    private lateinit var gameSearchAdapter : GameSearchAdapter
    private lateinit var viewPagerAdapter: ContentViewPagerAdapter
    private lateinit var searchedGames : List<Game>
    private val sharedViewModel : SharedViewModel by activityViewModels()

    init {
        Log.d(TAG, "INITHOMEFRAGMENT: ${GlobalConstant.ATHENTICATION_TOKEN}")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restartManager()
        setUpAddButton()
        setUpProfileButton()
        getData()
        setupGameRecyclerView()
        setupGameSearchRecyclerView()
        setupViewPager()
    }

    private fun setupViewPager(){
        viewPagerAdapter = ContentViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        with(binding){
            vpContent.adapter = viewPagerAdapter

            TabLayoutMediator(tlContentDivider, vpContent){ tab, position ->
                when(position){
                    0 -> tab.text = getString(R.string.competition_tab)
                    1 -> tab.text = getString(R.string.forum_tab)
                }
            }.attach()
        }
    }

    private fun setupGameRecyclerView(){
        binding.rvGameList.apply {
            gameAdapter = GameAdapter()
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            gameAdapter.gameListener = this@HomeFragment
        }
    }

    private fun setupGameSearchRecyclerView(){
        binding.apply {
            rvSearchedGame.apply {
                setHasFixedSize(true)
                gameSearchAdapter = GameSearchAdapter()
                adapter = gameSearchAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
                gameSearchAdapter.gameListener = this@HomeFragment
            }
            binding.etSearch.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(searchedGame: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filterList(searchedGame.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}

            })
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setRestart(false)
    }

    private fun getData(){
        lifecycleScope.launch {
            binding.pbGameList.isVisible = true
            val response = try {
                RetrofitInstances.api.getGame(GlobalConstant.ATHENTICATION_TOKEN)
            } catch (e : IOException){
                Log.d("HomeFragment", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("HomeFragment", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                gameAdapter.games = response.body()!!.data
                gameSearchAdapter.games = response.body()!!.data
                searchedGames = response.body()!!.data
                Log.d(TAG, "setupRecyclerView: ${gameAdapter.games}")
            }else{
                Log.d("HomeFragment", "Response no successful")
            }
            binding.pbGameList.isVisible = false
        }
    }



    private fun setUpAddButton(){
        binding.btnAdding.setOnClickListener {
            sharedViewModel.tab.observe(viewLifecycleOwner){tab ->
                when(tab){
                    "Forum" -> findNavController().navigate(R.id.action_homeFragment_to_createForumFragment)
                    "Tournament" -> findNavController().navigate(R.id.action_homeFragment_to_createCompetitionFragment)
                }
            }
        }
    }

    private fun setUpProfileButton(){
        binding.apply {
            llProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }
    }

    private fun restartManager(){
        sharedViewModel.restartHandler.observe(viewLifecycleOwner){
            when(it){
                true -> { sharedViewModel.setGame(sharedViewModel.game.value!!) }
                false -> {}
            }
        }
    }

    private fun filterList(query: String) {
            binding.rvSearchedGame.visibility = View.VISIBLE
            val filteredList = ArrayList<Game>()
            for (searchedGameLetter in searchedGames) {
                if (searchedGameLetter.name.lowercase(Locale.ROOT).contains(query.lowercase())) {
                    filteredList.add(searchedGameLetter)
                }
            }
            gameSearchAdapter.setFilteredGameList(filteredList)
    }

    override fun onItemClicked(view: View, game: Game) {
        Log.d("HomeFragment", "onItemClicked: name: ${game.name} id: ${game.id}")
        sharedViewModel.setGame(game.id)
        binding.rvSearchedGame.visibility = View.GONE
    }
}