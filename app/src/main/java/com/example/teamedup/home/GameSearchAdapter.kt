package com.example.teamedup.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.teamedup.databinding.GameSearchListItemBinding
import com.example.teamedup.repository.model.Game
import com.example.teamedup.util.GameRecyclerViewClickListener
import com.example.teamedup.util.PictureRelatedTools.convertBase64ToBitmap

class GameSearchAdapter : RecyclerView.Adapter<GameSearchAdapter.GameViewHolder>() {

    inner class GameViewHolder(val binding : GameSearchListItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Game>(){
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var games : List<Game>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    var gameListener : GameRecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(GameSearchListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.binding.apply {
            val game = games[position]

            tvGameTitle.text = game.name
            ivGameLogo.setImageBitmap(convertBase64ToBitmap(game.logo))
            itemGame.setOnClickListener {
                gameListener?.onItemClicked(it, game)
            }
        }
    }

    fun setFilteredGameList(gameList : List<Game>){
        games = gameList
        notifyDataSetChanged()
    }
}