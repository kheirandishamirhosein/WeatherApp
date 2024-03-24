package com.example.weatherapp.ui.fiveforcast.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.forecastLocation.FiveForecastList
import com.example.weatherapp.util.KelvinToCelsius

class ForecastListDiffCallback(
    private val oldList: List<FiveForecastList>,
    private val newList: List<FiveForecastList>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].dt_txt == newList[newItemPosition].dt_txt
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}

class ForecastListAdapter : RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {

    private var itemWeatherList = emptyList<FiveForecastList>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(itemList: List<FiveForecastList>) {
        val diffResult = DiffUtil.calculateDiff(
            ForecastListDiffCallback(itemWeatherList, itemList)
        )
        this.itemWeatherList = itemList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_five_day_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = itemWeatherList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemWeatherList.size
    }

    class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTempForecast5Day: TextView = view.findViewById(R.id.tv_temp_forecast_5_day)
        private val tvWeatherTypeForecast5Day: TextView = view.findViewById(R.id.tv_weather_type_forecast_5_day)
        private val tvFeelsLikeForecast5Day: TextView = view.findViewById(R.id.tv_internal_parameter_forecast_5_day)
        private val tvDateTime: TextView = view.findViewById(R.id.tv_date_time)
        private val ivImagesForecast5Day: ImageView = view.findViewById(R.id.iv_images_forecast_5_day)

        @SuppressLint("SetTextI18n")
        fun bind(fiveForecastWeatherModel: FiveForecastList) {
            tvTempForecast5Day.text =
                "" + KelvinToCelsius.kelvinToCelsius(fiveForecastWeatherModel.main.temp) + ""
            tvWeatherTypeForecast5Day.text = fiveForecastWeatherModel.weather[0].main
            tvDateTime.text = fiveForecastWeatherModel.dt_txt
            tvFeelsLikeForecast5Day.text =
                "Feels Like " + KelvinToCelsius.kelvinToCelsius(fiveForecastWeatherModel.main.feels_like) + ""
            updateImageWeather(fiveForecastWeatherModel.weather[0].id)
        }

        private fun updateImageWeather(id: Int) {
            when (id) {
                in 200..232 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_thunderstorm)
                }
                in 300..321 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_drizzle_)
                }
                in 500..531 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_rain)
                }
                in 600..622 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_snow)
                }
                in 701..781 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_atmosphere)
                }
                800 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_img_clear)
                }
                in 801..804 -> {
                    ivImagesForecast5Day.setImageResource(R.drawable.ic_clouds)
                }
            }

        }

    }

}