package br.edu.ifsp.scl.expressproposal.viewmodel.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.expressproposal.data.ExpressProposalDatabase
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.repository.item.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ItemRepository
    var allItems : LiveData<List<Item>>
    lateinit var item : LiveData<Item>
    init {
        val dao = ExpressProposalDatabase.getDatabase(application).itemDAO()
        repository = ItemRepository(dao)
        allItems = repository.getAllItems()
    }
    fun insert(item: Item) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(item)
    }

    fun update(item: Item) = viewModelScope.launch(Dispatchers.IO){
        repository.update(item)
    }
    fun delete(item: Item) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(item)
    }

    fun getItemById(id: Int) {
        viewModelScope.launch {
            item = repository.getItemById(id)
        }
    }
}