package com.dg.info.civildesk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.adapter.ContactsAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.views.MyDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomSelectableListActivity extends BaseActivity implements ContactsAdapter.ContactsAdapterListener {
    private RecyclerView recyclerView;
    private ArrayList<CustomerResponse.Datum> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    TextView msg;
    String newID,TAG="TAG";
    ProgressDialog progressDialog;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_selectable_list);
        context=CustomSelectableListActivity.this;

        setUpToolbarBackColor("Select Distributor" );
        progressDialog=new ProgressDialog(context);


        recyclerView = findViewById(R.id.recycler_view);
        msg=(TextView) findViewById(R.id.msg);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        // fetchContacts();
        getCustomersListData();
    }

    private void getCustomersListData() {
        progressDialog.show();
        progressDialog.setMessage("Please Wait!!");
        Call<CustomerResponse> call = RetrofitClient.getRetrofitClient().getcustomer();
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.body() != null ) {
                    progressDialog.dismiss();

                    if (response.body().getResponseCode().toString().matches("0")) {
                        Log.d(TAG, "onResponse: ");
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
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "" + t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(CustomSelectableListActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serchable_list, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();


        searchView.setQueryHint("Search Customer");
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    @Override
    public void onContactSelected(CustomerResponse.Datum contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getCustomerName() , Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        //intent.putExtra("MESSAGE", contact.getCompany());
        intent.putExtra("CustomerData", contact);
        setResult(2,intent);
        finish();//finishing activity
    }
}