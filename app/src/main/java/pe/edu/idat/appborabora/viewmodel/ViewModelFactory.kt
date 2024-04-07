package pe.edu.idat.appborabora.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    /*
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateUserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
}