package com.dg.info.civildesk.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.AddleavesActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowLeavesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowLeavesFragment extends BaseFragment {

    private RecyclerView rvLeaveList;
    private Context context;
    public static int REQUEST_CODE = 102;

    public ShowLeavesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShowLeavesFragment newInstance() {
        ShowLeavesFragment fragment = new ShowLeavesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_leaves, container, false);
        setHasOptionsMenu(true);
        rvLeaveList = view.findViewById(R.id.rv_leave_list);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create, menu);

        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                Intent lr=new Intent(context, AddleavesActivity.class);
                startActivityForResult(lr,REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}