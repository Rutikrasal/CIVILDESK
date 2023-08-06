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
import android.widget.AutoCompleteTextView;
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
import com.dg.info.civildesk.adapter.PurchaseBillAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.fragment.PurchaseBillFragment;
import com.dg.info.civildesk.models.AddPurchasebillDataRequest;
import com.dg.info.civildesk.models.AddPurchasebillResponse;
import com.dg.info.civildesk.models.CustomerBalanceResponse;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.models.ItemAdd;
import com.dg.info.civildesk.models.ItemResponse;
import com.dg.info.civildesk.models.PartyResponse;
import com.dg.info.civildesk.models.ShowPurchasebillResponse;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPurchaseBillActivity extends BaseActivity {
    private CustomerResponse.Datum customer = null;

    ArrayList<String> item_id=new ArrayList<>();
TextView tvtotaldiscamount,tvtotaltaxamount,tvtotalqty,tvfinalsubtotal;
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
    EditText etdate,etamount,etpaidamount,etcusomermobnum,etpayrefno,etdescription,edt_billno;
    TextView tvadditem,tvbalancamount;
    String strDate,amount,TAG="TAG",selectedpaymenttype,selectedstate;
    EditText etcustomername;
    Context context;
    Spinner spnpaymenttype,spnstateofsupply;
    ArrayList<String> paymenttype=new ArrayList<>();
    ArrayList<String> stateofsupply=new ArrayList<>();
    LinearLayout linearLayoutamount,linearLayoutpaymenttype,linearLayoutdesign,linearLayoutdesc,linearLayoutrecyclerview;
    private ArrayList<ItemAdd> itemList;
    RecyclerView recyclerViewitems;
    String a;
    ArrayList<String> total=new ArrayList<String>();
    ArrayList<String> totaltaxamount=new ArrayList<String>();
    ArrayList<String> totaldiscamount=new ArrayList<String>();
    ArrayList<String> totalquntity=new ArrayList<String>();

    Double totalsum;
    ArrayList<String> nettotal=new ArrayList<>();
    RelativeLayout rlpayre;
    LinearLayout linearLayoutsave;
    String cutomername,customermobnum,billno,totalamount,paidamount,balanceamount,description,encodeimage,referenceno,state;

    protected static final int PICK_FILE = 101;
    private ImageView imageView;
    private String Image,imgEncodedString;
    ArrayList<String> customerlist=new ArrayList<String>();
    String userid,siteid;
    ProgressDialog progressDialog;
    TextView tvbal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase_bill);
        context=AddPurchaseBillActivity.this;
        setUpToolbarBackButton("Add Purchase Bill");
        progressDialog=new ProgressDialog(context);

        //getcustomerlist();

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, customerlist);
*/
        itemList = new ArrayList<ItemAdd>();
recyclerViewitems=findViewById(R.id.rv_added_items);
linearLayoutrecyclerview=findViewById(R.id.l1);
        etdate=findViewById(R.id.edt_date);
        etcustomername=findViewById(R.id.edt_customername);
        etamount=findViewById(R.id.edt_totalamount);
        tvadditem=findViewById(R.id.tvadditem);
        spnpaymenttype=findViewById(R.id.spn_paymenttype);
        spnstateofsupply=findViewById(R.id.spn_stateofsupply);
        linearLayoutamount=findViewById(R.id.linearlayoutamount);
                linearLayoutpaymenttype=findViewById(R.id.linearlayoutpaytype);
                linearLayoutdesign=findViewById(R.id.linearlayoutdesign);
                linearLayoutdesc=findViewById(R.id.linearlayoutdescription);
                etpaidamount=findViewById(R.id.edt_totalpaidamount);
        tvbalancamount=findViewById(R.id.tv_balancedue);
        rlpayre=findViewById(R.id.rlpayre);
        linearLayoutsave=findViewById(R.id.btnsave);
        etcusomermobnum=findViewById(R.id.edt_customerno);
        etpayrefno=findViewById(R.id.edt_paymentrefno);
        etdescription=findViewById(R.id.edt_description);
        imageView=findViewById(R.id.img_images);
        edt_billno=findViewById(R.id.edt_billno);

        tvtotaldiscamount=findViewById(R.id.tv_totaldiscamount);
        tvtotaltaxamount=findViewById(R.id.tv_totaltaxamount);
        tvtotalqty=findViewById(R.id.tv_totalqty);
        tvfinalsubtotal=findViewById(R.id.tv_subtotal);
        tvbal=findViewById(R.id.tv_bal);

        siteid=localData.getAssignCompanyName();


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
        tvadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(context, AddItemtopurchaseActivity.class)
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
        etcustomername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CustomSelectableListActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        strDate = mdformat.format(calendar.getTime());
        // etenddate.setText(strDate);
        etdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(strDate));

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPurchaseBillActivity.this,
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
etamount.addTextChangedListener(new TextWatcher() {
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
            tvbalancamount.setText(etamount.getText().toString());

        }

    }
});

        etpaidamount.addTextChangedListener(new TextWatcher() {
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
                    tvbalancamount.setText("0.0");

                }
                else {
                    Double totalamount,paidamount,balance;
                    String t,p,b;
                    t=etamount.getText().toString();
                    totalamount=Double.parseDouble(t);
                    p=etpaidamount.getText().toString();
                    paidamount=Double.parseDouble(p);
                    balance=totalamount-paidamount;
                    b=String.valueOf(balance);
                    tvbalancamount.setText(b);


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
                billno=edt_billno.getText().toString();
                //cutomername=etcustomername.getText().toString();
                customermobnum=etcusomermobnum.getText().toString();
                totalamount=etamount.getText().toString();
                paidamount=etpaidamount.getText().toString();
                balanceamount=tvbalancamount.getText().toString();
                referenceno=etpayrefno.getText().toString();
                description=etdescription.getText().toString();
                userid=localData.getSignIn().getUserId();

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
                if (TextUtils.isEmpty(customermobnum))
                {
                    etcusomermobnum.requestFocus();
                    etcusomermobnum.setError("Please enter Mobile Number");
                    return;
                }





                else {
                    itemList.clear();
                    //============
                    for(ItemAdd inew: mapadded.values())
                    {
                        itemList.add(inew);
                        Log.d(TAG, "getAmenitySlotList:itemList "+mapadded.values());
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
                    Addpurchasebill();
                }

            }
        });


    }

    private void Addpurchasebill() {
        progressDialog.show();

        progressDialog.setMessage("Please Wait!!");

        Log.d(TAG, "Addpurchasebill: userid="+userid);
        Log.d(TAG, "Addpurchasebill: siteid="+siteid);
        Log.d(TAG, "Addpurchasebill: billno="+billno);
        Log.d(TAG, "Addpurchasebill: strDate="+strDate);
        Log.d(TAG, "Addpurchasebill: cutomername="+cutomername);
        Log.d(TAG, "Addpurchasebill: customermobnum="+customermobnum);
        Log.d(TAG, "Addpurchasebill: totalamount="+totalamount);
        Log.d(TAG, "Addpurchasebill: paidamount="+paidamount);
        Log.d(TAG, "Addpurchasebill: balanceamount="+balanceamount);
        Log.d(TAG, "Addpurchasebill: selectedpaymenttype="+selectedpaymenttype);
        Log.d(TAG, "Addpurchasebill: referenceno="+referenceno);
        Log.d(TAG, "Addpurchasebill: selectedstate="+selectedstate);
        Log.d(TAG, "Addpurchasebill: totalamount="+totalamount);
        Log.d(TAG, "Addpurchasebill: description="+description);
        Log.d(TAG, "Addpurchasebill: imgEncodedString="+imgEncodedString);
        Log.d(TAG, "Addpurchasebill: item_id="+item_id);
        Log.d(TAG, "Addpurchasebill: item_name="+item_name);
        Log.d(TAG, "Addpurchasebill: item_taxname="+item_taxname);
        Log.d(TAG, "Addpurchasebill: item_taxpercent="+item_taxpercent);
        Log.d(TAG, "Addpurchasebill: item_discountpercent="+item_discountpercent);
        Log.d(TAG, "Addpurchasebill: item_unit="+item_unit);
        Log.d(TAG, "Addpurchasebill: item_qty="+item_qty);
        Log.d(TAG, "Addpurchasebill: item_price="+item_price);
        Log.d(TAG, "Addpurchasebill: item_subtotal="+item_subtotal);
        Log.d(TAG, "Addpurchasebill: item_discount="+item_discount);
        Log.d(TAG, "Addpurchasebill: item_taxes="+item_taxes);
        Log.d(TAG, "Addpurchasebill: item_total="+item_total);
        //AddPurchasebillDataRequest addPurchasebillDataRequest=new AddPurchasebillDataRequest(userid,siteid,billno,strDate,cutomername,customermobnum,totalamount,paidamount,balanceamount,selectedpaymenttype,referenceno,selectedstate,totalamount,description,imgEncodedString,itemList);
        Call<AddPurchasebillResponse> call = RetrofitClient.getRetrofitClient().addpurchasebill(userid,siteid,billno,strDate,cutomername,customermobnum,totalamount,paidamount,balanceamount,selectedpaymenttype,referenceno,selectedstate,totalamount,description,imgEncodedString,item_id,item_name,item_taxname,item_taxpercent,item_discountpercent,item_unit,item_qty,item_price,item_subtotal,item_discount,item_taxes,item_total);
        call.enqueue(new Callback<AddPurchasebillResponse>() {
            @Override
            public void onResponse(Call<AddPurchasebillResponse> call, Response<AddPurchasebillResponse> response) {
        progressDialog.dismiss();

                    if (response.body().getResponseCode().toString().matches("0")) {
                        Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: responsemsg"+response.body().getResponseMessage());
                        Intent i = new Intent(context, PurchaseBillFragment.class);
                        startActivity(i);



                    } else {
                        Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                        //showAlertForEnquiry();
                    }

            }

            @Override
            public void onFailure(Call<AddPurchasebillResponse> call, Throwable t) {
                    progressDialog.dismiss();

                Log.d(TAG, "" + t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(AddPurchaseBillActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
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


    private void getAmenitySlotList() {

        itemList.clear();
        //============
        for(ItemAdd inew: mapadded.values())
        {
            itemList.add(inew);
            Log.d(TAG, "getAmenitySlotList:itemList "+mapadded.values());
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
            Log.d(TAG, "getAmenitySlotList: total"+total);
            additiontotal(total);
            additiontotaltotaltax(totaltaxamount);
            additiontotaltotaldisc(totaldiscamount);
            additiontotaltotalqty(totalquntity);




        }
        else
            linearLayoutrecyclerview.setVisibility(View.GONE);



        LinearLayoutManager layoutManager = new LinearLayoutManager(AddPurchaseBillActivity.this);
        recyclerViewitems.setLayoutManager(layoutManager);
        recyclerViewitems.setAdapter(new ItemAddedAdapter(AddPurchaseBillActivity.this, itemList));


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
            Log.d(TAG, "onDataChange:sum "+totalsum);
            etamount.setText(String.valueOf(String.format("%.2f", sumnettotal)));
            tvfinalsubtotal.setText(String.valueOf(String.format("%.2f", sumnettotal)));
            tvbalancamount.setText(String.format("%.2f", sumnettotal));
/*
            etamount.setText(String.valueOf(sumnettotal));
            tvfinalsubtotal.setText(String.valueOf(sumnettotal));*/

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Image = FilePath.getPath(AddPurchaseBillActivity.this, data.getData());
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
                Toast.makeText(AddPurchaseBillActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
       else if(resultCode==2)
        {
            customer = (CustomerResponse.Datum) data.getSerializableExtra("CustomerData");
            etcustomername.setText(customer.getCustomerName());
            etcusomermobnum.setText(customer.getCustomerMobileno());
            cutomername=customer.getCustomerId();
            Log.d(TAG, "onActivityResult: id"+cutomername);
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
                            Log.d(TAG, "onResponse: "+response.body().getData());

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
}