package com.example.finalproject.student_ui.snotes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SnotesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SnotesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}