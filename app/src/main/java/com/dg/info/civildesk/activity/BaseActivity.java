package com.dg.info.civildesk.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.dg.info.civildesk.R;
import com.dg.info.civildesk.models.ItemAdd;
import com.dg.info.civildesk.utils.LocalData;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.regex.Pattern;

public class BaseActivity extends AppCompatActivity {

    private Timer timer;
    protected static LocalData localData;


    public static Double TotalDistance=0.0;
    public static double discount=0.0;
    public static double Total=0.0;
    public static int mapsize=0;

    public static HashMap<String, Integer> mapBeatAdded = new HashMap<>();
    public static LinkedHashMap<Integer, ItemAdd> mapadded = new LinkedHashMap<>();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localData = LocalData.getInstance(this);
        /*list.put("first",first);
        list.put("second",second);
        list.put("third",third);
        list.put("fourth",fourth);*/
    }

    public static void addBeat(String key, int value) {
        mapBeatAdded.put(key, value);
    }

    public static int getBeat(String key)
    {
        return mapBeatAdded.get(key);
    }


    public static void addItemList(int key, ItemAdd value) {
        mapadded.put(key, value);
    }





    protected void setUpToolbarTitle(String name) {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
        }
        toolbar.setTitle(name);

    }
    protected void setUpToolbarBackButton(String name) {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(name);


    }

    protected void setUpToolbarBackColor(String name) {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.lightGray));


    }
    protected void setUpToolbarSubtitle(String name,String subname) {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(name);
        toolbar.setSubtitle(subname);



    }

    protected Date convertStringToDate(String strDate) {
        // SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = format.parse(strDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected String convertDate_dd_MM_yyyy_To_yyyy_dd_MM(String strDate)
    {
        DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        // DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertDate_dd_MMM_yyyy_To_yyyy_dd_MM(String strDate) {
        // DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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

    public String convertServerDate_dd_MMM_yyyy(String strDate) {
        if(strDate != null) {
            // DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+SS:SS");
            //  DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy , hh:mm");
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy , hh:mm"); //If you need time just put specific format for time like 'HH:mm:ss'


            try {
                Date date = originalFormat.parse(strDate);
                return targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }

    protected boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(BaseActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current  address", strReturnedAddress.toString());
            } else {
                Log.w("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current  address", "Canont get Address!");
        }
        return strAdd;
    }




}
