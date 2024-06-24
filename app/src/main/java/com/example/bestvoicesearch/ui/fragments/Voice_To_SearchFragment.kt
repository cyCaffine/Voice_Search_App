package com.example.bestvoicesearch.ui.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.bestvoicesearch.R
import com.example.bestvoicesearch.databinding.FragmentVoiceToSearchBinding


class Voice_To_SearchFragment : Fragment() {
    private val TAG = "Voice_To_SearchFragment"
    private lateinit var binding: FragmentVoiceToSearchBinding

    private val REQ_CODE_VOICE_INPUT = 100
    private var languageInitial: String = "en"
    private var adapter : ArrayAdapter<String>? = null

    override fun onResume() {
        super.onResume()
        val language = ArrayList<String>()
        language.add("Amharic")
        language.add("Arabic")
        language.add("Basque")
        language.add("Bengali")
        language.add("English (UK)")
        language.add("Portuguese (Brazil)")
        language.add("Bulgarian")
        language.add("Catalan")
        language.add("Cherokee")
        language.add("Croatian")
        language.add("Czech")
        language.add("Danish")
        language.add("Dutch")
        language.add("English (US)")
        language.add("Estonian")

        language.add("Filipino")
        language.add("Finnish")
        language.add("French")
        language.add("German")
        language.add("Greek")
        language.add("Gujarati")
        language.add("Hebrew")
        language.add("Hindi")
        language.add("Hungarian")
        language.add("Icelandic")
        language.add("Indonesian")
        language.add("Italian")
        language.add("Japanese")
        language.add("Kannada")
        language.add("Korean")
        language.add("Latvian")
        language.add("Lithuanian")
        language.add("Malay")
        language.add("Malayalam")
        language.add("Marathi")
        language.add("Norwegian")
        language.add("Polish")
        language.add("Portuguese (Portugal)")
        language.add("Romanian")
        language.add("Russian")
        language.add("Serbian")
        language.add("Chinese (PRC)")
        language.add("Slovak")
        language.add("Slovenian")
        language.add("Spanish")
        language.add("Swahili")
        language.add("Swedish")
        language.add("Tamil")
        language.add("Telugu")
        language.add("Thai")
        language.add("Chinese (Taiwan)")
        language.add("Turkish")
        language.add("Urdu")
        language.add("Ukrainian")
        language.add("Vietnamese")
        language.add("Welsh")
        language.add("Serbian")
        language.add("english")

        //Get string array from resource and show in dropdown spinner
        //val itemarray = resources.getStringArray(R.array.languages)
        adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            language
        )
        binding.dropdowntextview.setAdapter(adapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVoiceToSearchBinding.inflate(layoutInflater, container, false)

        // Set the Toolbar as the ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        // Enable the Up button for navigation
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener{
            activity.onBackPressed()
        }

        binding.dropdowntextview.setText("English (US)", false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Create the listener for the spinner and saving current lang initial
        binding.dropdowntextview.setOnItemClickListener { adapterView, view, i, l ->
            val selectedLang = adapterView?.getItemAtPosition(i).toString()
            Toast.makeText(context, "$selectedLang selected", Toast.LENGTH_SHORT).show()

            languageInitial = getLanguageInitial(selectedLang)
        }

        //Google voice engine activated onClick of mic button
        binding.microphoneBtn.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageInitial)
                .putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!!!")
            try {
                startActivityForResult(intent, REQ_CODE_VOICE_INPUT)
            } catch (e: ActivityNotFoundException) {
                e.stackTrace
            }
        }

        binding.searchbar?.setOnClickListener {
            if(binding.editTextText.text.toString().isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("search", binding.editTextText.text.toString().trim())
                findNavController(requireView()).navigate(R.id.action_voice_To_SearchFragment_to_webViewFragment, bundle)
            }else{
                binding.editTextText.error = "Enter any text"
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_CODE_VOICE_INPUT){
            if(resultCode == Activity.RESULT_OK && data != null){
                val searchStr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]

                binding.editTextText.setText(searchStr)

                val bundle = Bundle()
                bundle.putString("search", searchStr)
                findNavController(requireView()).navigate(R.id.action_voice_To_SearchFragment_to_webViewFragment, bundle)


            }
        }
    }




    /** Helper function
     * to get the initial of the language selected from dropdown spinner
     */
    private fun getLanguageInitial(selectedLang: String): String {
        return when (selectedLang) {
            "Amharic" -> "am"
            "Arabic" -> "ar"
            "Basque" -> "eu"
            "Bengali" -> "bn"
            "English (UK)" -> "en-GB"
            "Portuguese (Brazil)" -> "pt-BR"
            "Bulgarian" -> "bg"
            "Catalan" -> "ca"
            "Cherokee" -> "chr"
            "Croatian" -> "hr"
            "Czech" -> "cs"
            "Danish" -> "da"
            "Dutch" -> "nl"
            "English (US)" -> "en"
            "Estonian" -> "et"
            "Filipino" -> "fil"
            "Finnish" -> "fi"
            "French" -> "fr"
            "German" -> "de"
            "Greek" -> "el"
            "Gujarati" -> "gu-IN"
            "Hebrew" -> "iw"
            "Hindi" -> "hi"
            "Hungarian" -> "hu"
            "Icelandic" -> "is"
            "Indonesian" -> "id"
            "Italian" -> "it"
            "Japanese" -> "ja"
            "Kannada" -> "kn"
            "Korean" -> "ko"
            "Latvian" -> "lv"
            "Lithuanian" -> "lt"
            "Malay" -> "ms"
            "Malayalam" -> "ms"
            "Marathi" -> "mr"
            "Norwegian" -> "no"
            "Polish" -> "pl"
            "Portuguese (Portugal)" -> "pt-PT"
            "Romanian" -> "ro"
            "Russian" -> "ru"
            "Serbian" -> "sr"
            "Chinese (PRC)" -> "zh-CN"
            "Slovak" -> "sk"
            "Slovenian" -> "sl"
            "Spanish" -> "es"
            "Swahili" -> "sw"
            "Swedish" -> "sv"
            "Tamil" -> "ta"
            "Telugu" -> "te"
            "Thai" -> "th"
            "Chinese (Taiwan)" -> "zh-TW"
            "Turkish" -> "tr"
            "Urdu" -> "ur-IN"
            "Ukrainian" -> "uk"
            "Vietnamese" -> "vi"
            "Welsh" -> "cy"
            else -> "en"
        }
    }





}