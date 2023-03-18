package com.qc.ssm.ifc.feelclimate.viewmodels

/*import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject*/
import androidx.lifecycle.*
import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.repository.MainRepository
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.viewmodels.MainStateEvent.GetClimateEvents.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<ClimateModel>> = MutableLiveData()

    val dataState: LiveData<DataState<ClimateModel>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetClimateEvents -> {
                    mainRepository.getClimate(mainStateEvent.data)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    // No action
                }
            }
        }
    }
}

sealed class MainStateEvent(var data: String) {
    object GetClimateEvents : MainStateEvent(data)
    object None : MainStateEvent("")
}

