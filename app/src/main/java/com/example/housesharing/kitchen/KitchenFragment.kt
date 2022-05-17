package com.example.housesharing.kitchen

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.CalendarDateModel
import com.example.housesharing.data.Kitchen
import com.example.housesharing.databinding.FragmentKitchenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class KitchenFragment : Fragment(), KitchenAdapter.OnItemClickListener, CalendarAdapter.OnItemClickListener {

    private lateinit var binding: FragmentKitchenBinding

    private lateinit var viewModel: KitchenViewModel

    private lateinit var adapter: KitchenAdapter

    var listHour = ArrayList<Kitchen>()

    //calendar stuff
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var calendarAdapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()



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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Kitchen"

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(KitchenViewModel::class.java)

        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        adapter = KitchenAdapter(listHour, this)
        binding.recyclerViewKitchen.layoutManager = manager
        binding.recyclerViewKitchen.adapter = adapter

//        changeDayData()
//        changeDayButton()
//        setSelectWeekButton()


        setUpCalendarAdapter()
        changeMonthButtons()
        setUpCalendar()


//
//        viewModel.fetchReservations("nextWeek", "monday").observe(viewLifecycleOwner){
//            viewModel.editReservations(it.kitchen!!, "monday", "nextWeek")
//            list.clear()
//            list.addAll(viewModel.thisWeekMondayLiveData.value!!)
//            adapter.notifyDataSetChanged()
//        }
//
//        viewModel.fetchReservations("thisWeek", "tuesday").observe(viewLifecycleOwner){
//            viewModel.editReservations(it.kitchen!!, "tuesday", "thisWeek")
//            list.clear()
//            list.addAll(viewModel.thisWeekTuesdayLiveData.value!!)
//            adapter.notifyDataSetChanged()
//        }
//
//        viewModel.fetchReservations("nextWeek", "tuesday").observe(viewLifecycleOwner){
//            viewModel.editReservations(it.kitchen!!, "tuesday", "nextWeek")
//            list.clear()
//            list.addAll(viewModel.nextWeekTuesdayLiveData.value!!)
//            adapter.notifyDataSetChanged()
//        }

//       val time = "09:30"
//        val dateFormat = SimpleDateFormat("hh:mm")
//        val dateFormat2 = SimpleDateFormat("hh:mm aa")
//        try {
//            val date: Date = dateFormat.parse(time)
//            val out: String = dateFormat2.format(date)
//            Log.d("Time", out)
//        } catch (e: ParseException) {
//        }



        return binding.root
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

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        binding.buttonMonthKitchen.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        calendarAdapter.notifyDataSetChanged()
    }
    private fun setUpCalendarAdapter() {
        calendarAdapter = CalendarAdapter(calendarList2, this)
        binding.recyclerViewKitchen.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewDate.adapter = calendarAdapter
    }

    private fun changeMonthButtons() {
        binding.buttonNextMonthKitchen.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.buttonPreviousMonthKitchen.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }


//    private fun setSelectWeekButton(){
//        viewModel.selectedWeek.observe(viewLifecycleOwner){
//
//            if(it == "thisWeek"){
//                binding.buttonWeekLeftKitchen.isEnabled = false
//                binding.buttonWeekRightKitchen.isEnabled = true
//                binding.buttonKitchenWeek.text = "This Week"
//            }
//            if(it == "nextWeek"){
//                binding.buttonWeekLeftKitchen.isEnabled =true
//                binding.buttonWeekRightKitchen.isEnabled = false
//                binding.buttonKitchenWeek.text = "Next Week"
//            }
//        }
//        binding.buttonWeekRightKitchen.setOnClickListener {
//            viewModel.selectedWeek.value = "nextWeek"
//        }
//        binding.buttonWeekLeftKitchen.setOnClickListener {
//            viewModel.selectedWeek.value = "thisWeek"
//        }
//    }

//    private fun changeDayData(){
//        viewModel.selectedDay.observe(viewLifecycleOwner){ day->
//            if(day == "monday"){
//                resetButtonDayColor()
//                binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week_selected))
//                viewModel.selectedWeek.observe(viewLifecycleOwner){ week->
//                    if(week == "thisWeek" && day == "monday"){
//                        viewModel.fetchReservations("thisWeek", "monday").observe(viewLifecycleOwner){
//                            viewModel.editReservations(it.kitchen!!, "monday", "thisWeek")
//                            listHour.clear()
//                            listHour.addAll(viewModel.thisWeekMondayLiveData.value!!)
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                    else if(week == "nextWeek" && day == "monday"){
//                        viewModel.fetchReservations("nextWeek", "monday").observe(viewLifecycleOwner){
//                            viewModel.editReservations(it.kitchen!!, "monday", "nextWeek")
//                            listHour.clear()
//                            listHour.addAll(viewModel.nextWeekMondayLiveData.value!!)
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                }
//            }
//            if(day == "tuesday"){
//                resetButtonDayColor()
//                binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week_selected))
//                viewModel.selectedWeek.observe(viewLifecycleOwner){ week->
//                    if(week == "thisWeek" && day == "tuesday"){
//                        viewModel.fetchReservations("thisWeek", "tuesday").observe(viewLifecycleOwner){
//                            viewModel.editReservations(it.kitchen!!, "tuesday", "thisWeek")
//                            listHour.clear()
//                            listHour.addAll(viewModel.thisWeekTuesdayLiveData.value!!)
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                    else if(week == "nextWeek" && day == "tuesday"){
//                        viewModel.fetchReservations("nextWeek", "tuesday").observe(viewLifecycleOwner){
//                            viewModel.editReservations(it.kitchen!!, "tuesday", "tuesday")
//                            listHour.clear()
//                            listHour.addAll(viewModel.nextWeekTuesdayLiveData.value!!)
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                }
//            }
//            if(day == "wednesday"){
//                resetButtonDayColor()
//                binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week_selected))
//            }
//            if(day == "thursday"){
//                resetButtonDayColor()
//                binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week_selected))
//            }
//            if(day == "friday"){
//                resetButtonDayColor()
//                binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week_selected))
//            }
//            if(day == "saturday"){
//                resetButtonDayColor()
//                binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week_selected))
//            }
//            if(day == "sunday"){
//                resetButtonDayColor()
//                binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week_selected))
//            }
//        }
//    }
//
//    private fun changeDayButton(){
//        binding.buttonKitchenMonday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "monday"
//        }
//        binding.buttonKitchenTuesday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "tuesday"
//        }
//        binding.buttonKitchenWednesday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "wednesday"
//        }
//        binding.buttonKitchenThursday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "thursday"
//        }
//        binding.buttonKitchenFriday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "friday"
//        }
//        binding.buttonKitchenSaturday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "saturday"
//        }
//        binding.buttonKitchenSunday.setOnClickListener {
//            resetButtonDayColor()
//            binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week_selected))
//            viewModel.selectedDay.value = "sunday"
//        }
//    }
//
//    private fun resetButtonDayColor(){
//        binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week))
//        binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week))
//    }

    override fun onItemClick(item: Kitchen?) {
        viewModel.createReservation(item!!.week!!, item!!.day!!, item!!.time!!).observe(viewLifecycleOwner){
            Toast.makeText(context, item!!.day, Toast.LENGTH_SHORT).show()
            viewModel.fetchReservations(item.week!!, item.day!!).observe(viewLifecycleOwner){
                viewModel.editReservations(it.kitchen!!, item.day!!, item.week!!)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClick(item: CalendarDateModel?) {
//        list.forEach { _ ->
//            item?.isSelected = false
//        }
//        if (item != null) {
//            item.isSelected = true
//        }
//
        calendarAdapter.notifyDataSetChanged()
    }

}