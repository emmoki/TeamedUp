package com.example.teamedup.home.competition.createCompetition

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentCreateCompetitionBinding
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.home.forum.createForum.SuccessCreateDialog
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.PictureRelatedTools
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CreateCompetitionFragment : Fragment() {
    private lateinit var _binding : FragmentCreateCompetitionBinding
    private val binding get() = _binding
    private val tierItem = listOf("Pro", "Amateur")
    private val typeItem = listOf("Team", "Solo")
    private val personEachTeamItem = listOf(1, 2, 3, 4, 5)
    private lateinit var adapterTierItem : ArrayAdapter<String>
    private lateinit var adapterTypeItem : ArrayAdapter<String>
    private lateinit var adapterPersonEachTeamItem : ArrayAdapter<Int>
    private val viewModel : CreateTournamentViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private lateinit var tournamentType : Any
    private lateinit var tournamentTier : Any
    private lateinit var tournamentPersonEachTeam : Any
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateCompetitionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTierDropdownList()
        setupTypeDropdownList()
        setupPersonEachTeamDropdownList()
        setupAddIcon()
        setupEditIcon()
        setupIconImage()
        setupAddThumbnail()
        setupEditThumbnail()
        setupThumbnailImage()
        setupConfirmButton()
    }

    private fun setupTierDropdownList(){
        binding.apply {
            adapterTierItem = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, tierItem)
            ctvTournamentTier.setAdapter(adapterTierItem)
            ctvTournamentTier.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                tournamentTier = adapterView.getItemAtPosition(i)
            }
        }
    }

    private fun setupTypeDropdownList(){
        binding.apply {
            adapterTypeItem = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, typeItem)
            ctvTournamentType.setAdapter(adapterTypeItem)
            ctvTournamentType.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                tournamentType = adapterView.getItemAtPosition(i)
                viewModel.setTournamentType(tournamentType.toString())
            }
        }
    }

    private fun setupPersonEachTeamDropdownList(){
        binding.apply {
            viewModel.tournamentType.observe(viewLifecycleOwner){
                when(it){
                    "Team" -> {
                        llTournamentEachTeamTv.visibility = View.VISIBLE
                        ilTournamentPersonEachTeam.visibility = View.VISIBLE
                        adapterPersonEachTeamItem = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, personEachTeamItem)
                        ctvTournamentPersonEachTeam.setAdapter(adapterPersonEachTeamItem)
                        ctvTournamentPersonEachTeam.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                            tournamentPersonEachTeam = adapterView.getItemAtPosition(i)
                        }
                    }
                    else -> {
                        llTournamentEachTeamTv.visibility = View.GONE
                        ilTournamentPersonEachTeam.visibility = View.GONE
                        tournamentPersonEachTeam = 1
                    }
                }
            }
        }
    }

    private fun setupConfirmButton(){
        binding.apply {
            toolbar.btnCreate.setOnClickListener {
                val createdTournament = Tournament(
                    id = null,
                    name = etTournamentName.text.toString(),
                    maxPlayerInTeam = tournamentPersonEachTeam.toString().toInt(),
                    totalParticipant = 0,
                    maxParticipant = etTournamentMaxTeam.text.toString().toInt(),
                    type = tournamentType.toString(),
                    status = true,
                    location = etTournamentLocation.text.toString(),
                    prize = etTournamentPrizePool.text.toString().toInt(),
                    fee = etTournamentFee.text.toString().toInt(),
                    tier = tournamentTier.toString(),
                    icon = viewModel.iconBase64Image,
                    thumbnail = viewModel.thumbnailBase64Image,
                    game = null
                )
                Log.d("CreatedTournament", "CreatedTournament: ${createdTournament}")
                sharedViewModel.game.observe(viewLifecycleOwner){
                    postData(it, createdTournament)
                }
            }
        }
    }

    private fun setupAddIcon(){
        binding.apply {
            btnAddTournamentIcon.setOnClickListener {
                viewModel.setTournamentImagePickSession("Add Icon")
                pickImageFromGallery()
            }
        }
    }

    private fun setupEditIcon(){
        binding.apply {
            btnEditTournamentIcon.setOnClickListener {
                viewModel.setTournamentImagePickSession("Edit Icon")
                pickImageFromGallery()
            }
        }
    }

    private fun setupIconImage(){
        binding.apply {
            viewModel.tournamentIcon.observe(viewLifecycleOwner){picture ->
                if(picture != null){
                    ivTournamentIcon.setImageBitmap(picture)
                    viewModel.iconBase64Image = PictureRelatedTools.convertBitmapToBase64(picture)
                    ivTournamentIcon.visibility = View.VISIBLE
                    btnEditTournamentIcon.visibility = View.VISIBLE
                    btnAddTournamentIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun setupEditThumbnail(){
        binding.apply {
            btnEditTournamentThumbnail.setOnClickListener {
                viewModel.setTournamentImagePickSession("Edit Thumbnail")
                pickImageFromGallery()
            }
        }
    }

    private fun setupAddThumbnail(){
        binding.apply {
            btnAddTournamentThumbnail.setOnClickListener {
                viewModel.setTournamentImagePickSession("Add Thumbnail")
                pickImageFromGallery()
            }
        }
    }

    private fun setupThumbnailImage(){
        binding.apply {
            viewModel.tournamentThumbnail.observe(viewLifecycleOwner){picture ->
                if(picture != null){
                    ivTournamentThumbnail.setImageBitmap(picture)
                    viewModel.thumbnailBase64Image = PictureRelatedTools.convertBitmapToBase64(picture)
                    ivTournamentThumbnail.visibility = View.VISIBLE
                    btnEditTournamentThumbnail.visibility = View.VISIBLE
                    btnAddTournamentThumbnail.visibility = View.GONE
                }
            }
        }
    }

    private fun postData(gameID : String,tournament: Tournament){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.createTournament(GlobalConstant.ATHENTICATION_TOKEN,gameID, tournament)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                SuccessCreateDialog().show(parentFragmentManager, "SuccessCreateDialog")
                findNavController().popBackStack()
                sharedViewModel.setRestart(true)
//                Log.d(TAG, "getData: ${viewmodel.user}")
            }else{
                Log.d(TAG, "Response no successful")
                Log.d(TAG, "postData: ${response.body()}")
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        this.startActivityForResult(intent, GlobalConstant.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GlobalConstant.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            val imageSource = ImageDecoder.createSource(requireActivity().contentResolver, imageUri!!)
            val imageBitmap = ImageDecoder.decodeBitmap(imageSource)
            viewModel.tournamentImagePickSession.observe(viewLifecycleOwner){
                Log.d(TAG, "onActivityResult: $it")
                when(it){
                    "Add Icon" -> {viewModel.setTournamentIcon(imageBitmap)}
                    "Edit Icon" -> {viewModel.setTournamentIcon(imageBitmap)}
                    "Add Thumbnail" -> {viewModel.setTournamentThumbnail(imageBitmap)}
                    "Edit Thumbnail" -> {viewModel.setTournamentThumbnail(imageBitmap)}
                }
            }
        }
    }
}