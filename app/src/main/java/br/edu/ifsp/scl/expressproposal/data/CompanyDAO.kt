package br.edu.ifsp.scl.expressproposal.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CompanyDAO {
    @Insert
    suspend fun insert(company: Company)
}