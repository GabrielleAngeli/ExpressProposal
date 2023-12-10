package br.edu.ifsp.scl.expressproposal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CompanyDAO {
    @Insert
    suspend fun insert(company: Company)

    @Update
    suspend fun update (company: Company)
    @Delete
    suspend fun delete(company: Company)

    @Query("SELECT * FROM company ORDER BY name")
    fun getAllCompanies(): LiveData<List<Company>>

    @Query("SELECT * FROM company WHERE id=:id")
    fun getCompanyById(id: Int): LiveData<Company>

}