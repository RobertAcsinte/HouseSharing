package com.example.housesharing.kitchen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.Kitchen
import com.example.housesharing.databinding.FragmentKitchenBinding
import com.example.housesharing.notes.NotesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class KitchenFragment : Fragment(), KitchenAdapter.OnItemClickListener {

    private lateinit var binding: FragmentKitchenBinding

    private lateinit var viewModel: KitchenViewModel

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
        viewModel.selectedWeek.observe(viewLifecycleOwner){
            if(it == "monday"){
                resetButtonWeekColor()
                binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "tuesday"){
                resetButtonWeekColor()
                binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "wednesday"){
                resetButtonWeekColor()
                binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "thursday"){
                resetButtonWeekColor()
                binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "friday"){
                resetButtonWeekColor()
                binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "saturday"){
                resetButtonWeekColor()
                binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week_selected))
            }
            if(it == "sunday"){
                resetButtonWeekColor()
                binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week_selected))
            }
        }

        var list = ArrayList<Kitchen>()
        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        val adapter = KitchenAdapter(list, this)
        binding.recyclerViewKitchen.layoutManager = manager
        binding.recyclerViewKitchen.adapter = adapter

        viewModel.selectedWeek.observe(viewLifecycleOwner){
            if(it == "monday"){
                list.add(Kitchen("dfaas", "monday", "00:00 - 00:30"))
                adapter.notifyDataSetChanged()
            }
        }


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Kitchen"

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        setSelectedWeekButtonColor()
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

    private fun setSelectedWeekButtonColor(){
        binding.buttonKitchenMonday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "monday"
        }
        binding.buttonKitchenTuesday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "tuesday"
        }
        binding.buttonKitchenWednesday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "wednesday"
        }
        binding.buttonKitchenThursday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "thursday"
        }
        binding.buttonKitchenFriday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "friday"
        }
        binding.buttonKitchenSaturday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "saturday"
        }
        binding.buttonKitchenSunday.setOnClickListener {
            resetButtonWeekColor()
            binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week_selected))
            viewModel.selectedWeek.value = "sunday"
        }
    }

    private fun resetButtonWeekColor(){
        binding.buttonKitchenMonday.setBackgroundColor(binding.buttonKitchenMonday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenTuesday.setBackgroundColor(binding.buttonKitchenTuesday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenWednesday.setBackgroundColor(binding.buttonKitchenWednesday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenThursday.setBackgroundColor(binding.buttonKitchenThursday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenFriday.setBackgroundColor(binding.buttonKitchenFriday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenSaturday.setBackgroundColor(binding.buttonKitchenSaturday.context.resources.getColor(R.color.background_button_week))
        binding.buttonKitchenSunday.setBackgroundColor(binding.buttonKitchenSunday.context.resources.getColor(R.color.background_button_week))
    }

    override fun onItemClick(item: Kitchen?) {

    }

}