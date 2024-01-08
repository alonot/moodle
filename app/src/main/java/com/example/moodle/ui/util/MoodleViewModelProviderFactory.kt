package com.example.moodle.ui.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moodle.db.repositories.MoodleRepository
import com.example.moodle.ui.MoodleViewsModel

class MoodleViewModelProviderFactory(
    val app: Application,
    val repository: MoodleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoodleViewsModel(app,repository) as T
    }
}