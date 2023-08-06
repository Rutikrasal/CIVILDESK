package com.dg.info.civildesk.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.AddPurchaseBillActivity;
import com.dg.info.civildesk.activity.AddpurchasereportActivity;
import com.dg.info.civildesk.adapter.PurchaseorderAdapter;
import com.dg.info.civildesk.adapter.PurchasereturnAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.ShowPurchaseOrderResponse;
import com.dg.info.civildesk.models.ShowpurchaseReturn;
import com.dg.info.civildesk.utils.NetworkUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseReturnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseReturnFragment extends BaseFragment {
    private Context context;
    ProgressDialog progressDialog;

    public static int REQUEST_CODE = 102;
    String userid;
    RecyclerView rv_purchasebill_list;
    PurchasereturnAdapter adapter;
    ArrayList<String> total=new ArrayList<>();
    String startdate,enddate,TAG="TAG",accId,datese="-04-01";
    TextView tv_amount,msg,tvstartdate,tvenddate;
    String strmonth,name;
    Integer intmonth,intDate1;
    String strDate1,strfdate;
    int msYear,m=3;

    int msMonth ;
    int msDay;

    public PurchaseReturnFragment() {
        // Required empty public constructor
    }

     public static PurchaseReturnFragment newInstance() {
        PurchaseReturnFragment fragment = new PurchaseReturnFragment();
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
        View view = inflater.inflate(R.layout.fragment_purchase_return, container, false);
        setHasOptionsMenu(true);
        progressDialog=new ProgressDialog(context);
        rv_purchasebill_list=view.findViewById(R.id.rv_purchasebill_list);
        tv_amount=view.findViewById(R.id.tv_amount);
        msg=view.findViewById(R.id.msg);
        rv_purchasebill_list.setHasFixedSize(true);
        rv_purchasebill_list.setLayoutManager(new LinearLayoutManager(context));
        tvstartdate=view.findViewById(R.id.tv_startdate);
        tvenddate=view.findViewById(R.id.tv_enddate);
        Calendar calendarm = Calendar.getInstance();
        SimpleDateFormat mdformatm = new SimpleDateFormat("MM");
        strmonth = mdformatm.format(calendarm.getTime());
        intmonth=Integer.parseInt(strmonth);
        Log.d(TAG, "onCreate:intmonth "+intmonth);

        Calendar calendars = Calendar.getInstance();
        SimpleDateFormat mdformats = new SimpleDateFormat("yyyy");
        if (intmonth < 4)
        {
            strDate1 = mdformats.format(calendars.getTime());
            intDate1=Integer.parseInt(strDate1);
            strfdate=String.valueOf(intDate1-1);
            startdate=strfdate+datese;
            tvstartdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(startdate));

        }
        else {

            strDate1 = mdformats.format(calendars.getTime());
            startdate=strDate1+datese;
            tvstartdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(startdate));

        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        enddate = mdformat.format(calendar.getTime());
        // etenddate.setText(strDate);
        tvenddate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(enddate));


        tvstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();

            }
        });
        tvenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerend();

            }
        });


        if (NetworkUtil.getConnectivityStatus(context))
        {
            getpurchasereturndata();

        }
        else
        {
            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        }

        return view;
    }

    private void getpurchasereturndata() {
        progressDialog.show();
        progressDialog.setMessage("Please Wait!!..");
        userid = localData.getSignIn().getUserId();
        Log.d(TAG, "getpurchasebilldata: userid"+userid);
        Call<ShowpurchaseReturn> call = RetrofitClient.getRetrofitClient().showpurchasereturn(userid);
        call.enqueue(new Callback<ShowpurchaseReturn>() {
            @Override
            public void onResponse(Call<ShowpurchaseReturn> call, Response<ShowpurchaseReturn> response) {

                progressDialog.dismiss();

                if (response.body() != null ) {
                    if (response.body().getResponseCode().matches("0")) {

                        if(response.body().getData().size() != 0) {
                            Log.d(TAG, "onResponse: "+response.body().getData());
                            adapter=new PurchasereturnAdapter(context,response.body().getData());
                            rv_purchasebill_list.setAdapter(adapter);
/*
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                total.add(response.body().getData().get(i).getRoundOff());


                            }
                            Log.d(TAG, "getAmenitySlotList: total"+total);
                            additiontotal(total);
*/

                        }
                        else
                        {
                            msg.setVisibility(View.VISIBLE);
                        }
                        //MyProgress.stop();
                        //msg.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {


                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowpurchaseReturn> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

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
                Intent lr=new Intent(context, AddpurchasereportActivity.class);
                lr.putExtra("frag","1");
                startActivityForResult(lr,REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void openDatePicker() {


        final Calendar c = Calendar.getInstance();
        if (intmonth<4)
        {
            msYear = c.get(Calendar.YEAR)-1;

        }
        else {
            msYear = c.get(Calendar.YEAR);

        }
        msMonth =m;
        msDay =01; // add here +1 if you don't
        //want user to select current date also
        c.set(Calendar.DAY_OF_MONTH, msDay);
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        StringBuilder date = new StringBuilder();

                        date.append(year).append("-").append((monthOfYear + 1) < 10 ? "0" : "")
                                .append((monthOfYear + 1)).append("-")
                                .append((dayOfMonth < 10 ? "0" : "")).append(dayOfMonth);
                        startdate=date.toString();
                        m=monthOfYear;
                        Log.d(TAG, "onDateSet: month"+monthOfYear);

                        tvstartdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(date.toString()));

                    }
                }, msYear, msMonth, msDay);

        Calendar cl = Calendar.getInstance();
        cl.set(msYear, 4-1, 1);

        dpd.getDatePicker().setMinDate(cl.getTimeInMillis());
        Calendar cle = Calendar.getInstance();
        cle.set(msYear+1, 3-1, 31);
        dpd.getDatePicker().setMaxDate(cle.getTimeInMillis());
        dpd.show();
    }


    private void openDatePickerend() {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH); // add here +1 if you don't
        //want user to select current date also
        c.set(Calendar.DAY_OF_MONTH, mDay);
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        StringBuilder date = new StringBuilder();

                        date.append(year).append("-").append((monthOfYear + 1) < 10 ? "0" : "")
                                .append((monthOfYear + 1)).append("-")
                                .append((dayOfMonth < 10 ? "0" : "")).append(dayOfMonth);

                        enddate=date.toString();

                        tvenddate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(date.toString()));

                    }
                }, mYear, mMonth, mDay);

        Calendar cl = Calendar.getInstance();
        cl.set(mYear, 4-1, 1);

        dpd.getDatePicker().setMinDate(cl.getTimeInMillis());
        Calendar cle = Calendar.getInstance();
        cle.set(mYear+1, 3-1, 31);
        dpd.getDatePicker().setMaxDate(cle.getTimeInMillis());
        dpd.show();
    }
    public String convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(String strDate) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}