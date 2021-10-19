package com.example.tesisv1.Database

import androidx.room.*

@Dao
interface PacienteDAO{
    @Query("SELECT * FROM  PacienteEntity")
    fun getAllPaciente(): MutableList<PacienteEntity>

    @Query("SELECT * FROM PacienteEntity where id = :id")
    fun getPacinteById(id:Long): PacienteEntity

    @Insert
    fun addPaciente(pacienteEntity: PacienteEntity): Long

    @Update
    fun updatePaciente(pacienteEntity: PacienteEntity)

    @Delete
    fun deletePaciente(pacienteEntity: PacienteEntity)
}
