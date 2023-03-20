package com.example.weatherapp.ui.airPollution.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.airPollution.AirPollutionList

class AirPollutionListAdapter : RecyclerView.Adapter<AirPollutionListAdapter.AirPollutionViewHolder>() {

    private var itemAirPollutionWeatherList = emptyList<AirPollutionList>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(itemList: List<AirPollutionList>) {
        this.itemAirPollutionWeatherList = itemList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirPollutionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_air_pollution_weather, parent, false)
        return AirPollutionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemAirPollutionWeatherList.size
    }

    override fun onBindViewHolder(holder: AirPollutionViewHolder, position: Int) {
        val item = itemAirPollutionWeatherList[position]

    }

    class AirPollutionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //components
        private val tvCoComponent: TextView = view.findViewById(R.id.tv_component)
        private val tvNh3Component: TextView = view.findViewById(R.id.tv_component)
        private val tvNoComponent: TextView = view.findViewById(R.id.tv_component)
        private val tvNo2Component: TextView = view.findViewById(R.id.tv_component)
        private val tvO3Component: TextView = view.findViewById(R.id.tv_component)
        private val tvPm10Component: TextView = view.findViewById(R.id.tv_component)
        private val tvPm25Component: TextView = view.findViewById(R.id.tv_component)
        private val tvSo2Component: TextView = view.findViewById(R.id.tv_component)


        fun bind(){

        }
    }
}