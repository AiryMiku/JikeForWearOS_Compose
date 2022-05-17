package airy.github.jike.wearos.presentation.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody

class MainViewModel: ViewModel() {

    private val _profileUiState: MutableStateFlow<ResponseBody?> = MutableStateFlow(null)
    val orderLines: StateFlow<ResponseBody?> get() = _profileUiState


}