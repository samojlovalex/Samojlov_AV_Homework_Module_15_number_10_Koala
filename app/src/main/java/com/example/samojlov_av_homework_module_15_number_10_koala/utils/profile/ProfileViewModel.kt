package com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProfileRepository
    val profiles: LiveData<List<Profile>>

    init {
        val dao = ProfileDataBase.getDataBase(application).getProfileDao()
        repository = ProfileRepository(dao)
        profiles = repository.profiles
    }

    fun deleteProfile(profile: Profile) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(profile)
    }

    fun insertProfile(profile: Profile) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(profile)
    }

    fun updateProfile(profile: Profile) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(profile)
    }

    fun deleteAllProfile() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}