package com.dg.info.civildesk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.AddItemResponse;
import com.dg.info.civildesk.models.AddSiteResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSitesActivity extends BaseActivity {
    ProgressDialog progressDialog;
    Context context;
    EditText etsitename,etgstnum,etmobilenumber,etaddress;
    String selectedstate,sitename,gst,mobilenum,address,statecode;
    Button btnsubmit;
    Spinner spnstate;
    ArrayList<String> states=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sites);
        setUpToolbarBackButton("Add Party");
        context=AddSitesActivity.this;
        progressDialog=new ProgressDialog(context);
        etsitename=findViewById(R.id.edt_sitename);
        etgstnum=findViewById(R.id.edt_gstno);
        etmobilenumber=findViewById(R.id.edt_phoneno);
        etaddress=findViewById(R.id.edt_address);
        spnstate=findViewById(R.id.spn_state);
        btnsubmit=findViewById(R.id.btn_submititems);
        states.add("Select state");
        states.add("Andhra Pradesh");
        states.add("Arunachal Pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Chhattishgarh");
        states.add("Goa");
        states.add("Gujarat");
        states.add("Haryana");
        states.add("Himachal Pradesh");
        states.add("Jammu and Kashmir");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Lakshadweep");
        states.add("Madhya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Odisha");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("Sikkim");
        states.add("Tamil Nadu");
        states.add("Telangana");
        states.add("Tripura");
        states.add("Uttar Pradesh");
        states.add("Uttarakhand");
        states.add("West Bengal");

        ArrayAdapter<String> adapterpaytype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, states);
        adapterpaytype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnstate.setAdapter(adapterpaytype);

        spnstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedstate= parent.getItemAtPosition(position).toString();

                if (selectedstate.matches("Andhra Pradesh"))
                {
                    statecode="AP";
                }
                if (selectedstate.matches("Arunachal Pradesh"))
                {
                    statecode="AR";
                }
                if (selectedstate.matches("Assam"))
                {
                    statecode="AS";
                }
                if (selectedstate.matches("Bihar"))
                {
                    statecode="BR";
                }
                if (selectedstate.matches("Chattisgarh"))
                {
                    statecode="CG";
                }
                if (selectedstate.matches("Delhi"))
                {
                    statecode="DL";
                }
                if (selectedstate.matches("Goa"))
                {
                    statecode="GA";
                }
                if (selectedstate.matches("Gujarat"))
                {
                    statecode="GJ";
                }
                if (selectedstate.matches("Haryana"))
                {
                    statecode="HR";
                }
                if (selectedstate.matches("Himachal Pradesh"))
                {
                    statecode="HP";
                }
                if (selectedstate.matches("Jammu and Kashmir"))
                {
                    statecode="JK";
                }
                if (selectedstate.matches("Jharkhand"))
                {
                    statecode="JH";
                }
                if (selectedstate.matches("Karnataka"))
                {
                    statecode="KA";
                }
                if (selectedstate.matches("Kerala"))
                {
                    statecode="KL";
                }
                if (selectedstate.matches("Lakshadweep"))
                {
                    statecode="LD";
                }
                if (selectedstate.matches("Madhya Pradesh"))
                {
                    statecode="MP";
                }
                if (selectedstate.matches("Maharashtra"))
                {
                    statecode="MH";
                }
                if (selectedstate.matches("Manipur"))
                {
                    statecode="MN";
                }
                if (selectedstate.matches("Meghalaya"))
                {
                    statecode="ML";
                }
                if (selectedstate.matches("Mizoram"))
                {
                    statecode="MZ";
                }
                if (selectedstate.matches("Nagaland"))
                {
                    statecode="NL";
                }
                if (selectedstate.matches("Odisha"))
                {
                    statecode="OD";
                }
                if (selectedstate.matches("Pondicherry"))
                {
                    statecode="PY";
                }
                if (selectedstate.matches("Punjab"))
                {
                    statecode="PB";
                }
                if (selectedstate.matches("Rajasthan"))
                {
                    statecode="RJ";
                }
                if (selectedstate.matches("Sikkim"))
                {
                    statecode="SK";
                }
                if (selectedstate.matches("Tamil Nadu"))
                {
                    statecode="TN";
                }
                if (selectedstate.matches("Telangana"))
                {
                    statecode="TS";
                }
                if (selectedstate.matches("Tripura"))
                {
                    statecode="TR";
                }
                if (selectedstate.matches("Uttar Pradesh"))
                {
                    statecode="UP";
                }
                if (selectedstate.matches("Uttarakhand"))
                {
                    statecode="UK";
                }
                if (selectedstate.matches("West Bengal"))
                {
                    statecode="WB";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sitename=etsitename.getText().toString();
                gst=etgstnum.getText().toString();
                mobilenum=etmobilenumber.getText().toString();
                address=etaddress.getText().toString();
                if (TextUtils.isEmpty(sitename))
                {
                    etsitename.requestFocus();
                    etsitename.setError("Enter Site Name");
                    return;

                }
                if (TextUtils.isEmpty(gst))
                {
                    etgstnum.requestFocus();
                    etgstnum.setError("Enter GST Number");
                    return;
                }
                if (TextUtils.isEmpty(mobilenum))
                {
                    etmobilenumber.requestFocus();
                    etmobilenumber.setError("Enter Mob Number");
                    return;


                }
                if (TextUtils.isEmpty(address))
                {
                    etaddress.requestFocus();
                    etaddress.setError("Enter Address");
                    return;
                }
                if (selectedstate.matches("Select State"))
                {
                    Toast.makeText(context,"Please select State",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    addsite();
                }

            }
        });





    }

    private void addsite() {
        progressDialog.show();
        progressDialog.setMessage("please Wait...");
        Call<AddSiteResponse> call = RetrofitClient.getRetrofitClient().addsite(sitename,gst,mobilenum,address,statecode,selectedstate);
        call.enqueue(new Callback<AddSiteResponse>() {
            @Override
            public void onResponse(Call<AddSiteResponse> call, Response<AddSiteResponse> response) {
                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }

            }

            @Override
            public void onFailure(Call<AddSiteResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(AddSitesActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}