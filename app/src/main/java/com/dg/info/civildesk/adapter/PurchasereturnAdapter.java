package com.dg.info.civildesk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.CustomerDetailsResponse;
import com.dg.info.civildesk.models.ShowPurchaseOrderResponse;
import com.dg.info.civildesk.models.ShowpurchaseReturn;

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

public class PurchasereturnAdapter extends RecyclerView.Adapter<PurchasereturnAdapter.ItemViewHolder>
{
    Context context;
    List<ShowpurchaseReturn.Datum> data;
    String TAG="TAG",id,customerid;


    public PurchasereturnAdapter(Context context, List<ShowpurchaseReturn.Datum> data) {
        this.context=context;
        this.data=data;
        Log.d(TAG, "PurchaseBillAdapter: ");
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.purchaseretuendesign, parent, false);
        return new ItemViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.tvCustName.setText(data.get(position).getPurchasereturncustomerName());
        customerid=data.get(position).getPurchasereturncustomerName();
        //holder.tvPurchaseId.setText("Purchase #"+data.get(position).getPurchasebillId());
        holder.tvdate.setText(convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(data.get(position).getPurchasereturnDate()));
        holder.tvtotalamount.setText(data.get(position).getPurchasereturnroundOff());
        holder.tvbalanceamount.setText(data.get(position).getPurchasereturnbalanceAmount());
        holder.tv_purchase.setText("DN-"+data.get(position).getPurchasereturnbillNo());


    }
    @Override
    public int getItemCount() {
       return data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCustName,tvdate,tvtotalamount,tvbalanceamount,tv_purchase;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvCustName=itemView.findViewById(R.id.tv_custname);
            tvdate=itemView.findViewById(R.id.tv_date);
            tvtotalamount=itemView.findViewById(R.id.tv_totalamount);
            tvbalanceamount=itemView.findViewById(R.id.tv_balanceamount);
            tv_purchase=itemView.findViewById(R.id.tv_purchase);




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
