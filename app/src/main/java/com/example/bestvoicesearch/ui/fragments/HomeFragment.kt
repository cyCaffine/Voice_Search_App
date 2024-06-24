package com.example.bestvoicesearch.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bestvoicesearch.CheckUpdates
import com.example.bestvoicesearch.R
import com.example.bestvoicesearch.databinding.FragmentHomeBinding
import com.example.bestvoicesearch.utils.SharedPref


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        // Navigation Drawer SetUp
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.appbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)


        // Click listners on  Navigation Drawer Icons.........

        binding.navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    view?.let { Navigation.findNavController(it).navigate(R.id.homeFragment) }
                }

                R.id.feedbackFragment -> {
                    view?.let { Navigation.findNavController(it).navigate(R.id.feedbackFragment) }
                }

                R.id.privacy_Policy_Fragment -> {
                    view?.let {
                        Navigation.findNavController(it).navigate(R.id.privacy_Policy_Fragment)
                    }
                }

                R.id.about_Us_Fragment -> {
                    view?.let { Navigation.findNavController(it).navigate(R.id.about_Us_Fragment) }
                }

                R.id.checkUpdates -> {
                      startActivity(Intent(requireContext(),CheckUpdates::class.java))
                }

            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Intializing Switch button .....

        val menuItem = binding.navigationView.menu.findItem(R.id.darkmode)
        val switchButton = menuItem.actionView as SwitchCompat
        switchButton.setThumbResource(R.drawable.switch_bg)
        switchButton.isChecked = false


        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menuItem.title = "Light Mode"
                //SharedPref.saveNightModeStateToSharedPref(context,true)

            } else {
                menuItem.title = "Dark Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //SharedPref.saveNightModeStateToSharedPref(context,false)

            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        // Setting Button Actions..

        binding.voiceToSearchButton.setOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_homeFragment_to_voice_To_SearchFragment)
            }
        }
        binding.voiceToTextButton.setOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_homeFragment_to_voice_To_TextFragment)
            }
        }
        return binding.root
    }



}