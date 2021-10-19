package com.example.tesisv1.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(PacienteEntity::class),version = 1)
abstract class PacienteDatabase: RoomDatabase() {
    abstract fun pacienteDao():PacienteDAO
}