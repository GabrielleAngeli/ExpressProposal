package br.edu.ifsp.scl.expressproposal.ui.item

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
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.databinding.FragmentDetailItemBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.item.ItemViewModel
import com.google.android.material.snackbar.Snackbar


class DetailItemFragment : Fragment() {
    private var _binding: FragmentDetailItemBinding? = null
    private val binding get() = _binding!!
    lateinit var item: Item
    lateinit var typeEditText: EditText
    lateinit var valueEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var viewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailItemBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        typeEditText = binding.commonLayout.editTextType
        valueEditText = binding.commonLayout.editTextValue
        descriptionEditText = binding.commonLayout.editTextDescription
        val idItem = requireArguments().getInt("idItem")
        viewModel.getItemById(idItem)
        viewModel.item.observe(viewLifecycleOwner) { result ->
            result?.let {
                item = result
                typeEditText.setText(item.type)
                valueEditText.setText(item.value)
                descriptionEditText.setText(item.description)
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
                        item.type=typeEditText.text.toString()
                        item.value=valueEditText.text.toString()
                        item.description=descriptionEditText.text.toString()
                        viewModel.update(item)
                        Snackbar.make(binding.root, "Item alterado", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_delete ->{
                        viewModel.delete(item)
                        Snackbar.make(binding.root, "Item apagado", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}