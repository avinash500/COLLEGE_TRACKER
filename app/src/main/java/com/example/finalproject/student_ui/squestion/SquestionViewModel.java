package com.example.finalproject.student_ui.squestion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SquestionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SquestionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is squestion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}