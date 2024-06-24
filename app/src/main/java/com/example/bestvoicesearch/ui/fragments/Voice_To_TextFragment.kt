package com.example.bestvoicesearch.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bestvoicesearch.R
import com.example.bestvoicesearch.databinding.FragmentVoiceToTextBinding


class Voice_To_TextFragment : Fragment() {
    private val TAG = "Voice_To_TextFragment m"
    private lateinit var binding: FragmentVoiceToTextBinding

    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_main_fab
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_main_fab
        )
    }

    private var isFabExpanded = false

    private val REQ_CODE_VOICE_INPUT = 100
    private var languageInitial: String = "en"
    private var adapter : ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVoiceToTextBinding.inflate(layoutInflater, container, false)

        // Set the Toolbar as the ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)


        // Enable the Up button for navigation
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }


        binding.mainFabBtn.setOnClickListener {
            if (isFabExpanded) {
                shrinkFab()
            } else {
                expandFab()
            }
        }


        binding.dropdowntextview.setText("English (US)", false)

        return binding.root
    }

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Create the listener for the spinner and saving current lang initial
        binding.dropdowntextview.setOnItemClickListener { adapterView, view, i, l ->
            val selectedLang = adapterView?.getItemAtPosition(i).toString()
            Toast.makeText(context, "$selectedLang selected", Toast.LENGTH_SHORT).show()

            languageInitial = getLanguageInitial(selectedLang)
            Log.d(TAG, "after $languageInitial")
        }

        //Google voice engine activated onClick of mic button
        binding.microphoneBtn.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageInitial)
                .putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!!!")
            try {
                startActivityForResult(intent, REQ_CODE_VOICE_INPUT)
            } catch (e: ActivityNotFoundException) {
                e.stackTrace
            }
        }

        // For Deleting the Converted text
        binding.deleteBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Do You Want to Delete the Above text?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialogInterface, _ ->
                    binding.voicetotextEdt.text.clear()
                    dialogInterface.dismiss()
                }
                .setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
        }

        //For Copying the text to clipboard
        binding.copyBtn.setOnClickListener {
            val text = binding.voicetotextEdt.text.toString()
            if (text.isNotEmpty()) {
                val clipboardManager =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("key", text)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(requireContext(), "Text Copied to clipboard", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "No text to be copied", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareFabBtn.setOnClickListener {
            shareGlobally()
        }
        binding.whatsappFabBtn.setOnClickListener {
            shareToWhatsapp()
        }
        binding.twitterFabBtn.setOnClickListener {
            shareToTwitter()
        }
        binding.messengerFabBtn.setOnClickListener {
            shareToMessenger()
        }


    }


    //Method called when it return from the google mic prompt
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_VOICE_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.voicetotextEdt.append(result!![0].trimIndent() + "\n")
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

    // Global Share Function
    private fun shareGlobally() {

        val text = binding.voicetotextEdt.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "Can't send empty message", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val shareIntent = Intent().setAction(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, text)
            .setType("text/plain")
        startActivity(shareIntent)

    }

    // Share to WhatsApp Function
    private fun shareToWhatsapp() {
        try {
            val text = binding.voicetotextEdt.text.toString()
            val waIntent = Intent()
            waIntent.setAction(Intent.ACTION_SEND)
            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            waIntent.setType("text/plain")
            waIntent.setPackage("com.whatsapp")
            startActivity(waIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(requireContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                .show()
        }
    }


    // Share to Twitter Function
    private fun shareToTwitter() {
        try {
            val text = binding.voicetotextEdt.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "Can't send empty message", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            val pm: PackageManager? = activity?.packageManager
            val twitterIntent = Intent()
            twitterIntent.setAction(Intent.ACTION_SEND)
            twitterIntent.putExtra(Intent.EXTRA_TEXT, text)
            twitterIntent.setType("text/plain")
            val info = pm!!.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA)

            twitterIntent.setPackage("com.twitter.android")
            startActivity(twitterIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(requireContext(), "Feature Coming Soon", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // Share to Messenger Function
    private fun shareToMessenger() {
        try {
            val text = binding.voicetotextEdt.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "No text found to share", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            val pm: PackageManager? = activity?.packageManager
            val messengerIntent = Intent()
            messengerIntent.setAction(Intent.ACTION_SEND)
            messengerIntent.putExtra(Intent.EXTRA_TEXT, text)
            messengerIntent.setType("text/plain")

            val info = pm!!.getPackageInfo("com.facebook.orca", PackageManager.GET_ACTIVITIES)
            messengerIntent.setPackage("com.facebook.orca")

            startActivity(messengerIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(requireContext(), "Feature Coming Soon", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun expandFab() {
        binding.mainFabBtn.setImageResource(R.drawable.icon_plus_rotated)
        binding.messengerFabBtn.startAnimation(fromBottom)
        binding.whatsappFabBtn.startAnimation(fromBottom)
        binding.twitterFabBtn.startAnimation(fromBottom)
        binding.shareFabBtn.startAnimation(fromBottom)
        isFabExpanded = !isFabExpanded
    }

    private fun shrinkFab() {
        binding.mainFabBtn.setImageResource(R.drawable.icon_plus)
        binding.messengerFabBtn.startAnimation(toBottom)
        binding.whatsappFabBtn.startAnimation(toBottom)
        binding.twitterFabBtn.startAnimation(toBottom)
        binding.shareFabBtn.startAnimation(toBottom)

        isFabExpanded = !isFabExpanded
    }


}