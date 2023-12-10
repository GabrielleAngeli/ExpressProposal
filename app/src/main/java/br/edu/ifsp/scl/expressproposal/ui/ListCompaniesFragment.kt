package br.edu.ifsp.scl.expressproposal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.expressproposal.R
import br.edu.ifsp.scl.expressproposal.adapter.CompanyAdapter
import br.edu.ifsp.scl.expressproposal.data.Company
import br.edu.ifsp.scl.expressproposal.databinding.FragmentListCompaniesBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.CompanyViewModel

class ListCompaniesFragment : Fragment() {
        private var _binding: FragmentListCompaniesBinding? = null
        private val binding get() = _binding!!
        lateinit var companyAdapter: CompanyAdapter
        lateinit var viewModel: CompanyViewModel
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentListCompaniesBinding.inflate(inflater, container, false)
            val root: View = binding.root
            binding.fab.setOnClickListener {
                findNavController().navigate(R.id.action_list_companies_to_registration_company) }

            configureRecyclerView()

            return root
        }

    private fun configureRecyclerView()
    {
        viewModel = ViewModelProvider(this).get(CompanyViewModel::class.java)
        viewModel.allCompanies.observe(viewLifecycleOwner) { list ->
            list?.let {
                companyAdapter.updateList(list as ArrayList<Company>)
            }
        }
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        companyAdapter = CompanyAdapter()
        recyclerView.adapter = companyAdapter

        val listener = object : CompanyAdapter.CompanyListener {
            override fun onItemClick(pos: Int) {
                val c = companyAdapter.companyListFilterable[pos]
                val bundle = Bundle()
                bundle.putInt("idCompany", c.id)
                findNavController().navigate(
                    R.id.action_listCompaniesFragment_to_detailFragment,
                    bundle
                )
            }
        }
        companyAdapter.setClickListener(listener)

    }

}