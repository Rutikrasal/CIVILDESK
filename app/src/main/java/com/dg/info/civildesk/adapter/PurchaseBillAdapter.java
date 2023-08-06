package com.dg.info.civildesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.MakePaymentActivity;
import com.dg.info.civildesk.activity.PurchaseBillDetailActivity;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.CustomerDetailsResponse;
import com.dg.info.civildesk.models.PdfResponse;
import com.dg.info.civildesk.models.ShowPurchasebillResponse;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseBillAdapter extends RecyclerView.Adapter<PurchaseBillAdapter.ItemViewHolder>
{
    Context context;
    List<ShowPurchasebillResponse.Datum> data;
    String TAG="TAG",id;
    String customerid;


    public PurchaseBillAdapter(Context context, List<ShowPurchasebillResponse.Datum> data) {
        this.context=context;
        this.data=data;
        Log.d(TAG, "PurchaseBillAdapter: ");
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.purchaselist_design, parent, false);
        return new ItemViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        customerid=data.get(position).getPurchasebillcustomerName();

        holder.tvCustName.setText(data.get(position).getPurchasebillcustomerName());

        holder.tvPurchaseId.setText("Purchase #"+data.get(position).getPurchasebillId());
        holder.tvdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(data.get(position).getPurchasebillDate()));
        holder.tvtotalamount.setText(data.get(position).getRoundOff());
        holder.tvbalanceamount.setText(data.get(position).getBalanceAmount());
        Double total,balance,paidamount;
        total=Double.parseDouble(data.get(position).getRoundOff());
        balance=Double.parseDouble(data.get(position).getBalanceAmount());
        paidamount=Double.parseDouble(data.get(position).getPaidAmount());
        if (total==paidamount)
        {
            holder.tvpaystatus.setText("PAID");
            holder.tvpaystatus.setBackgroundResource(R.color.green);

        }
        else if (balance==total)
        {
            holder.tvpaystatus.setText("UNPAID");
            holder.tvpaystatus.setBackgroundResource(R.color.colorYelow);

        }
        else if (paidamount<total)
        {
            holder.tvpaystatus.setText("PARTIAL");
            holder.tvpaystatus.setBackgroundResource(R.color.blue);

        }






holder.imageViewoption.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
holder.imageViewoption.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        optionsmenu();
        PopupMenu popupMenu = new PopupMenu(context, holder.imageViewoption);
        popupMenu.getMenuInflater().inflate(R.menu.menu_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
               // Toast.makeText(context, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (menuItem.getTitle().toString().matches("Open PDF"))
                {
                    id=data.get(position).getPurchasebillId();
                    //progressDialog.show();
                    // progressDialog.setMessage("Please Wait!!..");
                    //userid = localData.getSignIn().getUserId();
                    //Log.d(TAG, "getpurchasebilldata: userid"+userid);
                    Call<PdfResponse> call = RetrofitClient.getRetrofitClient().getpdf(id);
                    call.enqueue(new Callback<PdfResponse>() {
                        @Override
                        public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {

                            //progressDialog.dismiss();

                            if (response.body() != null ) {
                                if (response.body().getResponseCode().matches("0")) {
                                    Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                                    intentUrl.setDataAndType(Uri.parse(response.body().getData().getPdf()), "application/pdf");
                                    intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intentUrl);




                                } else {
                                    Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {


                                Toast.makeText(context, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PdfResponse> call, Throwable t) {
                            //progressDialog.dismiss();
                            Log.d("BTAG", t.getMessage());
                            Toast.makeText(context, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                if (menuItem.getTitle().toString().matches("Edit"))
                {
                    id=data.get(position).getPurchasebillId();
                    context.startActivity(new Intent(context, PurchaseBillDetailActivity.class)
                    .putExtra("id",id)
                    );



                }
                if (menuItem.getTitle().toString().matches("Make Payment"))
                {
                    id=data.get(position).getPurchasebillId();
                    context.startActivity(new Intent(context, MakePaymentActivity.class)
                            .putExtra("purchasebillid",data.get(position).getPurchasebillId())
                            .putExtra("name",data.get(position).getPurchasebillcustomerName())
                            //.putExtra("phonenumber",data.get(position).mob)
                            .putExtra("balancedue",data.get(position).getBalanceAmount())
                            .putExtra("paymenttype",data.get(position).getPaymentType())

                    );



                }

                    return true;
            }
        });
        popupMenu.show();

    }
});
holder.imageViewshare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        id=data.get(position).getPurchasebillId();
        //progressDialog.show();
       // progressDialog.setMessage("Please Wait!!..");
        //userid = localData.getSignIn().getUserId();
        //Log.d(TAG, "getpurchasebilldata: userid"+userid);
        Call<PdfResponse> call = RetrofitClient.getRetrofitClient().getpdf(id);
        call.enqueue(new Callback<PdfResponse>() {
            @Override
            public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {

                //progressDialog.dismiss();

                if (response.body() != null ) {
                    if (response.body().getResponseCode().matches("0")) {
                        Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                        intentUrl.setDataAndType(Uri.parse(response.body().getData().getPdf()), "application/pdf");
                        intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intentUrl);




                    } else {
                        Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {


                    Toast.makeText(context, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PdfResponse> call, Throwable t) {
                //progressDialog.dismiss();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(context, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
            }
        });



    }
});
    }



    private void optionsmenu() {

    }

    @Override
    public int getItemCount() {
       return data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewoption,imageViewshare;
        public TextView tvCustName,tvPurchaseId,tvdate,tvpaystatus,tvtotalamount,tvbalanceamount;


        public ItemViewHolder(View itemView) {
            super(itemView);
            imageViewoption=itemView.findViewById(R.id.ivoption);
            imageViewshare=itemView.findViewById(R.id.ivshare);
            tvCustName=itemView.findViewById(R.id.tv_custname);
            tvPurchaseId=itemView.findViewById(R.id.tv_purchase);
            tvdate=itemView.findViewById(R.id.tv_date);
            tvpaystatus=itemView.findViewById(R.id.tv_status);
            tvtotalamount=itemView.findViewById(R.id.tv_totalamount);
            tvbalanceamount=itemView.findViewById(R.id.tv_balanceamount);



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
}
