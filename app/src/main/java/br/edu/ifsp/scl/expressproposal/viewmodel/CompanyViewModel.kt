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
    var allCompanies : LiveData<List<Company>>
    lateinit var company : LiveData<Company>
    init {
        val dao = CompanyDatabase.getDatabase(application).companyDAO()
        repository = CompanyRepository(dao)
        allCompanies = repository.getAllCompanies()
    }
    fun insert(company: Company) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(company)
    }

    fun update(company: Company) = viewModelScope.launch(Dispatchers.IO){
        repository.update(company)
    }
    fun delete(company: Company) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(company)
    }
    fun getContactById(id: Int) {
        viewModelScope.launch {
            company = repository.getCompanyById(id)
        }
    }

}