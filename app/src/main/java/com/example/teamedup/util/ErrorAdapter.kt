package com.example.teamedup.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.AlertPopBinding

class ErrorAdapter() : RecyclerView.Adapter<ErrorAdapter.ErrorViewHolder>(){
    inner class ErrorViewHolder(val binding : AlertPopBinding) : RecyclerView.ViewHolder(binding.root)
    var errorMessageList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        return ErrorViewHolder(AlertPopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return errorMessageList.size
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        val errorMessage = errorMessageList[position]
        holder.binding.apply {
            tvAlertMessage.text = errorMessage
        }
    }

    fun setFilteredGameList(errorMassageList : ArrayList<String>){
        this.errorMessageList = errorMassageList
        notifyDataSetChanged()
    }
}