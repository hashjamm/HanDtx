package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// SharedDateViewModel 에 대한 ViewModelFactory 를 생성
class SharedDateViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedDateViewModel::class.java)) {
            return SharedDateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}