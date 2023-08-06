package com.dg.info.civildesk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dg.info.civildesk.utils.LocalData;


public class BaseFragment extends Fragment {
    protected LocalData localData;
    protected Context context;

    public static boolean is_ckeck_in=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        localData = LocalData.getInstance(context);
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
