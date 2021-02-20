package com.innodroid.foodscannerapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.model.Scan
import java.text.SimpleDateFormat
import java.util.*

class ScansAdapter: RecyclerView.Adapter<ScansAdapter.ViewHolder>() {
    private var items = listOf<Scan>()
    private val format = SimpleDateFormat("MM/dd/yy", Locale.US)

    var itemClicked: (Scan) -> Unit = {}

    fun load(items: List<Scan>?) {
        this.items = items ?: listOf()

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scan, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val name = holder.itemView.findViewById(R.id.name) as TextView
        val date = holder.itemView.findViewById(R.id.date) as TextView

        name.text = item.title
        date.text = format.format(Date(item.date))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClicked(items[layoutPosition])
            }
        }
    }
}
