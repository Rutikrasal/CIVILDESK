package com.dg.info.civildesk.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.MediaRouteButton;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.fragment.HomeFragment;
import com.dg.info.civildesk.fragment.PaymentOutFragment;
import com.dg.info.civildesk.fragment.PurchaseBillFragment;
import com.dg.info.civildesk.fragment.PurchaseOrderFragment;
import com.dg.info.civildesk.fragment.PurchaseReturnFragment;
import com.dg.info.civildesk.fragment.ShowItemsFragment;
import com.dg.info.civildesk.fragment.ShowSitesFragment;
import com.dg.info.civildesk.fragment.ShowVendorFragment;
import com.dg.info.civildesk.navigation.BaseItem;
import com.dg.info.civildesk.navigation.CustomDataProvider;
import com.dg.info.civildesk.utils.GpsTracker;
import com.dg.info.civildesk.utils.NetworkUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;

import static com.dg.info.civildesk.fragment.BaseFragment.is_ckeck_in;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    SwitchMaterial drawerSwitch;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static Toolbar toolbar;
    public static TextView tvComp;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tvName, tvEmail;
    private TextView tvVersion;
    private com.mikhaellopez.circularimageview.CircularImageView ivProfile;
    private MultiLevelListView multiLevelListView;
    Fragment fragment = null;
    private FragmentTransaction fragmentTransaction;
    public static Fragment currentFragment;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    String currentaddress,TAG="TAG";
    String myDeviceModel,manufacturer;
    int sdkVersion;
    String deviceinfo;
    String batterLevel;
    private GpsTracker gpsTracker;

    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        callPermissions();
        initViews();
        confMenu();

        if (NetworkUtil.getConnectivityStatus(this)) {
          //  getDashboardData();
           // getStartdayStatus();


        } else
            Toast.makeText(HomeActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        Fragment fragment = HomeFragment.newInstance();
        replaceFragment(fragment, "DashBoard", HomeFragment.class.getSimpleName());
    }

    private void confMenu() {
        multiLevelListView = findViewById(R.id.multi_nav);
        // custom ListAdapter
        ListAdapter listAdapter = new ListAdapter();
        multiLevelListView.setAdapter(listAdapter);
        multiLevelListView.setOnItemClickListener(mOnItemClickListener);

        listAdapter.setDataItems(CustomDataProvider.getInitialItems());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        return false;
    }
    private void callPermissions() {
        int phonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermission = new ArrayList<>();

        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (phonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
        }
    }
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvComp=findViewById(R.id.tv_comp);
        drawer = findViewById(R.id.drawer_layout);

        toolbar.setTitle("DashBoard");
        // set up toolbar
        setSupportActionBar(toolbar);

        //Setup drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerSwitch = (SwitchMaterial) findViewById(R.id.drawer_switch);
        //Toast.makeText(HomeActivity.this,""+isWithin100m,Toast.LENGTH_SHORT).show();
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    getCheckin();


                } else {

                   // getActiveCheckoutData();
                }
            }
        });

        View headerView = navigationView.getHeaderView(0);
        ivProfile = headerView.findViewById(R.id.iv_profile);
        tvName = headerView.findViewById(R.id.tv_username);
        tvEmail = headerView.findViewById(R.id.tv_email);
        tvVersion = headerView.findViewById(R.id.tv_version);
       // tvVersion.setText("Version : " + BuildConfig.VERSION_NAME);
      //  Picasso.with(this).load(localData.getSignIn().getImage()).placeholder(R.drawable.placeholder).into(ivProfile);


        tvName.setText(localData.getSignIn().getFirstname()+" "+localData.getSignIn().getLastname());
        tvEmail.setText(localData.getSignIn().getEmailId());



        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

            }
        });


    }

    private void getCheckin() {
        drawerSwitch.setText("End Day");

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            mapsize=0;

        }


        gpsTracker = new GpsTracker(HomeActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.d(TAG, "getCheckin:latitude "+latitude);
            Log.d(TAG, "getCheckin:longitude "+longitude);

            getAddress(HomeActivity.this, latitude, longitude);
            Log.d(TAG, "getCheckin:currentaddress "+currentaddress);
            //currentaddress=address;
        } else {
            gpsTracker.showSettingsAlert();
        }

        myDeviceModel = android.os.Build.MODEL;
        manufacturer = Build.MANUFACTURER;
        sdkVersion = Build.VERSION.SDK_INT;
        deviceinfo=manufacturer+","+myDeviceModel+","+sdkVersion;
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batterLevel= level + "%";
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);

        if (currentaddress != null) {
            String location = latitude + "," + longitude;

            Toast.makeText(HomeActivity.this,currentaddress,Toast.LENGTH_SHORT).show();



            if (is_ckeck_in == false) {
                is_ckeck_in = true;
                drawerSwitch.setChecked(true);

                // tvStartDay.setEnabled(false);
                //tvEndDay.setEnabled(true);

            }
        }



        }

    public String getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

                // if you want only city or pin code use following code //
                String Location = address.getAddressLine(0);
                String zip = address.getPostalCode();
                String CITY = address.getSubAdminArea();

               /* Log.d("BTAG","ADDRESS LOCALITY..."+Location);
                Log.d("BTAG","ADDRESS CITY..."+CITY);
                Log.d("BTAG","ADDRESS zip..."+zip);
                Log.d("BTAG","ADDRESS full..."+fullAdd);*/

                if (Location != null) {
                    currentaddress = Location + "," + CITY + "," + zip;
                } else
                    currentaddress = CITY + "," + zip;

            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fullAdd;
    }

    private class ListAdapter extends MultiLevelListAdapter {
        @Override
        public List<?> getSubObjects(Object object) {
            // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
            return CustomDataProvider.getSubItems((BaseItem) object);
        }

        @Override
        public boolean isExpandable(Object object) {
            return CustomDataProvider.isExpandable((BaseItem) object);
        }

        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.data_item, null);
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = convertView.findViewById(R.id.dataItemArrow);
                viewHolder.icon = convertView.findViewById(R.id.di_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameView.setText(((BaseItem) object).getName());
            //viewHolder.infoView.setText(getItemInfoDsc(itemInfo));

            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.drawable.bottomarrow : R.drawable.rightarrow);
            } else {
                viewHolder.arrowView.setVisibility(View.GONE);
            }
            viewHolder.icon.setImageResource(((BaseItem) object).getIcon());
            return convertView;
        }

        private class ViewHolder {
            TextView nameView;
            ImageView arrowView;
            ImageView icon;

        }
    }
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        private void showItemDescription(Object object, ItemInfo itemInfo) {

            if (((BaseItem) object).getName().contains("Logout")) {
                displaySelectedScreen("LOGOUT");
            }

            if (((BaseItem) object).getName().contains("Purchase Bills")) {
                displaySelectedScreen("PURCHASE BILLS");
            }
            if (((BaseItem) object).getName().contains("Dashboard")) {
                displaySelectedScreen("DASHBOARD");
            }
            if (((BaseItem) object).getName().contains("Payment Out")) {
                displaySelectedScreen("PAYMENT OUT");
            }
            if (((BaseItem) object).getName().contains("Sites")) {
                displaySelectedScreen("SITES");
            }
            if (((BaseItem) object).getName().contains("Items")) {
                displaySelectedScreen("ITEMS");
            }
            if (((BaseItem) object).getName().contains("Vendors")) {
                displaySelectedScreen("VENDORS");
            }
            if (((BaseItem) object).getName().contains("Purchase Return")) {
                displaySelectedScreen("PURCHASE RETURN");
            }
            if (((BaseItem) object).getName().contains("Purchase Order")) {
                displaySelectedScreen("PURCHASE ORDER");
            }
        }

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }
    };
    private void displaySelectedScreen(String itemName) {
        //creating fragment object


        //initializing the fragment object which is selected
        switch (itemName) {
            case "SITES":
                fragment = new ShowSitesFragment();
                replaceFragment(fragment, "Sites", ShowSitesFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "VENDORS":
                fragment = new ShowVendorFragment();
                replaceFragment(fragment, "Vendors", ShowVendorFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "ITEMS":
                fragment = new ShowItemsFragment();
                replaceFragment(fragment, "Items", ShowItemsFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "PURCHASE BILLS":
                fragment = new PurchaseBillFragment();
                replaceFragment(fragment, "Purchase Bills", PurchaseBillFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;

            case "PAYMENT OUT":
                fragment = new PaymentOutFragment();
                replaceFragment(fragment, "Payment Out", PaymentOutFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;

            case "PURCHASE RETURN":
                fragment = new PurchaseReturnFragment();
                replaceFragment(fragment, "Purchase Return", PurchaseReturnFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "PURCHASE ORDER":
                fragment = new PurchaseOrderFragment();
                replaceFragment(fragment, "Purchase Order", PurchaseOrderFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "DASHBOARD":
                fragment = new HomeFragment();
                replaceFragment(fragment, "Dashboard", HomeFragment.class.getSimpleName());
                drawer.closeDrawers();
                break;
            case "LOGOUT":
                dialogExit();


            break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_layout, fragment);
            ft.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    private void dialogExit() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        localData.logout();

                       startActivity(new Intent(HomeActivity.this, LoginScreenActivity.class));
                        ActivityCompat.finishAffinity(HomeActivity.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    public void replaceFragment(Fragment fragment, String name, String tag) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment, tag);
        fragmentTransaction.commit();

        toolbar.setTitle(name);

        currentFragment = fragment;

    }
}