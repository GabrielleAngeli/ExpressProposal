package br.edu.ifsp.scl.expressproposal.data.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var type: String,
    var value: String,
    var description: String?
)