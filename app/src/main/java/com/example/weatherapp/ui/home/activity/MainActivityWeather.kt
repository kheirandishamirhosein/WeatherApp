package com.example.weatherapp.ui.home.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainWeatherBinding
import com.example.weatherapp.ui.airPollution.fragment.AirPollutionFragment
import com.example.weatherapp.ui.calendar.fragment.CalendarFragment
import com.example.weatherapp.ui.fiveforcast.fragment.FiveDayForecastFragment
import com.example.weatherapp.ui.home.fragment.HomeFragment

class MainActivityWeather : AppCompatActivity() {

    private lateinit var binding: ActivityMainWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loadFragment(HomeFragment())
        //use bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bar_home -> loadFragment(HomeFragment())
                R.id.bar_forecast_5_day -> loadFragment(FiveDayForecastFragment())
                R.id.bar_air_pollution -> loadFragment(AirPollutionFragment())
                R.id.bar_calendar -> loadFragment(CalendarFragment())
            }
            true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("EXIT")
        builder.setMessage("Are you sure to exit?")
        builder.setPositiveButton("Yes") { _ , _ ->
            finish()
            super.onBackPressed()
        }
        builder.setNegativeButton("No") { _ , _ ->

        }
        builder.show()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

}