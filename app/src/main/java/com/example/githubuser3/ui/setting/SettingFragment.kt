package com.example.githubuser3.ui.setting

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
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
        val settingViewModel = ViewModelProvider(this, SettingFactory(preferences))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                Log.d(TAG, "masuk true")
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
                    UiModeManager.MODE_NIGHT_YES
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                binding.scDarkMode.isChecked = true
            } else {
                Log.d(TAG, "masuk false")
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
                    UiModeManager.MODE_NIGHT_NO
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                binding.scDarkMode.isChecked = false
            }
        }

        binding.scDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, isChecked.toString())
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "setting"
    }
}