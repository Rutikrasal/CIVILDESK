package com.dg.info.civildesk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.dg.info.civildesk.models.AddPurchasebillResponse;
import com.dg.info.civildesk.models.TaxResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemsActivity extends BaseActivity {
    Context context;
    EditText etitemname;
    EditText etitemhsn;

    Spinner spnunit,spntax;
    String selectedunit,selectedtax,itemname,itemhsn;
    Button btnsubmit;
    ArrayList<String> units=new ArrayList<>();
    ArrayList<TaxResponse.Datum> taxes=new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        context=AddItemsActivity.this;
        progressDialog=new ProgressDialog(context);
        setUpToolbarBackButton("Add Items");
        etitemname=findViewById(R.id.edt_itemname);
        etitemhsn=findViewById(R.id.edt_itemhsn);
        spnunit=findViewById(R.id.spn_unit);
        spntax=findViewById(R.id.spn_tax);
        btnsubmit=findViewById(R.id.btn_submititems);
        units.add("Select Unit");
        units.add("BAGS(Bag)");
        units.add("BOTTELS(Btl)");
        units.add("BOX(Box)");
        units.add("BUNDLES(Bdl)");
        units.add("CANS(Can)");
        units.add("GRAMMES(Gm)");
        units.add("KILOGRAMS(Kg)");
        units.add("LITER(Ltr)");
        units.add("MITERS(Mtr)");
        units.add("MILILITER(Mi)");
        units.add("NUMBERS(Nos)");
        units.add("PACKS(Pac)");
        units.add("PAIRS(Prs)");
        units.add("PIECES(Pcs)");

        ArrayAdapter<String> adapterpaytype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, units);
        adapterpaytype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnunit.setAdapter(adapterpaytype);

        spnunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedunit= parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        gettax();
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemname = etitemname.getText().toString();
                itemhsn=etitemhsn.getText().toString();
                if (TextUtils.isEmpty(itemname))
                {
                    etitemname.setError("Enter Item Name");
                    etitemname.requestFocus();
                    return;
                }
                if (selectedunit.matches("Select Unit"))
                {
                    Toast.makeText(context,"Please Select Unit",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedtax.matches("Select Tax"))
                {
                    Toast.makeText(context,"Please Select tax",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    calladditemApi();
                }
            }
        });

    }

    private void gettax() {
        Call<TaxResponse> call = RetrofitClient.getRetrofitClient().get_tax();
        call.enqueue(new Callback<TaxResponse>() {
            @Override
            public void onResponse(Call<TaxResponse> call, Response<TaxResponse> response) {
                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    taxes.addAll(response.body().getData());
                    ArrayList<String> services = new ArrayList<>();
                    services.add("Select Tax");
                    for (int i = 0; i < taxes.size(); i++) {
                        services.add( taxes.get(i).getTaxname());
                    }

                    ArrayAdapter<String> adaptertax = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, services);
                    adaptertax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spntax.setAdapter(adaptertax);

                    spntax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedtax= parent.getItemAtPosition(position).toString();
                            //Toast.makeText(context,selectedtax,Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }

            }

            @Override
            public void onFailure(Call<TaxResponse> call, Throwable t) {

                Toast.makeText(AddItemsActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void calladditemApi() {
        progressDialog.show();
        progressDialog.setMessage("Please Wait...");
        Call<AddItemResponse> call = RetrofitClient.getRetrofitClient().additem(itemname,selectedunit,selectedtax,itemhsn);
        call.enqueue(new Callback<AddItemResponse>() {
            @Override
            public void onResponse(Call<AddItemResponse> call, Response<AddItemResponse> response) {
                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }

            }

            @Override
            public void onFailure(Call<AddItemResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(AddItemsActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}