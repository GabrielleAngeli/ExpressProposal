package br.edu.ifsp.scl.expressproposal.data.item

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.expressproposal.data.company.Company

@Dao
interface ItemDAO {
    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update (item: Item)
    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM item ORDER BY type")
    fun getAllItems(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE id=:id")
    fun getItemById(id: Int): LiveData<Item>
}