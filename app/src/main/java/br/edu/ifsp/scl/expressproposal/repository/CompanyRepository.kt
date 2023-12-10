package br.edu.ifsp.scl.expressproposal.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.expressproposal.data.Company
import br.edu.ifsp.scl.expressproposal.data.CompanyDAO

class CompanyRepository(private val companyDAO: CompanyDAO) {
    suspend fun insert(company: Company){
        companyDAO.insert(company)
    }

    suspend fun update(company: Company){
        companyDAO.update(company)
    }

    suspend fun delete(company: Company){
        companyDAO.delete(company)
    }

    fun getAllCompanies(): LiveData<List<Company>> {
        return companyDAO.getAllCompanies()
    }

    fun getCompanyById(id: Int): LiveData<Company>{
        return companyDAO.getCompanyById(id)
    }
}