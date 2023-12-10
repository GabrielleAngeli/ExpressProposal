package br.edu.ifsp.scl.expressproposal.ui.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.expressproposal.R
import br.edu.ifsp.scl.expressproposal.data.company.Company
import br.edu.ifsp.scl.expressproposal.databinding.FragmentDetailBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.company.CompanyViewModel
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var company: Company
    lateinit var nomeEditText: EditText
    lateinit var phoneEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var cnpjEditText: EditText
    lateinit var viewModel: CompanyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompanyViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nomeEditText = binding.commonLayout.editTextNome
        phoneEditText = binding.commonLayout.editTextPhone
        cnpjEditText = binding.commonLayout.editTextCnpj
        emailEditText = binding.commonLayout.editTextEmail
        val idCompany = requireArguments().getInt("idCompany")
        viewModel.getContactById(idCompany)
        viewModel.company.observe(viewLifecycleOwner) { result ->
            result?.let {
                company = result
                nomeEditText.setText(company.name)
                phoneEditText.setText(company.phone)
                cnpjEditText.setText(company.cnpj)
                emailEditText.setText(company.email)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_edit -> {
                        company.name = nomeEditText.text.toString()
                        company.phone = phoneEditText.text.toString()
                        company.email = emailEditText.text.toString()
                        company.cnpj = cnpjEditText.text.toString()

                        viewModel.update(company)
                        Snackbar.make(binding.root, "Empresa alteradoa", Snackbar.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_delete -> {
                        viewModel.delete(company)
                        Snackbar.make(binding.root, "Empresa apagada", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
}
