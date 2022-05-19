package com.example.housesharing.kitchen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.CalendarDateModel
import com.example.housesharing.data.Kitchen
import com.example.housesharing.databinding.FragmentKitchenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*


class KitchenFragment : Fragment(), KitchenAdapter.OnItemClickListener, CalendarAdapter.OnItemClickListener {

    private lateinit var binding: FragmentKitchenBinding

    private lateinit var viewModel: KitchenViewModel

    private lateinit var hoursAdapter: KitchenAdapter

    private var listHour = ArrayList<Kitchen>()

    private var selectedDay = 1

    //calendar stuff
    //how to be displayed on top
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var calendarAdapter: CalendarAdapter
    private val calendarList = ArrayList<CalendarDateModel>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_kitchen,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(KitchenViewModel::class.java)

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
                    viewModel.updateList(it.kitchen!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                }
                else{
                    viewModel.updateList(it.kitchen!!, 0)
                }
            }
            hoursAdapter.notifyDataSetChanged()
        }
        return binding.root
    }


    private fun actionBar(){
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Kitchen"
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
        calendarAdapter = CalendarAdapter(calendarList, this)
        binding.recyclerViewDate.adapter = calendarAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeMonthButtons() {
        binding.buttonNextMonthKitchen.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
            viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                if(it.exception == null){
                    if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                        viewModel.updateList(it.kitchen!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                    }
                    else{
                        viewModel.updateList(it.kitchen!!, 0)
                    }
                    hoursAdapter.notifyDataSetChanged()
                }
            }
            calendarAdapter.selectedItemPosition = 0
            calendarAdapter.notifyDataSetChanged()
        }
        binding.buttonPreviousMonthKitchen.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
            viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                if(it.exception == null){
                    if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                        viewModel.updateList(it.kitchen!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                    }
                    else{
                        viewModel.updateList(it.kitchen!!, 0)
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
        binding.buttonMonthKitchen.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        //maximum number of days in the current month
        var maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        //current calendar month
        val calCurrent = Calendar.getInstance(Locale.ENGLISH)
        if(SimpleDateFormat("M", Locale.ENGLISH).format(calCurrent.time) == SimpleDateFormat("M", Locale.ENGLISH).format(cal.time)){
            binding.buttonPreviousMonthKitchen.isEnabled = false
            monthCalendar.set(Calendar.DAY_OF_MONTH, SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt())
            selectedDay = SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt()
            maxDaysInMonth -= SimpleDateFormat("d", Locale.ENGLISH).format(calCurrent.time).toString().toInt() -1
        }
        else {
            selectedDay = 1
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            binding.buttonPreviousMonthKitchen.isEnabled = true
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
        hoursAdapter = KitchenAdapter(listHour, this)
        binding.recyclerViewKitchen.layoutManager = manager
        binding.recyclerViewKitchen.adapter = hoursAdapter
    }


    //press on hour
    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(item: Kitchen?) {
        if (item != null) {
            viewModel.createReservation(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString(), item).observe(viewLifecycleOwner){
                viewModel.fetchReservations(selectedDay.toString() + SimpleDateFormat("Myy", Locale.ENGLISH).format(cal.time).toString()).observe(viewLifecycleOwner){
                    if(it.exception == null){
                        if(selectedDay == SimpleDateFormat("d", Locale.ENGLISH).format(cal.time).toString().toInt()){
                            viewModel.updateList(it.kitchen!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                        }
                        else{
                            viewModel.updateList(it.kitchen!!, 0)
                        }
                        hoursAdapter.notifyDataSetChanged()
                    }
                }
                }
                calendarAdapter.notifyDataSetChanged()

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
                    viewModel.updateList(it.kitchen!!, SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt())
                }
                else{
                    viewModel.updateList(it.kitchen!!, 0)
                }
            }
        }
        calendarAdapter.notifyDataSetChanged()
    }

}