package br.edu.ifsp.scl.expressproposal.viewmodel.company

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.expressproposal.data.company.Company
import br.edu.ifsp.scl.expressproposal.data.ExpressProposalDatabase
import br.edu.ifsp.scl.expressproposal.repository.company.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyViewModel(application: Application): AndroidViewModel(application) {
    private val repository: CompanyRepository
    var allCompanies : LiveData<List<Company>>
    lateinit var company : LiveData<Company>
    init {
        val dao = ExpressProposalDatabase.getDatabase(application).companyDAO()
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