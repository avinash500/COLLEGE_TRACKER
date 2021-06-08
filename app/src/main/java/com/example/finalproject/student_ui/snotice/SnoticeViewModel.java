package com.example.finalproject.student_ui.snotice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SnoticeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SnoticeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is snotice fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}