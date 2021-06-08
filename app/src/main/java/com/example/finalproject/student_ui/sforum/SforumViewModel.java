package com.example.finalproject.student_ui.sforum;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SforumViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SforumViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}