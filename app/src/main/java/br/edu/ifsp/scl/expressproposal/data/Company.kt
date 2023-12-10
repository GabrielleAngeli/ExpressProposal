package br.edu.ifsp.scl.expressproposal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var email: String,
    var phone: String?,
    var cnpj: String
)
