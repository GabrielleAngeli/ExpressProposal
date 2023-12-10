package br.edu.ifsp.scl.expressproposal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.expressproposal.R
import br.edu.ifsp.scl.expressproposal.data.Company
import br.edu.ifsp.scl.expressproposal.databinding.FragmentRegistrationCompanyBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.CompanyViewModel
import com.google.android.material.snackbar.Snackbar

class RegistrationCompanyFragment : Fragment() {

    private var _binding: FragmentRegistrationCompanyBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CompanyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompanyViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.register_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        val name = binding.commonCompanyLayout.editTextNome.text.toString()
                        val email = binding.commonCompanyLayout.editTextEmail.text.toString()
                        val cnpj = binding.commonCompanyLayout.editTextCnpj.text.toString()
                        val phone = binding.commonCompanyLayout.editTextPhone.text.toString()

                        val company = Company(id = 0, name = name, phone = phone, email = email, cnpj =cnpj)
                        viewModel.insert(company)
                        Snackbar.make(binding.root, "Empresa inserida", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}