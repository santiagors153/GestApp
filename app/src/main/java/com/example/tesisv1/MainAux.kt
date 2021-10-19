package com.example.tesisv1

import android.opengl.Visibility
import com.example.tesisv1.Database.PacienteEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)
    fun addPaciente(pacienteEntity: PacienteEntity)
    fun updatePaciente(pacienteEntity: PacienteEntity)
}