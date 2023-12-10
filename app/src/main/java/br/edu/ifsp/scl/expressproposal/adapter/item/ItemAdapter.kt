package br.edu.ifsp.scl.expressproposal.adapter.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.databinding.ItemCellBinding

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ContatoViewHolder>() {
    private lateinit var binding: ItemCellBinding
    var itemListFilterable = ArrayList<Item>()
    var itemList = ArrayList<Item>()
    var listener: ItemListener?=null

    fun updateList(newList: ArrayList<Item>) {
        itemList = newList
        itemListFilterable = itemList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ItemListener)
    {
        this.listener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContatoViewHolder {
        binding = ItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContatoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.typeVH.text = itemList[position].type
        holder.valueVH.text = itemList[position].value
        holder.descriptionVH.text = itemList[position].description

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ContatoViewHolder(view: ItemCellBinding) : RecyclerView.ViewHolder(view.root) {
        val typeVH = view.type
        val valueVH = view.value
        val descriptionVH = view.description

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }


    }

    interface ItemListener
    {
        fun onItemClick(pos: Int)
    }
}