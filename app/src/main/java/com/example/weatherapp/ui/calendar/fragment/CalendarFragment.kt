package com.example.weatherapp.ui.calendar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.model.persianCalender.PersianCalendar
import com.example.weatherapp.databinding.FragmentCalendarBinding
import com.example.weatherapp.ui.calendar.viewmodel.CalendarViewModel
import com.example.weatherapp.util.TimeFormatter
import javax.inject.Inject

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    //private val calendarApiViewModel: CalendarViewModel by activityViewModels()
    @Inject
    lateinit var calendarApiViewModel: CalendarViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        binding = FragmentCalendarBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarApiViewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        binding.coLayout.visibility = View.GONE
        fetchCalendarViewModel()
    }

    private fun setData(persianCalendar: PersianCalendar) = with(binding) {
        tvDate.text = persianCalendar.date.full.official.usual.fa
        tvDateYear.text = persianCalendar.date.year.name
        tvDateMonth.text = persianCalendar.date.month.name
        tvDateDay.text = persianCalendar.date.day.name
        tvDateWeekday.text = persianCalendar.date.weekday.name
        tvDateTime12.text = TimeFormatter.currentTime
        tvCalendarSeason.text = persianCalendar.season.name
        tvCalendarAnimal.text = persianCalendar.date.year.animal
        tvCalendarLoc.text = persianCalendar.timezone.name
    }

    private fun fetchCalendarViewModel() = with(binding) {
        mdPcProgressLoading.visibility = View.VISIBLE
        calendarApiViewModel.fetchCalendarData()
        calendarApiViewModel.calendarStatus.observe(viewLifecycleOwner) {
            mdPcProgressLoading.visibility = View.GONE
            binding.coLayout.visibility = View.VISIBLE
            setData(it)
        }
    }

}