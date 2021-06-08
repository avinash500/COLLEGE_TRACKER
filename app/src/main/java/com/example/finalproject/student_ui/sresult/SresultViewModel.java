package com.example.finalproject.student_ui.sresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SresultViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SresultViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is result fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}