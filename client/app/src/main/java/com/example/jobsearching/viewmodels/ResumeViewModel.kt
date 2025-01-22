package com.example.jobsearching.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ResumeItem
import com.example.domain.repository.Repository
import com.example.jobsearching.activities.ResumeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResumeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _resumeItem = MutableLiveData<ResumeItem?>()
    val resumeItem: LiveData<ResumeItem?>
        get() = _resumeItem

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isEditMode = MutableLiveData<Boolean>()
    val isEditMode: LiveData<Boolean>
        get() = _isEditMode

    private val _shouldShowEditDialog = MutableLiveData<Boolean>()
    val shouldShowEditDialog: LiveData<Boolean>
        get() = _shouldShowEditDialog

    private val _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>>
        get() = _tags

    var resumeItemRoom: ResumeItem? = null

    var isEditDialogShown: Boolean = false

    var switchVisibility: Boolean = false

    fun startEditing() {
        _isEditMode.postValue(true)
    }

    fun stopEditing() = viewModelScope.launch(Dispatchers.IO) {
//        _resumeItem.postValue(null)
        _shouldShowEditDialog.postValue(false)
        _isEditMode.postValue(false)
        deleteResume()
    }

    fun changeConfiguration() = viewModelScope.launch(Dispatchers.IO) {
        if (isEditMode.value == true) {
            _shouldShowEditDialog.postValue(true)
            _isEditMode.postValue(false)
        }
    }

    fun loadResume() = viewModelScope.launch(Dispatchers.IO) {
        val data = repository.getResume()
        if (data.isFailure) {
            postError(data.exceptionOrNull()?.message)
        } else {
            val value = data.getOrNull()
            _resumeItem.postValue(value)
            _tags.postValue(value?.tags ?: emptyList<String>())
        }
    }

    fun updateResume(resume: ResumeItem) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val newTags = repository.updateResume(resume)
            _resumeItem.postValue(resume)
            _tags.postValue(newTags)
        } catch (e: Exception) {
            _error.postValue("Invalid input")
        }
        stopEditing()
    }

    private fun postError(message: String?) {
        _error.postValue(message ?: "Something went wrong")
    }

    fun isEditedResume(resume: ResumeItem): Boolean {
        val oldResume = resumeItem.value?.copyWithoutTags()
        val newResume = resume.copyWithoutTags()
        Log.d(TAG, "old = $oldResume")
        Log.d(TAG, "new = $newResume")
        return oldResume != newResume
    }

    fun saveResume(resume: ResumeItem) = viewModelScope.launch(Dispatchers.IO) {
        try {
            withContext(NonCancellable) {
                deleteResume()
                repository.saveResumeLocally(resume)
                _shouldShowEditDialog.postValue(false)
                _isEditMode.postValue(false)
                Log.d(TAG, "Resume were saved = $resume")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error during saving resume", e)
        }
    }

    suspend fun deleteResume() {
        repository.deleteResumeLocally()
    }

    fun getResumeFromRoom() = viewModelScope.launch(Dispatchers.IO) {
        repository.getResumeLocally()?.let {
            _shouldShowEditDialog.postValue(true)
            resumeItemRoom = it
        }

    }

    companion object {
        private const val TAG = ResumeActivity.TAG + "ViewModel"
    }
}