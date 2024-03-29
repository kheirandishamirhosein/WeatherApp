package com.example.weatherapp.ui.calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.GetApi
import com.example.weatherapp.data.model.persianCalender.PersianCalendar
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _calendarStatus = MutableLiveData<PersianCalendar>()
    val calendarStatus: LiveData<PersianCalendar> = _calendarStatus

    fun fetchCalendarData() {
        //observe viewModel one time , and keep before data
        if (_calendarStatus.value != null) {
            return
        }
        viewModelScope.launch {
            try {
                _calendarStatus.value = GetApi.calendarRetrofitService.getCalendarData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}