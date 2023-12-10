package br.edu.ifsp.scl.expressproposal.ui.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.expressproposal.R
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.databinding.FragmentRegistrationItemBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.item.ItemViewModel
import com.google.android.material.snackbar.Snackbar

class RegistrationItemFragment : Fragment() {
    private var _binding: FragmentRegistrationItemBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationItemBinding.inflate(inflater, container, false)
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
                        val type = binding.commonLayout.editTextType.text.toString()
                        val value = binding.commonLayout.editTextValue.text.toString()
                        val description = binding.commonLayout.editTextDescription.text.toString()
                        val item = Item(id = 0, type = type, value = value, description = description)
                        viewModel.insert(item)
                        Snackbar.make(binding.root, "Item inserido", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}