package com.example.housesharing.bathroom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.Appointment
import com.example.housesharing.data.CalendarDateModel
import com.example.housesharing.databinding.FragmentBathroomBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class BathroomFragment : Fragment(), BathroomAdapter.OnItemClickListener, CalendarBathroomAdapter.OnItemClickListener {

    private lateinit var binding: FragmentBathroomBinding

    private lateinit var viewModel: BathroomViewModel

    private lateinit var hoursAdapter: BathroomAdapter

    private var listHour = ArrayList<Appointment>()

    private var selectedDay = 1

    //calendar stuff
    //how to be displayed on top
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var calendarAdapter: CalendarBathroomAdapter
    private val calendarList = ArrayList<CalendarDateModel>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bathroom,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(BathroomViewModel::class.java)

        actionBar()
        navBar()

        setUpCalendarAdapter()
        changeMonthButtons()
        setUpCalendar()
        setupHoursAdapter()

        viewModel.listHoursLiveData.observe(viewLifecycleOwner){
            listHour.clear()
            listHour.addAll(it)
            hoursAdapter.notifyDataSetChanged()
        }

        viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
            if(it.exception == null){
                if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                    viewModel.updateList(it.appointment!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                }
                else{
                    viewModel.updateList(it.appointment!!, 0)
                }
            }
            hoursAdapter.notifyDataSetChanged()
        }
        return binding.root
    }


    private fun actionBar(){
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Bathroom"
        setHasOptionsMenu(true)
    }

    private fun navBar(){
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun setUpCalendarAdapter() {
        calendarAdapter = CalendarBathroomAdapter(calendarList, this)
        binding.recyclerViewDateBathroom.adapter = calendarAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeMonthButtons() {
        binding.buttonNextMonthBathroom.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
            viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                if(it.exception == null){
                    if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                        viewModel.updateList(it.appointment!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                    }
                    else{
                        viewModel.updateList(it.appointment!!, 0)
                    }
                    hoursAdapter.notifyDataSetChanged()
                }
            }
            calendarAdapter.selectedItemPosition = 0
            calendarAdapter.notifyDataSetChanged()
        }
        binding.buttonPreviousMonthBathroom.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
            viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                if(it.exception == null){
                    if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                        viewModel.updateList(it.appointment!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                    }
                    else{
                        viewModel.updateList(it.appointment!!, 0)
                    }
                    hoursAdapter.notifyDataSetChanged()
                }
            }
            calendarAdapter.selectedItemPosition = 0
            calendarAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCalendar() {
        val calendarListTemp = ArrayList<CalendarDateModel>()
        //current month and year
        binding.buttonMonthBathroom.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        //maximum number of days in the current month
        var maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        //current calendar month
        val calCurrent = Calendar.getInstance(Locale.ENGLISH)
        if(SimpleDateFormat("M", Locale.ENGLISH).format(calCurrent.time) == SimpleDateFormat("M", Locale.ENGLISH).format(cal.time)){
            binding.buttonPreviousMonthBathroom.isEnabled = false
            monthCalendar.set(Calendar.DAY_OF_MONTH, SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt())
            selectedDay = SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt()
            maxDaysInMonth -= SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt() -1
        }
        else {
            selectedDay = 1
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            binding.buttonPreviousMonthBathroom.isEnabled = true
        }
        //store all dates in array until max days of current month
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarListTemp.add(CalendarDateModel(monthCalendar.time))
            //add the next day of the month
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList.clear()
        calendarList.addAll(calendarListTemp)
        calendarAdapter.notifyDataSetChanged()
    }

    private fun setupHoursAdapter(){
        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        hoursAdapter = BathroomAdapter(listHour, this)
        binding.recyclerViewBathroom.layoutManager = manager
        binding.recyclerViewBathroom.adapter = hoursAdapter
    }


    //press on hour
    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(item: Appointment?) {
        if (item != null) {
            var hourStart: String
            var minuteStart: String
            var hourEnd: String
            var minuteEnd: String

            if(item.timeStartHour!! < 10){
                if(item.timeStartMinute == 0){
                    hourStart = "0" + item.timeStartHour.toString()
                    minuteStart = item.timeStartMinute.toString() + "0"
                    hourEnd = "0" + item.timeEndHour.toString()
                    minuteEnd = item.timeEndMinute.toString()
                }
                else{
                    if(item.timeEndHour != 10){
                        hourStart = "0" + item.timeStartHour.toString()
                        minuteStart = item.timeStartMinute.toString()
                        hourEnd = "0" + item.timeEndHour.toString()
                        minuteEnd = item.timeEndMinute.toString() + "0"
                    }
                    else{
                        hourStart = "0" + item.timeStartHour.toString()
                        minuteStart = item.timeStartMinute.toString()
                        hourEnd = item.timeEndHour.toString()
                        minuteEnd = item.timeEndMinute.toString() + "0"
                    }
                }
            }
            else{
                if(item.timeStartMinute == 0){
                    hourStart = item.timeStartHour.toString()
                    minuteStart = item.timeStartMinute.toString() + "0"
                    hourEnd = item.timeEndHour.toString()
                    minuteEnd = item.timeEndMinute.toString()
                }
                else{
                    hourStart = item.timeStartHour.toString()
                    minuteStart = item.timeStartMinute.toString()
                    hourEnd = item.timeEndHour.toString()
                    minuteEnd = item.timeEndMinute.toString() + "0"
                }
            }

            AlertDialog.Builder(requireContext())
                .setMessage("Confirm booking from $hourStart:$minuteStart to $hourEnd:$minuteEnd?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.createReservation(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString(), item).observe(viewLifecycleOwner){
                        viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                            if(it.exception == null){
                                if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                                    viewModel.updateList(it.appointment!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                                } else{
                                    viewModel.updateList(it.appointment!!, 0)
                                }
                                hoursAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    calendarAdapter.notifyDataSetChanged()

                }
                .setNegativeButton("No", null).show()
        }

    }

    //press on date
    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(item: CalendarDateModel?) {
        if (item != null) {
            selectedDay = item.calendarDate.toInt()
        }
        viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
            if(it.exception == null){
                if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                    viewModel.updateList(it.appointment!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                }
                else{
                    viewModel.updateList(it.appointment!!, 0)
                }
            }
        }
        calendarAdapter.notifyDataSetChanged()
    }

}