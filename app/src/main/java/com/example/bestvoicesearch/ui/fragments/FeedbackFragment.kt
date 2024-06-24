package com.example.bestvoicesearch.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bestvoicesearch.CheckUpdates
import com.example.bestvoicesearch.R
import com.example.bestvoicesearch.databinding.FragmentFeedbackBinding
import com.example.bestvoicesearch.utils.SharedPref


class FeedbackFragment : Fragment() {
    private lateinit var binding: FragmentFeedbackBinding
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(layoutInflater, container, false)

        // Navigation Drawer SetUp
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.appbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout?.addDrawerListener(actionBarDrawerToggle!!)


        // Click listners on  Navigation Drawer Icons.........

        binding.navigationView?.setNavigationItemSelectedListener { menuItem: MenuItem ->
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
                    startActivity(Intent(requireContext(), CheckUpdates::class.java))
                }

            }
            binding.drawerLayout?.closeDrawer(GravityCompat.START)
            true
        }

        // Intializing Switch button .....

        val menuItem = binding.navigationView.menu.findItem(R.id.darkmode)
        val switchButton = menuItem?.actionView as SwitchCompat

        switchButton.setThumbResource(R.drawable.switch_bg)
        switchButton.isChecked = false
//        /** Check night mode state from shared pref
//         *  if nightmode then switch button on by default
//         *  else switch button off by default
//         */
//        if(SharedPref.getNightModeStateFromSharedPref(context)){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            switchButton.isChecked = true
//            menuItem.title = "Light Mode"
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            switchButton.isChecked = false
//            menuItem.title = "Dark Mode"
//        }

        // Using condition to change LIGHT MODE TO DARK , DARK TO LIGHT  ...
        // And saving its current state to shared prefernce
        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menuItem.title = "Light Mode"
                //SharedPref.saveNightModeStateToSharedPref(context,true)
            } else {
                menuItem.title = "Dark Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
               // SharedPref.saveNightModeStateToSharedPref(context,false)

            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.ratingBar!!.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) { // Check if the rating was changed by the user and not programmatically
                if (rating <= 3) {
                    // User rated less than 3, so submit feedback
                    binding.editText.visibility=View.VISIBLE
                    binding.feedbackEdt.visibility=View.VISIBLE
                    binding.cancelButton.visibility=View.VISIBLE
                    binding.submitButton.visibility=View.VISIBLE
                    binding.cancelButton.setOnClickListener {

                        if(binding.feedbackEdt.text.toString().isEmpty()){
                            binding.feedbackEdt.error = "This can not be empty"
                        }
                        else{
                            Toast.makeText(requireContext(), "You canceled the feedback", Toast.LENGTH_SHORT).show()
                            binding.ratingBar!!.rating = 0F
                            binding.feedbackEdt.text!!.clear()
                        }
                    }
                    binding.submitButton.setOnClickListener {

                        if(binding.feedbackEdt.text.toString().isEmpty()){
                            binding.feedbackEdt.error = "This can not be empty"
                        }
                        else{
                            AlertDialog.Builder(requireContext())
                                .setMessage("Thanks for Submitting Your Valuable Feedback")
                                .setCancelable(false)
                                .setPositiveButton("Ok") { dialogInterface, _ ->
                                    view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.homeFragment) }
                                    dialogInterface.dismiss()
                                }
                                .create()
                                .show()

                        }

                    }
                   // Toast.makeText(requireContext(), "Ok", Toast.LENGTH_SHORT).show()
                } else {
                    // User rated 3 or more, open Play Store review section
                    binding.editText.visibility=View.GONE
                    binding.feedbackEdt.visibility=View.GONE
                    binding.cancelButton.visibility=View.GONE
                    binding.submitButton.visibility=View.GONE
                    startActivity(Intent(requireContext(),CheckUpdates::class.java))
                }
            }
        }
        return binding.root

    }


}