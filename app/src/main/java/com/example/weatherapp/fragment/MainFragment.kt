package com.example.weatherapp.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.api.GetApi
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt

@Suppress("UNREACHABLE_CODE")
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
        binding.coLayout.visibility = View.GONE


        binding.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //   getWeatherCity(binding.editText.text.toString())
                val view = activity?.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    binding.editText.clearFocus()
                }
                true
            } else false
        }

    }


    //init views
    fun initViews(){
        //progress loading
        binding.mdPcProgressLoading.setOnClickListener {  }
        //toolbar
        binding.mdTfGetCityName.setOnClickListener {  }
        //rl_layout_date_time
        binding.mdTvDate.setOnClickListener {  }
        binding.mdTvTime.setOnClickListener {  }
        //rl_layout_image_temp
        binding.mdTvTemp.setOnClickListener {  }
        binding.ivImages.setOnClickListener {  }
        binding.ivTempNumCircle.setOnClickListener {  }     //null
        //rl_layout_city_weather_type
        binding.mdTvCity.setOnClickListener {  }
        binding.mdTvWeatherType.setOnClickListener {  }
        //rl_layout_min_max_feels
        binding.mdTvFeelsLike.setOnClickListener {  }
        binding.mdTvDayMaxTemp.setOnClickListener {  }
        binding.mdTvDayMinTemp.setOnClickListener {  }
        //temps
        //layout above
        //rl_layout_pressure
        binding.ivPressure.setOnClickListener {  }          //null
        binding.mdTvNumPressure.setOnClickListener {  }
        binding.mdTvNumPressure.setOnClickListener {  }
        //rl_layout_humidity
        binding.ivHumidity.setOnClickListener {  }          //null
        binding.mdTvNumHumidity.setOnClickListener {  }
        binding.mdTvNameHumidity.setOnClickListener {  }
        //rl_layout_wind_speed
        binding.ivWindSpeed.setOnClickListener {  }         //null
        binding.mdTvNumWindSpeed.setOnClickListener {  }
        binding.mdTvNameWindSpeed.setOnClickListener {  }
        //layout below
        //rl_layout_sunrise
        binding.ivSunrise.setOnClickListener {  }           //null
        binding.mdTvNumSunrise.setOnClickListener {  }
        binding.mdTvNameSunrise.setOnClickListener {  }
        //rl_layout_sunset
        binding.ivSunset.setOnClickListener {  }            //null
        binding.mdTvNumSunset.setOnClickListener {  }
        binding.mdTvNameSunset.setOnClickListener {  }
        //rl_layout_fahrenheit_temp
        binding.ivFahrenheit.setOnClickListener {  }        //null
        binding.mdTvNumFahrenheit.setOnClickListener {  }
        binding.mdTvNameFahrenheit.setOnClickListener {  }
    }

    /*
     fun getWeatherCity(city: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        GetApi.retrofitService.getCityWeatherData(city,UrlKeyApi.KEY)?.enqueue(object :
            Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful){
                    setDataOnView(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(activity,"Not city name",Toast.LENGTH_SHORT).show()
            })
    }
*/

     suspend fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        GetApi.retrofitService.getCurrentWeatherData(latitude,longitude,UrlKeyApi.KEY)?.enqueue(object :
        Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful){
                    setDataOnView(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(activity,"ERROR",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setDataOnView(body: WeatherModel?){
        val sdf = SimpleDateFormat("dd/mm/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        binding.mdTvDayMaxTemp.text = "Day "+kelvinToCelsius(body!!.main.temp_max)+""
        binding.mdTvDayMinTemp.text = "Night "+kelvinToCelsius(body!!.main.temp_min)+""
        binding.mdTvTemp.text = " "+kelvinToCelsius(body!!.main.temp)+""
        binding.mdTvTemp.text = " "+kelvinToCelsius(body!!.main.temp)+""
        binding.mdTvWeatherType.text = body.weather[0].main
        binding.mdTvNumSunrise.text = timeStampToLocalDate(body.sys.sunrise.toLong())
        binding.mdTvNumSunset.text = timeStampToLocalDate(body.sys.sunrise.toLong())
        binding.mdTvNumPressure.text = body.main.pressure.toString()
        binding.mdTvNumHumidity.text = body.main.humidity.toString() +"%"
        binding.mdTvNumWindSpeed.text = body.wind.speed.toString() +"m/s"
        binding.mdTvNumFahrenheit.text = ""+((kelvinToCelsius(body!!.main.temp)).times(1.8).plus(32).roundToInt())
        binding.mdTfGetCityName.editText?.setText(body.name)

        updateUI(body.weather[0].id)
    }

    private fun updateUI(id: Int) {
        if (id in 200..232){
            binding.ivImages.setImageResource(R.drawable.ic_thunderstorm)
        }
        else if (id in 300..321){
            binding.ivImages.setImageResource(R.drawable.ic_drizzle_)
        }
        else if (id in 500 .. 531){
            binding.ivImages.setImageResource(R.drawable.ic_rain)
        }
        else if (id in 600 .. 622){
            binding.ivImages.setImageResource(R.drawable.ic_snow)
        }
        else if (id in 700 .. 781){
            binding.ivImages.setImageResource(R.drawable.ic_atmosphere)
        }
        else if (id == 800){
            binding.ivImages.setImageResource(R.drawable.ic_img_clear)
        }
        else if (id in 801 .. 804){
            binding.ivImages.setImageResource(R.drawable.ic_clouds)
        }
        binding.mdPcProgressLoading.visibility = View.GONE
        binding.coLayout.visibility = View.VISIBLE
    }


    private fun kelvinToCelsius(temp: Double): Double {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()
    }

    private fun timeStampToLocalDate(timeStamp: Long):String {
        val localTime = timeStamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
        return localTime.toString()
    }


}