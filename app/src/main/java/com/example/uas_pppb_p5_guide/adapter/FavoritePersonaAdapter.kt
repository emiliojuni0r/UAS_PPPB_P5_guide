package com.example.uas_pppb_p5_guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_pppb_p5_guide.data.FavoritePersona
import com.example.uas_pppb_p5_guide.databinding.ItemPersonaBinding


class FavoritePersonaAdapter(
    private val favoritePersonaList: List<FavoritePersona>,
    private val onItemClick: (FavoritePersona) -> Unit
) : RecyclerView.Adapter<FavoritePersonaAdapter.FavoritePersonaViewHolder>() {

    inner class FavoritePersonaViewHolder(private val binding: ItemPersonaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritePersona: FavoritePersona) {
            binding.txtPersonaName.text = favoritePersona.name
            Glide.with(binding.imgPersona.context)
                .load(favoritePersona.image)
                .into(binding.imgPersona)

            binding.root.setOnClickListener {
                onItemClick(favoritePersona)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePersonaViewHolder {
        val binding = ItemPersonaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritePersonaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritePersonaViewHolder, position: Int) {
        holder.bind(favoritePersonaList[position])
    }

    override fun getItemCount(): Int = favoritePersonaList.size
}