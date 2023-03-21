package com.qc.ssm.ifc.feelclimate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qc.ssm.ifc.feelclimate.R
import com.qc.ssm.ifc.feelclimate.ui.SearchActivity

class PopularAdapter(
    private val context: Context,
    var listener: SearchActivity.OnItemClickListener,
    var list: Array<String>?
) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    var cities = arrayListOf<String>(
        "Las vegas",
        "Miami",
        "Berlin",
        "San francisco"
    )

    init {
        list?.let {
            cities.clear()
            for (i in it) {
                cities.add(i)
            }
            cities.removeAt(cities.size - 1)
            cities.reverse()
        }

    }


    class ViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        val cityText: TextView = itemView.findViewById(R.id.cityText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.child_popular, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityText.text = cities[position]
        holder.itemView.setOnClickListener { listener.onItemClick(cities[position]) }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setData(list: Array<String>?) {
        cities.clear()
        if (list != null && list.isNotEmpty()) {
            cities.addAll(list)
        }
        cities.removeAt(cities.size - 1)
        cities.reverse()
        notifyDataSetChanged()
    }
}