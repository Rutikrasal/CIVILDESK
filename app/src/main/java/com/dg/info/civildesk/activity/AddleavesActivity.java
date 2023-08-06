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
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.AddLeavesResponse;
import com.dg.info.civildesk.utils.FilePath;
import com.dg.info.civildesk.utils.NetworkUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddleavesActivity extends BaseActivity {
    EditText etstartdate,etenddate,etreason,etselectimage,etsubject;
    Button btnaddleave;
    protected static final int PICK_FILE = 101;
    private String profileImage;
    String  imgEncodedString="",startdate,enddate,subject,reason;
    private ArrayList<String> leaveype;
    Spinner spnleave;
    String selectedleavetype;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addleaves);
        setUpToolbarBackButton("Leave Request");
        context=AddleavesActivity.this;
        progressDialog=new ProgressDialog(this);
        leaveype=new ArrayList<>();
        leaveype.add("Select Leave Type");
        leaveype.add("Casual Leave");
        leaveype.add("Sick Leave");
        leaveype.add("Privileged Leave");
        spnleave=findViewById(R.id.spn_leavetype);

        etstartdate=findViewById(R.id.etstartdate);
        etenddate=findViewById(R.id.etenddate);
        etreason=findViewById(R.id.etreason);
        etselectimage=findViewById(R.id.etchoosepicture);
        btnaddleave=findViewById(R.id.btn_addleave);
        etstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddleavesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = format.format(calendar.getTime());
                                etstartdate.setText(strDate);


                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar calendar = Calendar.getInstance();  // this is default system date
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());  //set min date
                // set today's date as min date
                datePickerDialog.show();

            }
        });
        etenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddleavesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = format.format(calendar.getTime());
                                etenddate.setText(strDate);


                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar calendar = Calendar.getInstance();  // this is default system date
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());  //set min date
                // set today's date as min date
                datePickerDialog.show();


            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddleavesActivity.this, android.R.layout.simple_spinner_item, leaveype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnleave.setAdapter(adapter);
        spnleave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedleavetype = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimage();

            }
        });
        btnaddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject=etsubject.getText().toString();
                startdate=etstartdate.getText().toString();
                enddate=etenddate.getText().toString();
                reason=etreason.getText().toString();
                if (TextUtils.isEmpty(startdate))
                {
                    // etstartdate.setError("");
                    Toast.makeText(AddleavesActivity.this,"Please Select Start Date",Toast.LENGTH_SHORT).show();
                    etstartdate.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(enddate))
                {

                    // etenddate.setError("Select End Date");
                    Toast.makeText(AddleavesActivity.this,"Please Select End Date",Toast.LENGTH_SHORT).show();

                    etenddate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(reason))
                {

                    etreason.setError("Please Enter Reason");
                    etreason.requestFocus();
                    return;
                }
                if (selectedleavetype.matches("Select Leave Type"))
                {
                    Toast.makeText(AddleavesActivity.this,"please select Leave type",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (NetworkUtil.getConnectivityStatus(AddleavesActivity.this))
                        AddLeave();
                    else
                        Toast.makeText(AddleavesActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void AddLeave() {
        progressDialog.show();

        progressDialog.setMessage("Please Wait!!");
        String newID;
        if(localData.getNewUserId() != null)
        {
            Log.d("BTAG","New User id... "+localData.getNewUserId());
            newID=localData.getNewUserId();

        }
        else {
            newID = localData.getSignIn().getUserId();
        }

        Call<AddLeavesResponse> call = RetrofitClient.getRetrofitClient().Addleave(newID,startdate,enddate,selectedleavetype,reason,imgEncodedString);
        call.enqueue(new Callback<AddLeavesResponse>() {
            @Override
            public void onResponse(Call<AddLeavesResponse> call, Response<AddLeavesResponse> response) {

                progressDialog.dismiss();

                if (response.body().getResponseCode().toString().matches("0")) {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(context,response.body().getResponseMessage(),Toast.LENGTH_SHORT).show();

                    //showAlertForEnquiry();
                }
            }

            @Override
            public void onFailure(Call<AddLeavesResponse> call, Throwable t) {
                Log.d("BTAG", t.getMessage());
                Toast.makeText(AddleavesActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE);
        onActivityResult(PICK_FILE, 1, intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profileImage = FilePath.getPath(AddleavesActivity.this, data.getData());
            Log.d("IMAGE PATH...",profileImage);

            try {
                Uri imageUri = data.getData();

                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if (selectedImage != null) {
                    // ivOutletImage.setImageBitmap(selectedImage);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    imgEncodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Log.e("BTAG", "IMG Encoded String :" + imgEncodedString); // display the Base64 String Image encoded text
                }
                //Log.e("BTAG","IMG Encoded String :"+imgEncodedString);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddleavesActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
    }

}