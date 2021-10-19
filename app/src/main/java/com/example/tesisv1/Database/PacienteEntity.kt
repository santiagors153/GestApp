package com.example.tesisv1.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "PacienteEntity")
data class PacienteEntity(@PrimaryKey(autoGenerate = true)var id: Long = 0,
                          var nombre:String,
                          var ApellidoP:String,
                          var ApellidoM:String,
                          var Edad:Int,
                          var HitoriaCLin:Int,
                          var telefono:String = "",
                          var estado:Boolean = false,
                          var DNI: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PacienteEntity

        if (id != other.id) return false

        return true
    }


    override fun hashCode(): Int {
        return id.hashCode()
    }
}

