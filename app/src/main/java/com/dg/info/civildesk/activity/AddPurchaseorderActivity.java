package com.dg.info.civildesk.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.adapter.ItemAddedAdapter;
import com.dg.info.civildesk.adapter.ItemAddedpurchaseorderAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.fragment.PaymentOutFragment;
import com.dg.info.civildesk.fragment.PurchaseBillFragment;
import com.dg.info.civildesk.models.AddPurchaseOrderResponse;
import com.dg.info.civildesk.models.AddPurchaseReturnResponse;
import com.dg.info.civildesk.models.CustomerBalanceResponse;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.models.ItemAdd;
import com.dg.info.civildesk.utils.FilePath;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

public class AddPurchaseorderActivity extends BaseActivity {
    LinearLayout linearLayoutsave;
    String ordernumber,totalamount,advanceamount,balanceamout,refno,description;
    ArrayList<String> item_id=new ArrayList<>();
    ArrayList<String> item_name=new ArrayList<>();
    ArrayList<String> item_taxname=new ArrayList<>();
    ArrayList<String> item_taxpercent=new ArrayList<>();
    ArrayList<String> item_discountpercent=new ArrayList<>();
    ArrayList<String> item_unit=new ArrayList<>();
    ArrayList<String> item_qty=new ArrayList<>();
    ArrayList<String> item_price=new ArrayList<>();
    ArrayList<String> item_subtotal=new ArrayList<>();
    ArrayList<String> item_discount=new ArrayList<>();
    ArrayList<String> item_taxes=new ArrayList<>();
    ArrayList<String> item_total=new ArrayList<>();

    TextView tvtotaldiscamount,tvtotaltaxamount,tvtotalqty,tvfinalsubtotal;
    Spinner spnpaymenttype,spnstateofsupply;
    ArrayList<String> paymenttype=new ArrayList<>();
    ArrayList<String> stateofsupply=new ArrayList<>();
    EditText etorderno,etdate,etcustomername,etduedate,ettotalamount,etadvanceamount,etrefno,etdescription;
    TextView tvbalanceamount,tvadditem;
    Context context;
    ProgressDialog progressDialog;
    String strDate,cutomername,Image,imgEncodedString,duedate,selectedpaymenttype,selectedstate;
    protected static final int PICK_FILE = 101;
    ImageView imageView;
    private CustomerResponse.Datum customer = null;
    RecyclerView recyclerViewitems;
    LinearLayout linearLayoutamount,linearLayoutpaymenttype,linearLayoutdesign,linearLayoutdesc,linearLayoutrecyclerview;
    String a,siteid;
    private ArrayList<ItemAdd> itemList;
    ArrayList<String> total=new ArrayList<String>();
    ArrayList<String> totaltaxamount=new ArrayList<String>();
    ArrayList<String> totaldiscamount=new ArrayList<String>();
    ArrayList<String> totalquntity=new ArrayList<String>();
    RelativeLayout rlpayre;
    TextView tvbal;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchaseorder);
        context=AddPurchaseorderActivity.this;
        setUpToolbarBackButton("Purchase Order");
        progressDialog=new ProgressDialog(context);
        itemList = new ArrayList<ItemAdd>();
        etorderno=findViewById(R.id.edt_orderno);
        rlpayre=findViewById(R.id.rlpayre);
        etdate=findViewById(R.id.edt_date);
        etcustomername=findViewById(R.id.edt_customername);
        etduedate=findViewById(R.id.edt_duedate);
        ettotalamount=findViewById(R.id.edt_totalamount);
        etadvanceamount=findViewById(R.id.edt_totalpaidamount);
        etrefno=findViewById(R.id.edt_paymentrefno);
        etdescription=findViewById(R.id.edt_description);
        tvbalanceamount=findViewById(R.id.tv_balancedue);
        imageView=findViewById(R.id.img_images);
        tvadditem=findViewById(R.id.tvadditem);
        recyclerViewitems=findViewById(R.id.rv_added_items);
        linearLayoutrecyclerview=findViewById(R.id.l1);
        linearLayoutamount=findViewById(R.id.linearlayoutamount);
        linearLayoutpaymenttype=findViewById(R.id.linearlayoutpaytype);
        linearLayoutdesign=findViewById(R.id.linearlayoutdesign);
        linearLayoutdesc=findViewById(R.id.linearlayoutdescription);
        tvtotaldiscamount=findViewById(R.id.tv_totaldiscamount);
        tvtotaltaxamount=findViewById(R.id.tv_totaltaxamount);
        tvtotalqty=findViewById(R.id.tv_totalqty);
        tvfinalsubtotal=findViewById(R.id.tv_subtotal);
        tvbal=findViewById(R.id.tv_bal);
        linearLayoutsave=findViewById(R.id.btnsave);

        siteid=localData.getAssignCompanyName();

        spnpaymenttype=findViewById(R.id.spn_paymenttype);
        spnstateofsupply=findViewById(R.id.spn_stateofsupply);
        tvadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddItemforPurchaseorderActivity.class)
                        .putExtra("id","0")
                        .putExtra("itemname"," ")
                        .putExtra("quantity"," ")
                        .putExtra("rate","")
                        .putExtra("subtotal","")
                        .putExtra("discountperc","")
                        .putExtra("discountamount","")
                        .putExtra("taxname","")
                        .putExtra("taxperc","")
                        .putExtra("taxamount","")
                        .putExtra("total","")

                        ;
                startActivity(intent);
                finish();


            }
        });
        paymenttype.add("Select Type");
        paymenttype.add("Cash");
        paymenttype.add("Cheque");

        ArrayAdapter<String> adapterpaytype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, paymenttype);
        adapterpaytype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnpaymenttype.setAdapter(adapterpaytype);

        spnpaymenttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedpaymenttype= parent.getItemAtPosition(position).toString();

                if (selectedpaymenttype.matches("Cheque"))
                {
                    rlpayre.setVisibility(View.VISIBLE);
                }
                else {
                    rlpayre.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stateofsupply.add("Select state");
        stateofsupply.add("Andhra Pradesh");
        stateofsupply.add("Arunachal Pradesh");
        stateofsupply.add("Assam");
        stateofsupply.add("Bihar");
        stateofsupply.add("Chhattishgarh");
        stateofsupply.add("Goa");
        stateofsupply.add("Gujarat");
        stateofsupply.add("Haryana");
        stateofsupply.add("Himachal Pradesh");
        stateofsupply.add("Jammu & Kashmir");
        stateofsupply.add("Jharkhand");
        stateofsupply.add("Karnataka");
        stateofsupply.add("Kerala");
        stateofsupply.add("Lakshadweep");
        stateofsupply.add("Madhya Pradesh");
        stateofsupply.add("Maharashtra");
        stateofsupply.add("Manipur");
        stateofsupply.add("Meghalaya");
        stateofsupply.add("Mizoram");
        stateofsupply.add("Nagaland");
        stateofsupply.add("Odisha");
        stateofsupply.add("Punjab");
        stateofsupply.add("Rajasthan");
        stateofsupply.add("Sikkim");
        stateofsupply.add("Tamil Nadu");
        stateofsupply.add("Telangana");
        stateofsupply.add("Tripura");
        stateofsupply.add("Uttar Pradesh");
        stateofsupply.add("Uttarakhand");
        stateofsupply.add("West Bengal");





        ArrayAdapter<String> adapterstate = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, stateofsupply);
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnstateofsupply.setAdapter(adapterstate);

        spnstateofsupply.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedstate= parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        etduedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPurchaseorderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                duedate= format.format(calendar.getTime());
                                etduedate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(duedate));


                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });
        strDate = mdformat.format(calendar.getTime());
        // etenddate.setText(strDate);
        etdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(strDate));

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPurchaseorderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                strDate= format.format(calendar.getTime());
                                etdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(strDate));


                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE);
                onActivityResult(PICK_FILE, 1, intent);

            }
        });

        etcustomername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CustomSelectableListActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });
        ettotalamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                {
                    linearLayoutamount.setVisibility(View.GONE);
                    linearLayoutdesign.setVisibility(View.GONE);
                    linearLayoutdesc.setVisibility(View.GONE);
                    linearLayoutpaymenttype.setVisibility(View.GONE);
                }
                else {
                    linearLayoutamount.setVisibility(View.VISIBLE);
                    linearLayoutdesign.setVisibility(View.VISIBLE);
                    linearLayoutdesc.setVisibility(View.VISIBLE);
                    linearLayoutpaymenttype.setVisibility(View.VISIBLE);
                    tvbalanceamount.setText(ettotalamount.getText().toString());

                }

            }
        });
        etadvanceamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                {
                    tvbalanceamount.setText("0.0");

                }
                else {
                    Double totalamount,paidamount,balance;
                    String t,p,b;
                    t=ettotalamount.getText().toString();
                    totalamount=Double.parseDouble(t);
                    p=etadvanceamount.getText().toString();
                    paidamount=Double.parseDouble(p);
                    balance=totalamount-paidamount;
                    b=String.valueOf(balance);
                    tvbalanceamount.setText(b);


                }

            }
        });
        Bundle extras = getIntent().getExtras();
        a=extras.getString("frag");
        if (a.matches("1"))
        {
            mapadded.clear();
            getAmenitySlotList();

        }
        else {
            getAmenitySlotList();

        }
        linearLayoutsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordernumber=etorderno.getText().toString();
                totalamount=ettotalamount.getText().toString();
                advanceamount=etadvanceamount.getText().toString();
                balanceamout=tvbalanceamount.getText().toString();
                refno=etrefno.getText().toString();
                description=etdescription.getText().toString();
                userid=localData.getSignIn().getUserId();
                if (TextUtils.isEmpty(ordernumber))
                {
                    etorderno.requestFocus();
                    etorderno.setError("Please enter Order Number");
                    return;
                }
                if (TextUtils.isEmpty(duedate))
                {
                    etduedate.requestFocus();
                    etduedate.setError("Please enter Due Date");
                    return;
                }

                if (selectedpaymenttype.matches("Select Type"))
                {
                    Toast.makeText(context,"Please Select Payment Type",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedstate.matches("Select state"))
                {
                    Toast.makeText(context,"Please Select State",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(cutomername))
                {
                    etcustomername.requestFocus();
                    etcustomername.setError("Please enter customer name");
                    return;
                }

                else {
                    itemList.clear();
                    //============
                    for(ItemAdd inew: mapadded.values())
                    {
                        itemList.add(inew);
                        //Log.d(TAG, "getAmenitySlotList:itemList "+mapadded.values());
                    }
                    for (int i=0;i<itemList.size();i++)
                    {
                        item_id.add(itemList.get(i).getItemId().toString());
                        item_name.add(itemList.get(i).getItemName());
                        item_qty.add(itemList.get(i).getItemQuantity());
                        item_price.add(itemList.get(i).getItemRate());
                        item_subtotal.add(itemList.get(i).getItemSubtotal());
                        item_discountpercent.add(itemList.get(i).getItemDiscperc());
                        item_discount.add(itemList.get(i).getItemDiscount());
                        item_taxname.add(itemList.get(i).getItemTaxName());
                        item_taxes.add(itemList.get(i).getItemTax());
                        item_taxpercent.add(itemList.get(i).getItemTaxperc());
                        item_total.add(itemList.get(i).getItemTotal());
                        item_unit.add(itemList.get(i).getItem_unit());

                        // String data=","+itemList.get(i).getItemId().toString()+":"+itemList.get(i).getItemId().toString(),



                    }
                    Addpurchaseorder();
                }

            }
        });
    }

    private void Addpurchaseorder() {
        progressDialog.show();

        progressDialog.setMessage("Please Wait!!");
        Call<AddPurchaseOrderResponse> call = RetrofitClient.getRetrofitClient().Addpurchaseorder(userid,siteid,ordernumber,strDate,cutomername,duedate,totalamount,advanceamount,balanceamout,selectedpaymenttype,refno,selectedstate,totalamount,description,imgEncodedString,item_id,item_name,item_taxname,item_taxpercent,item_discountpercent,item_unit,item_qty,item_price,item_subtotal,item_discount,item_taxes,item_total);
        call.enqueue(new Callback<AddPurchaseOrderResponse>() {
            @Override
            public void onResponse(Call<AddPurchaseOrderResponse> call, Response<AddPurchaseOrderResponse> response) {
                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();
                  /*  Intent i = new Intent(context, PaymentOutFragment.class);
                    startActivity(i);
*/

                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }

            }

            @Override
            public void onFailure(Call<AddPurchaseOrderResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(AddPurchaseorderActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAmenitySlotList() {

        itemList.clear();
        //============
        for(ItemAdd inew: mapadded.values())
        {
            itemList.add(inew);
          //  Log.d(TAG, "getAmenitySlotList:itemList "+mapadded.values());
        }

        if(itemList.size() != 0)
        {
            linearLayoutrecyclerview.setVisibility(View.VISIBLE);
            for (int i=0;i<itemList.size();i++)
            {
                total.add(itemList.get(i).getItemTotal());
                totaltaxamount.add(itemList.get(i).getItemTax());
                totaldiscamount.add(itemList.get(i).getItemDiscount());
                totalquntity.add(itemList.get(i).getItemQuantity());


            }
           // Log.d(TAG, "getAmenitySlotList: total"+total);
            additiontotal(total);
            additiontotaltotaltax(totaltaxamount);
            additiontotaltotaldisc(totaldiscamount);
            additiontotaltotalqty(totalquntity);




        }
        else
            linearLayoutrecyclerview.setVisibility(View.GONE);



        LinearLayoutManager layoutManager = new LinearLayoutManager(AddPurchaseorderActivity.this);
        recyclerViewitems.setLayoutManager(layoutManager);
        recyclerViewitems.setAdapter(new ItemAddedpurchaseorderAdapter(AddPurchaseorderActivity.this, itemList));


    }

    private void additiontotaltotalqty(ArrayList<String> totalquntity) {
        Integer sumnettotalqty=0;

        for (int i= 0;i<totalquntity.size();i++)
        {
            sumnettotalqty +=Integer.parseInt(totalquntity.get(i));

            tvtotalqty.setText(String.valueOf(sumnettotalqty));

        }

    }

    private void additiontotaltotaldisc(ArrayList<String> totaldiscamount) {
        double sumnettotaldisc=0;

        for (int i= 0;i<totaldiscamount.size();i++)
        {
            sumnettotaldisc +=Double.parseDouble(totaldiscamount.get(i));
            tvtotaldiscamount.setText(String.valueOf(String.format("%.2f", sumnettotaldisc)));

            // tvtotaldiscamount.setText(String.valueOf(sumnettotaldisc));

        }
    }

    private void additiontotaltotaltax(ArrayList<String> totaltaxamount) {
        double sumnettotaltax=0;

        for (int i= 0;i<totaltaxamount.size();i++)
        {
            sumnettotaltax +=Double.parseDouble(totaltaxamount.get(i));
            tvtotaltaxamount.setText(String.valueOf(String.format("%.2f", sumnettotaltax)));

            //tvtotaltaxamount.setText(String.valueOf(sumnettotaltax));

        }
    }

    private void additiontotal(ArrayList<String> total) {
        double sumnettotal=0;

        for (int i= 0;i<total.size();i++)
        {
            sumnettotal +=Double.parseDouble(total.get(i));
          //  Log.d(TAG, "onDataChange:sum "+totalsum);
            ettotalamount.setText(String.valueOf(String.format("%.2f", sumnettotal)));
            tvfinalsubtotal.setText(String.valueOf(String.format("%.2f", sumnettotal)));
            tvbalanceamount.setText(String.format("%.2f", sumnettotal));
/*
            etamount.setText(String.valueOf(sumnettotal));
            tvfinalsubtotal.setText(String.valueOf(sumnettotal));*/

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Image = FilePath.getPath(AddPurchaseorderActivity.this, data.getData());
            Log.d("IMAGE PATH...",Image);

            try {
                Uri imageUri = data.getData();

                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if (selectedImage != null) {
                    imageView.setImageBitmap(selectedImage);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    imgEncodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Log.e("BTAG", "IMG Encoded String :" + imgEncodedString); // display the Base64 String Image encoded text

                }
                //Log.e("BTAG","IMG Encoded String :"+imgEncodedString);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddPurchaseorderActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
        else if(resultCode==2)
        {
            customer = (CustomerResponse.Datum) data.getSerializableExtra("CustomerData");
            etcustomername.setText(customer.getCustomerName());
            cutomername=customer.getCustomerId();
            getbalance();


        }
    }
    private void getbalance() {
        Call<CustomerBalanceResponse> call = RetrofitClient.getRetrofitClient().getbalance(cutomername);
        call.enqueue(new Callback<CustomerBalanceResponse>() {
            @Override
            public void onResponse(Call<CustomerBalanceResponse> call, Response<CustomerBalanceResponse> response) {

                progressDialog.dismiss();

                if (response.body() != null ) {
                    if (response.body().getResponseCode().matches("0")) {

                        if(response.body().getData().size() != 0) {
                           // Log.d(TAG, "onResponse: "+response.body().getData());

                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                tvbal.setText("Party Balance ₹:"+response.body().getData().get(i).getCustomerBalance());


                            }

                        }
                        else
                        {
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
            public void onFailure(Call<CustomerBalanceResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

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