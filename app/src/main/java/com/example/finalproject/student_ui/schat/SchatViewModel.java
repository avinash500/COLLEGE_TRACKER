package com.example.finalproject.student_ui.schat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SchatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SchatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is schat fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}