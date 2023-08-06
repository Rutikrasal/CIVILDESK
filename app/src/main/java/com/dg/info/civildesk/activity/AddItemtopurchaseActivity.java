package com.dg.info.civildesk.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.adapter.TaxlistAdapter;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.ItemAdd;
import com.dg.info.civildesk.models.ItemResponse;
import com.dg.info.civildesk.models.LoginResponse;
import com.dg.info.civildesk.models.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemtopurchaseActivity extends BaseActivity implements TaxlistAdapter.OnItemClickListener{
    EditText etquantity,etrate,etdiscount,ettax,etgstpercentage,etdiscountamount,edt_totalamount,etgstamountamount;
    TextView tvtotal,tvsubtotal;
    String stritemname,strquantity,strrate,strdiscount,strtotal,strsubtotal,strtax,strtaxnaming,TAG="TAG",strtaxperc,strdiscountperc;
    Button btnsave;
    LinearLayout linearLayouttotals,linearLayoutsave,linearLayoutsaveandnew,linearLayoutcheckout,linearLayoutupdate,linearLayoutdelete,linearLayoutupdatebtn;
     Dialog dialog;
     double rate,quantity,subtotal,discount,discountrupees;
     int id;
    private ArrayList<ItemAdd> itemList;
    String itemid,itemname,itemquantity,itemrate,itemsubttal,itemdiscountperc,itemdiscountamount,itemtaxname,itemtaxperc,itemtaxamount,itemtotal;
    AutoCompleteTextView  etitemname;
    ArrayList<String> itemslist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_itemtopurchase);
        setUpToolbarBackButton("Add Items to Purchase");
        Bundle extras = getIntent().getExtras();
        itemid=extras.getString("id");
        itemname=extras.getString("itemname");
        itemquantity=extras.getString("quantity");
        itemrate=extras.getString("rate");
        itemsubttal =extras.getString("subtotal");
                itemdiscountperc=extras.getString("discountperc");
                itemdiscountamount=extras.getString("discountamount");
                        itemtaxname=extras.getString("taxname");
        itemtaxperc=extras.getString("taxperc");
                itemtaxamount=extras.getString("taxamount");
        itemtotal=extras.getString("total");

        getitem();






        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, itemslist);


        etitemname=findViewById(R.id.edt_itemname);
        etquantity=findViewById(R.id.edt_quantity);
        etrate=findViewById(R.id.edt_rate);
        tvsubtotal=findViewById(R.id.tv_subtotal);
        linearLayouttotals=findViewById(R.id.linearlayouttotals);
        etgstpercentage=findViewById(R.id.etgstpercent);
        etdiscountamount=findViewById(R.id.etdiscountamount);
        etdiscount=findViewById(R.id.etdiscountpercent);
        edt_totalamount=findViewById(R.id.edt_totalamount);
        etgstamountamount=findViewById(R.id.etgstamountamount);
        linearLayoutsave=findViewById(R.id.btnsave);
        linearLayoutsaveandnew=findViewById(R.id.btnsavenew);
        linearLayoutcheckout=findViewById(R.id.ll_checkout);
        linearLayoutupdate=findViewById(R.id.ll_update);
        linearLayoutdelete=findViewById(R.id.btndelete);
        linearLayoutupdatebtn=findViewById(R.id.btnupdate);
        etrate.setEnabled(false);

        etitemname.setThreshold(0);
        etitemname.setAdapter(adapter);

        itemList = new ArrayList<ItemAdd>();
        //itemList.clear();
        //============
        for(ItemAdd inew: mapadded.values())
        {
            itemList.add(inew);
            Log.d(TAG, "getAmenitySlotList:itemList "+mapadded.values());
        }
        if (!itemid.matches("0"))
        {
            linearLayouttotals.setVisibility(View.VISIBLE);
            linearLayoutupdate.setVisibility(View.VISIBLE);

            etitemname.setText(itemname);
            etquantity.setText(itemquantity);
            etrate.setText(itemrate);
            tvsubtotal.setText(itemsubttal);
            etdiscount.setText(itemdiscountperc);
            etdiscountamount.setText(itemdiscountamount);
            strtaxnaming=itemtaxname;
            etgstpercentage.setText(itemtaxname);
            etgstamountamount.setText(itemtaxamount);
            edt_totalamount.setText(itemtotal);
            strtaxperc=itemtaxperc;
        }
        else {
            linearLayoutupdate.setVisibility(View.GONE);

        }




        linearLayoutdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new androidx.appcompat.app.AlertDialog.Builder(AddItemtopurchaseActivity.this)
                        .setMessage("Are you sure you want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mapadded.remove(Integer.parseInt(itemid));
                              //  statusUpdateListener.onStatusUpdate(position, item);
                                Toast.makeText(AddItemtopurchaseActivity.this, "Item Deleted...", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AddItemtopurchaseActivity.this,AddPurchaseBillActivity.class).putExtra("frag","2"));

                                finish();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();



            }
        });
        linearLayoutupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stritemname=etitemname.getText().toString();
                strquantity=etquantity.getText().toString();
                strrate=etrate.getText().toString();
                strdiscount=etdiscountamount.getText().toString();
                strsubtotal=tvsubtotal.getText().toString();
                strtotal=edt_totalamount.getText().toString();
                strtax=etgstamountamount.getText().toString();
                strdiscountperc=etdiscount.getText().toString();

                if (TextUtils.isEmpty(stritemname))
                {
                    etitemname.setError("Please Enter Item Name");
                    etitemname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(strquantity))
                {
                    etquantity.setError("Please Enter Item Quantity");
                    etquantity.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strrate))
                {
                    etrate.setError("Please Enter Item Rate");
                    etrate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strdiscount)||strdiscount.matches(" "))
                {
                    strdiscount="0.0";
                }
                if (TextUtils.isEmpty(strtax)||strtax.matches("null"))
                {
                    strtax="0.0";
                }
                if (TextUtils.isEmpty(strtaxperc)||strtaxperc.matches("null"))
                {
                    strtaxperc="0.0";
                }
                if (TextUtils.isEmpty(strdiscountperc)||strdiscountperc.matches("null"))
                {
                    strdiscountperc="0.0";
                }
                if (TextUtils.isEmpty(strtaxnaming)||strtaxnaming.matches("null"))
                {
                    strtaxnaming="None";
                }
                if(itemList.size() != 0)
                {
                    for (int i=0;i<itemList.size();i++)
                    {
                        id=Integer.parseInt(itemid);

                    }
                }
                else
                {
                    id=1;

                }
                Log.d(TAG, "onClick:id "+id);
                Log.d(TAG, "onClick:stritemname "+stritemname);
                Log.d(TAG, "onClick:strquantity "+strquantity);
                Log.d(TAG, "onClick:strrate "+strrate);
                Log.d(TAG, "onClick:strsubtotal "+strsubtotal);
                Log.d(TAG, "onClick:strdiscount "+strdiscount);
                Log.d(TAG, "onClick:strtax "+strtax);
                Log.d(TAG, "onClick:strtotal "+strtotal);
                Log.d(TAG, "onClick:strtaxnaming "+strtaxnaming);
                Log.d(TAG, "onClick:strtaxperc "+strtaxperc);
                Log.d(TAG, "onClick:etdiscount.getText().toString() "+etdiscount.getText().toString());




                ItemAdd iadd = new ItemAdd(id,stritemname,strquantity,strrate,strsubtotal,strdiscount,strtax,strtotal,strtaxnaming,strtaxperc,strdiscountperc,"");

                addItemList(id, iadd);
                Log.d(TAG, "onClick: id"+id);

                Toast.makeText(AddItemtopurchaseActivity.this, "Item Updated...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddItemtopurchaseActivity.this,AddPurchaseBillActivity.class).putExtra("frag","2"));

                finish();
            }
        });

        linearLayoutsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stritemname=etitemname.getText().toString();
                strquantity=etquantity.getText().toString();
                strrate=etrate.getText().toString();
                strdiscount=etdiscountamount.getText().toString();
                strsubtotal=tvsubtotal.getText().toString();
                strtotal=edt_totalamount.getText().toString();
                strtax=etgstamountamount.getText().toString();
                strdiscountperc=etdiscount.getText().toString();

                if (TextUtils.isEmpty(stritemname))
                {
                    etitemname.setError("Please Enter Item Name");
                    etitemname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(strquantity))
                {
                    etquantity.setError("Please Enter Item Quantity");
                    etquantity.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strrate))
                {
                    etrate.setError("Please Enter Item Rate");
                    etrate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strdiscount)||strdiscount.matches(" "))
                {
                   strdiscount="0.0";
                }
                if (TextUtils.isEmpty(strtax)||strtax.matches("null"))
                {
                    strtax="0.0";
                }
                if (TextUtils.isEmpty(strtaxperc)||strtaxperc.matches("null"))
                {
                    strtaxperc="0.0";
                }
                if (TextUtils.isEmpty(strdiscountperc)||strdiscountperc.matches("null"))
                {
                    strdiscountperc="0.0";
                }
                if (TextUtils.isEmpty(strtaxnaming)||strtaxnaming.matches("null"))
                {
                    strtaxnaming="None";
                }


                if(itemList.size() != 0)
                {
                    for (int i=0;i<itemList.size();i++)
                    {
                        id=itemList.get(i).getItemId()+1;

                    }
                }
                else
                {
                    id=1;

                }

                Log.d(TAG, "onClick:id "+id);
                Log.d(TAG, "onClick:stritemname "+stritemname);
                Log.d(TAG, "onClick:strquantity "+strquantity);
                Log.d(TAG, "onClick:strrate "+strrate);
                Log.d(TAG, "onClick:strsubtotal "+strsubtotal);
                Log.d(TAG, "onClick:strdiscount "+strdiscount);
                Log.d(TAG, "onClick:strtax "+strtax);
                Log.d(TAG, "onClick:strtotal "+strtotal);
                Log.d(TAG, "onClick:strtaxnaming "+strtaxnaming);
                Log.d(TAG, "onClick:strtaxperc "+strtaxperc);
                Log.d(TAG, "onClick:strdiscountperc "+strdiscountperc);



                ItemAdd iadd = new ItemAdd(id,stritemname,strquantity,strrate,strsubtotal,strdiscount,strtax,strtotal,strtaxnaming,strtaxperc,strdiscountperc,"");

                addItemList(id, iadd);
                Log.d(TAG, "onClick: id"+id);

                Toast.makeText(AddItemtopurchaseActivity.this, "Item Added...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddItemtopurchaseActivity.this,AddPurchaseBillActivity.class).putExtra("frag","2"));

                finish();

            }
        });
        linearLayoutsaveandnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stritemname=etitemname.getText().toString();
                strquantity=etquantity.getText().toString();
                strrate=etrate.getText().toString();
                strdiscount=etdiscountamount.getText().toString();
                strsubtotal=tvsubtotal.getText().toString();
                strtotal=edt_totalamount.getText().toString();
                strtax=etgstamountamount.getText().toString();
                strdiscountperc=etdiscount.getText().toString();

                if (TextUtils.isEmpty(stritemname))
                {
                    etitemname.setError("Please Enter Item Name");
                    etitemname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(strquantity))
                {
                    etquantity.setError("Please Enter Item Quantity");
                    etquantity.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strrate))
                {
                    etrate.setError("Please Enter Item Rate");
                    etrate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(strdiscount)||strdiscount.matches(" "))
                {
                    strdiscount="0.0";
                }
                if (TextUtils.isEmpty(strtax)||strtax.matches("null"))
                {
                    strtax="0.0";
                }
                if (TextUtils.isEmpty(strtaxperc)||strtaxperc.matches("null"))
                {
                    strtaxperc="0.0";
                }
                if (TextUtils.isEmpty(strdiscountperc)||strdiscountperc.matches("null"))
                {
                    strdiscountperc="0.0";
                }
                if (TextUtils.isEmpty(strtaxnaming)||strtaxnaming.matches("null"))
                {
                    strtaxnaming="None";
                }
                if(itemList.size() != 0)
                {
                    for (int i=0;i<itemList.size();i++)
                    {
                        id=itemList.get(i).getItemId()+1;

                    }
                }
                else
                {
                    id=1;

                }
                ItemAdd iadd = new ItemAdd(id,stritemname,strquantity,strrate,strsubtotal,strdiscount,strtax,strtotal,strtaxnaming,strtaxperc,strdiscountperc,"");

                addItemList(id, iadd);
                Log.d(TAG, "onClick: id"+id);

                Toast.makeText(AddItemtopurchaseActivity.this, "Item Added...", Toast.LENGTH_LONG).show();

                Intent i = new Intent(AddItemtopurchaseActivity.this, AddItemtopurchaseActivity.class);
                Bundle bundle = new Bundle();


                bundle.putString("id","0");
                bundle.putString("itemname"," ");
                bundle.putString("quantity"," ");
                bundle.putString("rate","");
                bundle.putString("subtotal","");
                bundle.putString("discountperc","");
                bundle.putString("discountamount","");
                bundle.putString("taxname","");
                bundle.putString("taxperc","");
                bundle.putString("taxamount","");
                bundle.putString("total","");



                i.putExtras(bundle);
                startActivity(i);
                AddItemtopurchaseActivity.this.finish();

               /* finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
*/
            }
        });
        etgstpercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });
        etquantity.addTextChangedListener(new TextWatcher() {
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
                    etrate.setEnabled(false); // user navigates with wheel and selects widget

                }
                else {

                    etrate.setEnabled(true); // user navigates with wheel and selects widget

                }
                }
        });
        etrate.addTextChangedListener(new TextWatcher() {
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
                    linearLayouttotals.setVisibility(View.GONE);

                }
                else {
                    linearLayouttotals.setVisibility(View.VISIBLE);
                    strrate=etrate.getText().toString();
                    strquantity=etquantity.getText().toString();
                    if (TextUtils.isEmpty(strquantity))
                    {
                        etquantity.setError("Please Enter Quantity");
                        etquantity.requestFocus();
                        return;


                    }
                    else {
                        rate= Double.parseDouble(strrate);
                        quantity=Double.parseDouble(strquantity);
                        subtotal=rate*quantity;
                        tvsubtotal.setText(String.valueOf(subtotal));
                        edt_totalamount.setText(String.valueOf(subtotal));

                        strdiscount=etdiscount.getText().toString();
                        if (TextUtils.isEmpty(strdiscount))
                        {
                            etdiscountamount.setText(" ");
                            //edt_totalamount.setText("0.0");


                        }
                        else {
                            discount=Double.parseDouble(strdiscount);
                            discountrupees=subtotal*discount/100;
                            etdiscountamount.setText(String.valueOf(discountrupees));
                            edt_totalamount.setText(String.valueOf(subtotal-discountrupees));

                        }
                      /*  double finalamount,subfinal,taxes;
                        taxes=Double.parseDouble(itemtaxperc);
                        finalamount=subtotal-discountrupees;
                        subfinal=finalamount*taxes/100;
                        etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)));
                        edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));*/
/*

                        if (itemtaxperc.matches("0.0%"))
                        {
                            double finalamount,subfinal,taxes;
                            taxes=Double.parseDouble(itemtaxperc);
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*taxes/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));


                        }
                        if (itemtaxperc.matches("0.25%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*0.25/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));


                        }
                        if (itemtaxperc.matches("3.0%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*3.0/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

                        }
                        if (itemtaxperc.matches("5.0%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*5.0/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

                        }
                        if (itemtaxperc.matches("12.0%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*12.0/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

                        }
                        if (itemtaxperc.matches("18.0%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*18.0/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

                        }
                        if (itemtaxperc.matches("28.0%"))
                        {
                            double finalamount,subfinal;
                            finalamount=subtotal-discountrupees;
                            subfinal=finalamount*28.0/100;
                            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
                            ));
                            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

                        }

*/



                    }







                }

            }
        });
        etdiscount.addTextChangedListener(new TextWatcher() {
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
                    discountrupees=0.0;

                }
                else {
                    strdiscount=etdiscount.getText().toString();

                    discount=Double.parseDouble(strdiscount);
                    discountrupees=subtotal*discount/100;
                    etdiscountamount.setText(String.valueOf(discountrupees));
                    edt_totalamount.setText(String.valueOf(subtotal-discountrupees));


                }

            }
        });



    }

    private void getitem() {
        Log.d(TAG, "getitem: ");
        Call<ItemResponse> call = RetrofitClient.getRetrofitClient().get_item();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.body() != null ) {

                    if (response.body().getResponseCode().toString().matches("0")) {
                        Log.d(TAG, "onResponse: ");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            itemslist.add(String.valueOf(response.body().getData().get(i).getItemName()));

                        }



                    } else {
                        //showAlertForEnquiry();
                    }
                } else {

                    //Toast.makeText(LoginScreenActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.d(TAG, "" + t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(AddItemtopurchaseActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDialog() {

         dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        RecyclerView recyclerViewtax=dialog.findViewById(R.id.recyclerviewtax);
        ImageView imageViewcancel=dialog.findViewById(R.id.imageViewcancel);
        ArrayList<String> taxname=new ArrayList<>();
        ArrayList<String> taxvalue=new ArrayList<>();
        taxname.add("None");
        taxname.add("Exempted");
        taxname.add("GST@0%");
        taxname.add("IGST@0%");
        taxname.add("GST@0.25%");
        taxname.add("IGST@0.25%");
        taxname.add("GST@3%");
        taxname.add("IGST@3%");
        taxname.add("GST@5%");
        taxname.add("IGST@5%");
        taxname.add("GST@12%");
        taxname.add("IGST@12%");
        taxname.add("GST@18%");
        taxname.add("IGST@18%");
        taxname.add("GST@28%");
        taxname.add("IGST@28%");

        taxvalue.add("0.0");
        taxvalue.add("0.0");
        taxvalue.add("0.0");
        taxvalue.add("0.0");
        taxvalue.add("0.25");
        taxvalue.add("0.25");
        taxvalue.add("3.0");
        taxvalue.add("3.0");
        taxvalue.add("5.0");
        taxvalue.add("5.0");
        taxvalue.add("12.0");
        taxvalue.add("12.0");
        taxvalue.add("18.0");
        taxvalue.add("18.0");
        taxvalue.add("28.0");
        taxvalue.add("28.0");
        TaxlistAdapter adapter = new TaxlistAdapter(taxname,taxvalue,this);
        recyclerViewtax.setHasFixedSize(true);
        recyclerViewtax.setLayoutManager(new LinearLayoutManager(AddItemtopurchaseActivity.this));
        recyclerViewtax.setAdapter(adapter);

        imageViewcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onItemClick(String strtax,String strtaxname) {
        etgstpercentage.setText(strtaxname);
        strtaxperc=strtax;
        strtaxnaming=strtaxname;
        double finalamount,subfinal,taxing;
        taxing=Double.parseDouble(strtaxperc);
        finalamount=subtotal-discountrupees;
        subfinal=finalamount*taxing/100;
        etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
        ));
        edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

/*

        if (strtax.matches("0.0%"))
        {
            double finalamount,subfinal,taxing;
            taxing=Double.parseDouble(strtaxperc);
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*taxing/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));


        }
        if (strtax.matches("0.25%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*0.25/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));


        }
        if (strtax.matches("3.0%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*3.0/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

        }
        if (strtax.matches("5.0%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*5.0/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

        }
        if (strtax.matches("12.0%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*12.0/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

        }
        if (strtax.matches("18.0%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*18.0/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

        }
        if (strtax.matches("28.0%"))
        {
            double finalamount,subfinal;
            finalamount=subtotal-discountrupees;
            subfinal=finalamount*28.0/100;
            etgstamountamount.setText(String.valueOf(String.format("%.2f", subfinal)
            ));
            edt_totalamount.setText(String.valueOf(String.format("%.2f", finalamount+subfinal)));

        }
*/
        dialog.dismiss();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AddItemtopurchaseActivity.this,AddPurchaseBillActivity.class).putExtra("frag","2"));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}