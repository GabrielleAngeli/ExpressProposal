package br.edu.ifsp.scl.expressproposal.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.expressproposal.R
import br.edu.ifsp.scl.expressproposal.adapter.item.ItemAdapter
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.databinding.FragmentListItemsBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.item.ItemViewModel


class ListItemsFragment : Fragment() {
    private var _binding: FragmentListItemsBinding? = null
    private val binding get() = _binding!!
    lateinit var itemAdapter: ItemAdapter
    lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listItensFragment_to_registrationItemFragment)
        }

        configureRecyclerView()

        return root

    }

    private fun configureRecyclerView()
    {
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.allItems.observe(viewLifecycleOwner) { list ->
            list?.let {
                itemAdapter.updateList(list as ArrayList<Item>)
            }
        }
        val recyclerView = binding.recyclerviewItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = ItemAdapter()
        recyclerView.adapter = itemAdapter

        val listener = object : ItemAdapter.ItemListener {
            override fun onItemClick(pos: Int) {
                val c = itemAdapter.itemListFilterable[pos]
                val bundle = Bundle()
                bundle.putInt("idItem", c.id)
                findNavController().navigate(
                    R.id.action_listItemsFragment_to_detailItemFragment,
                    bundle
                )
            }
        }
        itemAdapter.setClickListener(listener)
    }

}