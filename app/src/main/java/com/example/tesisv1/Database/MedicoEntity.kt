package com.example.tesisv1.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MedicoEntity")
data class MedicoEntity(@PrimaryKey(autoGenerate = true)var id:Long = 0,
                        var NombreMed:String,
                        var ApellidoPMed:String, var ApellidoMMed:String,var DNI:Int, var colegiatura:String,)
