package com.example.uas_pppb_p5_guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_pppb_p5_guide.databinding.ItemPersonaBinding
import com.example.uas_pppb_p5_guide.model.Persona

class PersonaAdapter(
    private val personaList: List<Persona>,
    private val onItemClick: (Persona) -> Unit
) : RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>() {

    inner class PersonaViewHolder(private val binding: ItemPersonaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(persona: Persona) {
            binding.txtPersonaName.text = persona.name
            // Load image using Glide
            Glide.with(binding.imgPersona.context)
                .load(persona.image)
                .into(binding.imgPersona)

            binding.root.setOnClickListener {
                onItemClick(persona)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val binding = ItemPersonaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        holder.bind(personaList[position])
    }

    override fun getItemCount(): Int = personaList.size
}