package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelForEachDateViewerFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelForEachDateViewer::class.java)) {
            return ViewModelForEachDateViewer(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}