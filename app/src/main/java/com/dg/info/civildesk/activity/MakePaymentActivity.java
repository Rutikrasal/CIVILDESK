package com.dg.info.civildesk.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.AddPaymentOutResponse;
import com.dg.info.civildesk.models.CustomerBalanceResponse;
import com.dg.info.civildesk.models.CustomerResponse;
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

public class MakePaymentActivity extends BaseActivity {
    EditText etdate,etpaidamount,etcusomermobnum,etpayrefno,etdescription,edt_receiptno,etblancedue;
    EditText etcustomername;
    Context context;
    ProgressDialog progressDialog;
    Spinner spnpaymenttype;
    TextView tvtotalamount;
    ImageView imageView;
    String siteid,selectedpaymenttype,strDate;
    protected static final int PICK_FILE = 101;
    ArrayList<String> paymenttype=new ArrayList<>();
    RelativeLayout rlpayre;
    private String Image,imgEncodedString,cutomername,billno,customermobnum,totalamount,paidamount,referenceno,description,userid;
    private CustomerResponse.Datum customer = null;
    TextView tvbal;
    LinearLayout linearLayoutsave;
    String custname,baldue,paiamont,paytype,TAG="TAG",purchasebillid,unusedamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        context=MakePaymentActivity.this;
        setUpToolbarBackButton("Payment-Out");
        progressDialog=new ProgressDialog(context);
        etdate=findViewById(R.id.edt_date);
        etcustomername=findViewById(R.id.edt_customername);
        etpaidamount=findViewById(R.id.edt_totalamount);
        etcusomermobnum=findViewById(R.id.edt_customerno);
        spnpaymenttype=findViewById(R.id.spn_paymenttype);
        tvtotalamount=findViewById(R.id.tv_totalamount);
        etpayrefno=findViewById(R.id.edt_paymentrefno);
        etdescription=findViewById(R.id.edt_description);
        edt_receiptno=findViewById(R.id.edt_billno);
        imageView=findViewById(R.id.img_images);
        rlpayre=findViewById(R.id.rlpayre);
        tvbal=findViewById(R.id.tv_bal);
        linearLayoutsave=findViewById(R.id.btnsave);
        etblancedue=findViewById(R.id.edt_balancedueamount);
        Intent intent = getIntent();
        purchasebillid=intent.getStringExtra("purchasebillid");
        custname=intent.getStringExtra("name");
        baldue=intent.getStringExtra("balancedue");
        paytype=intent.getStringExtra("paymenttype");
        Log.d(TAG, "onCreate: paytype"+paytype);


        etcustomername.setText(custname);
        etblancedue.setText(baldue);
        etpaidamount.setText(baldue);
        siteid=localData.getAssignCompanyName();
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

                }
                else {
                    Double paidamount,balancedue,unused;
                    paidamount=Double.parseDouble(String.valueOf(s));
                    balancedue=Double.parseDouble(etblancedue.getText().toString());
                    unused=paidamount-balancedue;
                    tvtotalamount.setText(String.valueOf(unused));

                }

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
        paymenttype.add("Select Type");
        paymenttype.add("Cash");
        paymenttype.add("Cheque");


        ArrayAdapter<String> adapterpaytype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, paymenttype);
        adapterpaytype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnpaymenttype.setAdapter(adapterpaytype);
        if (paytype.matches("Cash"))
        {
            spnpaymenttype.setSelection(1);
        }
        else if (paytype.matches("Cheque"))
        {
            spnpaymenttype.setSelection(2);

        }
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(MakePaymentActivity.this,
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
        linearLayoutsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billno=edt_receiptno.getText().toString();
                customermobnum=etcusomermobnum.getText().toString();
                unusedamount=tvtotalamount.getText().toString();
                paidamount=etpaidamount.getText().toString();
                referenceno=etpayrefno.getText().toString();
                description=etdescription.getText().toString();
                userid=localData.getSignIn().getUserId();

                if (selectedpaymenttype.matches("Select Type"))
                {
                    Toast.makeText(context,"Please Select Payment Type",Toast.LENGTH_SHORT).show();
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
                    Addpaymentout();

                }





            }


        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Image = FilePath.getPath(MakePaymentActivity.this, data.getData());
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
                Toast.makeText(MakePaymentActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }

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
    private void getbalance() {
        Call<CustomerBalanceResponse> call = RetrofitClient.getRetrofitClient().getbalance(cutomername);
        call.enqueue(new Callback<CustomerBalanceResponse>() {
            @Override
            public void onResponse(Call<CustomerBalanceResponse> call, Response<CustomerBalanceResponse> response) {

                progressDialog.dismiss();

                if (response.body() != null ) {
                    if (response.body().getResponseCode().matches("0")) {

                        if(response.body().getData().size() != 0) {

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
    private void Addpaymentout() {
        progressDialog.show();

        progressDialog.setMessage("Please Wait!!");
        Call<AddPaymentOutResponse> call = RetrofitClient.getRetrofitClient().Addpaymentout(userid,purchasebillid,siteid,billno,strDate,cutomername,customermobnum,baldue,unusedamount,paidamount,"",selectedpaymenttype,referenceno,description,imgEncodedString);
        call.enqueue(new Callback<AddPaymentOutResponse>() {
            @Override
            public void onResponse(Call<AddPaymentOutResponse> call, Response<AddPaymentOutResponse> response) {
                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,HomeActivity.class));
                    finish();
/*
                    Intent i = new Intent(context, PaymentOutFragment.class);
                    startActivity(i);
*/



                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }

            }

            @Override
            public void onFailure(Call<AddPaymentOutResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(MakePaymentActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

}