package com.example.uas_pppb_p5_guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_pppb_p5_guide.databinding.ItemAdminPersonaBinding
import com.example.uas_pppb_p5_guide.model.Persona

class AdminPersonaAdapter(
    private val personas: List<Persona>,
    private val onViewClick: (Persona) -> Unit,
    private val onEditClick: (Persona) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<AdminPersonaAdapter.PersonaViewHolder>() {

    inner class PersonaViewHolder(private val binding: ItemAdminPersonaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(persona: Persona) {
            binding.txtPersonaName.text = persona.name
            // Menggunakan Glide untuk memuat gambar dari URL
            Glide.with(binding.imgPersona.context)
                .load(persona.image)
                .into(binding.imgPersona)

            binding.btnEdit.setOnClickListener { onEditClick(persona) }
            binding.btnView.setOnClickListener { onViewClick(persona) }
            binding.btnDelete.setOnClickListener { persona.id?.let { it1 -> onDeleteClick(it1) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val binding = ItemAdminPersonaBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PersonaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        holder.bind(personas[position])
    }

    override fun getItemCount(): Int = personas.size
}