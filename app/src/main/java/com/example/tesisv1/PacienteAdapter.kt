package com.example.tesisv1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tesisv1.Database.PacienteEntity
import com.example.tesisv1.databinding.ItemPacienteBinding

class PacienteAdapter(private var pacientes: MutableList<PacienteEntity>, private var listener: OnClickListener): RecyclerView.Adapter<PacienteAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_paciente, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = pacientes.get(position)
        with(holder){
            setListener(paciente)
            binding.tvName.text = paciente.nombre
            binding.cbFavorite.isChecked = paciente.estado

        }
    }

    override fun getItemCount(): Int = pacientes.size

    fun setPaciente(pacientes: MutableList<PacienteEntity>) {
        this.pacientes = pacientes
        notifyDataSetChanged()
    }

    fun add(pacienteEntity: PacienteEntity) {
        if (!pacientes.contains(pacienteEntity)) {
            pacientes.add(pacienteEntity)
            notifyItemInserted(pacientes.size - 1)
        }
    }

    fun update(pacienteEntity: PacienteEntity) {
        val index = pacientes.indexOf(pacienteEntity)
        if (index != -1){
            pacientes.set(index, pacienteEntity)
            notifyItemChanged(index)
        }
    }
    fun delete(pacienteEntity: PacienteEntity) {
        val index = pacientes.indexOf(pacienteEntity)
        if (index != -1){
            pacientes.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPacienteBinding.bind(view)

        fun setListener(pacienteEntity: PacienteEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(pacienteEntity.id) }
                setOnLongClickListener {
                    listener.onDeleteStore(pacienteEntity)
                    true
                }
            }
            binding.cbFavorite.setOnClickListener { listener.onFavoriteStore(pacienteEntity) }
        }
    }
}