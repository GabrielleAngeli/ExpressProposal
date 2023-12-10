package br.edu.ifsp.scl.expressproposal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.expressproposal.data.Company
import br.edu.ifsp.scl.expressproposal.data.CompanyDatabase
import br.edu.ifsp.scl.expressproposal.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyViewModel(application: Application): AndroidViewModel(application) {
    private val repository: CompanyRepository
    lateinit var company : LiveData<Company>
    init {
        val dao = CompanyDatabase.getDatabase(application).companyDAO()
        repository = CompanyRepository(dao)
    }
    fun insert(company: Company) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(company)
    }
}