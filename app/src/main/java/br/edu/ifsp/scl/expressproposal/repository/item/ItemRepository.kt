package br.edu.ifsp.scl.expressproposal.repository.item

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.data.item.ItemDAO

class ItemRepository (private val itemDAO: ItemDAO) {
    suspend fun insert(item: Item){
        itemDAO.insert(item)
    }
    suspend fun update(item: Item){
        itemDAO.update(item)
    }

    suspend fun delete(item: Item){
        itemDAO.delete(item)
    }
    fun getAllItems(): LiveData<List<Item>> {
        return itemDAO.getAllItems()
    }

    fun getItemById(id: Int): LiveData<Item>{
        return itemDAO.getItemById(id)
    }


}