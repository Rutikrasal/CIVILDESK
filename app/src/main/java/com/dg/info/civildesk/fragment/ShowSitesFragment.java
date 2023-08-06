package com.dg.info.civildesk.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.AddItemsActivity;
import com.dg.info.civildesk.activity.AddSitesActivity;
import com.dg.info.civildesk.adapter.ContactsdisAdapter;
import com.dg.info.civildesk.adapter.SiteAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.models.PartyResponse;
import com.dg.info.civildesk.views.MyDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowSitesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowSitesFragment extends BaseFragment implements SiteAdapter.ContactsAdapterListener {
    ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private ArrayList<PartyResponse.Datum> contactList;
    private SiteAdapter mAdapter;
    private SearchView search_daily_product;
    TextView msg;
    String newID;
    Context context;
    public static int REQUEST_CODE = 102;

    public ShowSitesFragment() {
        // Required empty public constructor
    }

    public static ShowSitesFragment newInstance() {
        ShowSitesFragment fragment = new ShowSitesFragment();
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
        View view = inflater.inflate(R.layout.fragment_show_sites, container, false);
        setHasOptionsMenu(true);
        progressDialog=new ProgressDialog(context);

        recyclerView =view. findViewById(R.id.recycler_view);
        msg=(TextView) view.findViewById(R.id.msg);
        contactList = new ArrayList<>();
        search_daily_product=view.findViewById(R.id.search_daily_product);

        mAdapter = new SiteAdapter(context, contactList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
        getSiteListData();
        search_daily_product.setQueryHint("Search Customer");

        search_daily_product.setMaxWidth(Integer.MAX_VALUE);

        search_daily_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);

                return false;
            }
        });
        search_daily_product.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }
        );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create, menu);

        return;
    }

    private void getSiteListData() {
        progressDialog.setMessage("Please Wait!!");
        progressDialog.show();
        Call<PartyResponse> call = RetrofitClient.getRetrofitClient().getparty();
        call.enqueue(new Callback<PartyResponse>() {
            @Override
            public void onResponse(Call<PartyResponse> call, Response<PartyResponse> response) {
                if (response.body() != null ) {
                    progressDialog.dismiss();

                    if (response.body().getResponseCode().toString().matches("0")) {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            contactList.clear();
                            contactList.addAll(response.body().getData());

                            // refreshing recycler view
                            mAdapter.notifyDataSetChanged();

                        }



                    } else {
                        //showAlertForEnquiry();
                    }
                } else {

                    //Toast.makeText(LoginScreenActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PartyResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(context, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:

                Intent lr=new Intent(context, AddSitesActivity.class);
                lr.putExtra("frag","1");
                startActivityForResult(lr,REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onContactSelected(CustomerResponse.Datum contact) {

    }
}