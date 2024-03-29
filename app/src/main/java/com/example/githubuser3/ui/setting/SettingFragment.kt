package com.example.githubuser3.ui.setting

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser3.R
import com.example.githubuser3.data.datastore.SettingPreferences
import com.example.githubuser3.databinding.FragmentSettingBinding
import com.example.githubuser3.util.SettingFactory


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingFactory(preferences))[SettingViewModel::class.java]

        val language: Array<String> = resources.getStringArray(R.array.language_array)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, language)

        settingViewModel.apply {
            getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                setDarkMode(isDarkModeActive)
            }

            getLocaleSetting().observe(viewLifecycleOwner) {
                if (it == "in") {
                    binding.spLanguage.setSelection(arrayAdapter.getPosition(language[1]))
                } else {
                    binding.spLanguage.setSelection(arrayAdapter.getPosition(language[0]))
                }
            }
        }

        binding.apply {
            scDarkMode.setOnCheckedChangeListener { _, isChecked ->
                settingViewModel.saveThemeSetting(isChecked)
            }

            spLanguage.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent?.getItemAtPosition(position).toString() == language[1]) {
                        setLocaleIndonesia(true, settingViewModel)
                    } else {
                        setLocaleIndonesia(false, settingViewModel)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spLanguage.adapter = arrayAdapter
        }
    }

    private fun setDarkMode(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.scDarkMode.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.scDarkMode.isChecked = false
        }
    }

    private fun setLocaleIndonesia(isIndonesia: Boolean, settingViewModel: SettingViewModel) {
        if (isIndonesia) {
            settingViewModel.saveLocaleSetting("in")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireContext().getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags("in")
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("in"))
            }
        } else {
            settingViewModel.saveLocaleSetting("en")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireContext().getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags("en")
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}