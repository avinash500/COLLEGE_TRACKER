package com.example.finalproject.student_ui.sattendance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SattendanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SattendanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sattendance fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}