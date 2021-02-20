package com.innodroid.foodscannerapp.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.model.Ingredient

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var items = listOf<Ingredient>()

    fun load(items: List<Ingredient>?) {
        this.items = items ?: listOf()

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = holder.itemView as TextView

        tv.text = items[position].text
        tv.setTextColor(ContextCompat.getColor(tv.context, items[position].level.color))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}
