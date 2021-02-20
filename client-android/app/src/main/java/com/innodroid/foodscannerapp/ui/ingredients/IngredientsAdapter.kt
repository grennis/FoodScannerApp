package com.innodroid.foodscannerapp.ui.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.model.Ingredient
import com.innodroid.foodscannerapp.model.SensitivityLevel

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var items = listOf<Item>()

    data class Item(@ColorRes val color: Int, val header: String?, val item: Ingredient?)

    fun load(ingredients: List<Ingredient>?) {
        val items = ingredients?.sortedByDescending { it.level.ordinal } ?: listOf()

        this.items = getSection("Severely Sensitive", SensitivityLevel.Severe, items) +
                getSection("Moderately Sensitive", SensitivityLevel.Moderate, items) +
                getSection("Mildly Sensitive", SensitivityLevel.Mild, items) +
                getSection("Not Sensitive", SensitivityLevel.None, items)

        notifyDataSetChanged()
    }

    private fun getSection(label: String, level: SensitivityLevel, ingredients: List<Ingredient>?): List<Item> {
        val header = Item(R.color.gray, label, null)
        val items: List<Item>
        val filtered = ingredients?.filter { it.level == level }

        if (filtered.isNullOrEmpty()) {
            items = listOf(Item(R.color.gray, "No Ingredients", null))
        } else {
            items = filtered.map { Item(level.color, null, it) }
        }

        return listOf(header) + items
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].header != null) 0 else 1
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (viewType == 0) R.layout.ingredient_header else R.layout.ingredient
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = holder.itemView as TextView
        val item = items[position]

        tv.text = item.header ?: item.item!!.text
        tv.setTextColor(ContextCompat.getColor(tv.context, item.color))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}
