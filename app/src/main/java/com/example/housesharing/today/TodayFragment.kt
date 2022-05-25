package com.example.housesharing.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.Note
import com.example.housesharing.databinding.FragmentTodayBinding
import com.example.housesharing.notes.NotesFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView


class TodayFragment : Fragment(), NotesTodayAdapter.OnItemClickListener {

    private lateinit var todayViewModel: TodayViewModel

    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_today,
            container,
            false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Today"

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        todayViewModel = ViewModelProvider(this).get(TodayViewModel::class.java)

        todayViewModel.fetchNotes().observe(viewLifecycleOwner) {
            if(it.exception == null){
                if(it.notes!!.size == 0){
                    binding.textViewNoNotes.visibility = View.VISIBLE
                    binding.recyclerViewTodayNotes.visibility = View.GONE
                }
                else{
                    binding.textViewNoNotes.visibility = View.INVISIBLE
                    binding.recyclerViewTodayNotes.visibility = View.VISIBLE
                }
                val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                val adapter = NotesTodayAdapter(it.notes!!, this)
                binding.recyclerViewTodayNotes.layoutManager = manager
                binding.recyclerViewTodayNotes.adapter = adapter
            }
        }

        todayViewModel.fetchKitchen().observe(viewLifecycleOwner) {
            if(it.exception == null){
                if(it.appointment!!.size == 0){
                    binding.textViewNoKitchen.visibility = View.VISIBLE
                    binding.recyclerViewTodayKitchen.visibility = View.GONE
                }
                else{
                    binding.textViewNoKitchen.visibility = View.INVISIBLE
                    binding.recyclerViewTodayKitchen.visibility = View.VISIBLE
                }
                val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                val adapter = KitchenTodayAdapter(it.appointment!!)
                binding.recyclerViewTodayKitchen.layoutManager = manager
                binding.recyclerViewTodayKitchen.adapter = adapter
            }
        }

        todayViewModel.fetchBathroom().observe(viewLifecycleOwner) {
            if(it.exception == null){
                if(it.appointment!!.size == 0){
                    binding.textViewNoBathroom.visibility = View.VISIBLE
                    binding.recyclerViewTodayBathroom.visibility = View.GONE
                }
                else{
                    binding.textViewNoBathroom.visibility = View.INVISIBLE
                    binding.recyclerViewTodayBathroom.visibility = View.VISIBLE
                }
                val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                val adapter = BathroomTodayAdapter(it.appointment!!)
                binding.recyclerViewTodayBathroom.layoutManager = manager
                binding.recyclerViewTodayBathroom.adapter = adapter
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    //notes
    override fun onItemClick(item: Note?) {
        val action = TodayFragmentDirections.actionTodayFragmentToDetailsNoteFragment(item!!)
        view?.findNavController()?.navigate(action)
    }


}