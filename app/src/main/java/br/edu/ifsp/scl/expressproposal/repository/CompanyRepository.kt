package br.edu.ifsp.scl.expressproposal.repository

import br.edu.ifsp.scl.expressproposal.data.Company
import br.edu.ifsp.scl.expressproposal.data.CompanyDAO

class CompanyRepository(private val companyDAO: CompanyDAO) {
    suspend fun insert(company: Company){
        companyDAO.insert(company)
    }
}