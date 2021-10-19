package com.example.tesisv1

import com.example.tesisv1.Database.PacienteEntity

interface OnClickListener {
    fun onClick(pacienteId: Long)
    fun onFavoriteStore(pacienteEntity: PacienteEntity)
    fun onDeleteStore(pacienteEntity: PacienteEntity)
}