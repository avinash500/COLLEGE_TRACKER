package com.example.finalproject.student_ui.sresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;


import java.util.ArrayList;

public class SresultFragment extends Fragment {



    WebView webView;

    private SresultViewModel sresultViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        sresultViewModel =
                ViewModelProviders.of(this).get(SresultViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sresult, container, false);

//        webView =root.findViewById(R.id.web);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://136.232.6.238:8080/CVRCEResult/");

        Uri webpage = Uri.parse("http://136.232.6.238:8080/CVRCEResult/");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);



        return root;
    }


}