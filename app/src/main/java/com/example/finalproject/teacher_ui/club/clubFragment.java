package com.example.finalproject.teacher_ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalproject.R;

class ClubFragment extends Fragment {

    private com.example.finalproject.teacher_ui.club.ClubViewModel mViewModel;
    private ActionBar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mViewModel =
                ViewModelProviders.of(this).get(ClubViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sclub, container, false);




        return root;
    }



}

