package br.edu.ifsp.scl.expressproposal.adapter.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.expressproposal.data.company.Company
import br.edu.ifsp.scl.expressproposal.databinding.CompanyCellBinding

class CompanyAdapter() :
    RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {
    private lateinit var binding: CompanyCellBinding
    var companyList = ArrayList<Company>()
    var companyListFilterable = ArrayList<Company>()
    var listener: CompanyListener?=null

    fun updateList(newList: ArrayList<Company>) {
        companyList = newList
        companyListFilterable = companyList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: CompanyListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyViewHolder {
        binding = CompanyCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.nomeVH.text = companyList[position].name
        holder.foneVH.text = companyList[position].cnpj
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    inner class CompanyViewHolder(view: CompanyCellBinding) : RecyclerView.ViewHolder(view.root) {
        val nomeVH = view.nome
        val foneVH = view.cnpj

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface CompanyListener
    {
        fun onItemClick(pos: Int)
    }

}