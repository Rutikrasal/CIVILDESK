package com.dg.info.civildesk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.activity.AddItemtopurchaseActivity;
import com.dg.info.civildesk.activity.AddPurchaseBillActivity;
import com.dg.info.civildesk.models.ItemAdd;

import java.util.ArrayList;

public class ItemAddedAdapter extends RecyclerView.Adapter<ItemAddedAdapter.ItemViewHolder>{
    private Context context;
    private ArrayList<ItemAdd> rowItems;
    double qty,rate,subtotal;

    public ItemAddedAdapter(AddPurchaseBillActivity context, ArrayList<ItemAdd> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_added_items, parent, false);
        return new ItemAddedAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final ItemAdd item = rowItems.get(position);
        holder.tvid.setText(item.getItemId().toString());
        holder.tvitemname.setText(item.getItemName().toString());
        holder.tvtotal.setText(item.getItemTotal().toString());
        holder.tvquantity.setText(item.getItemQuantity().toString());
        holder.tvrate.setText(item.getItemRate().toString());
        holder.tvdiscountperc.setText(item.getItemDiscperc().toString());
        holder.tvdiscountamount.setText(item.getItemDiscount().toString());
        holder.tvgstname.setText(item.getItemTaxName().toString()+":");
        holder.tvgstperc.setText(item.getItemTaxperc().toString());
        holder.tvgstamount.setText(item.getItemTax().toString());
/*        holder.tvtotaldiscamount.setText(item.getItemDiscount().toString());
        holder.tvtotaltaxamount.setText(item.getItemTax().toString());
        holder.tvtotalqty.setText(item.getItemQuantity().toString());
        holder.tvfinalsubtotal.setText(item.getItemTotal().toString());*/
        qty=Double.parseDouble(item.getItemQuantity());
        rate=Double.parseDouble(item.getItemRate());
        subtotal=qty*rate;
        holder.tvitemsubtotal.setText(String.valueOf(subtotal));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddItemtopurchaseActivity.class)
                .putExtra("id",item.getItemId().toString())
                        .putExtra("itemname",item.getItemName().toString())
                        .putExtra("quantity",item.getItemQuantity())
                        .putExtra("rate",item.getItemRate())
                                .putExtra("subtotal",item.getItemSubtotal())
                                .putExtra("discountperc",item.getItemDiscperc())
                                .putExtra("discountamount",item.getItemDiscount())
                                .putExtra("taxname",item.getItemTaxName())
                                .putExtra("taxperc",item.getItemTaxperc())
                                .putExtra("taxamount",item.getItemTax())
                                .putExtra("total",item.getItemTotal())


                );
                ((Activity)context).finish();


            }
        });



    }

    public interface StatusUpdateListener {
        void onStatusUpdate(int index, ItemAdd item);
    }


    @Override
    public int getItemCount() {
         return rowItems.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvid,tvitemname,tvtotal,tvquantity,tvrate,tvitemsubtotal,tvdiscountperc,tvdiscountamount,tvgstname,tvgstperc,tvgstamount;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvid=itemView.findViewById(R.id.tv_itemid);
            tvitemname=itemView.findViewById(R.id.tv_itemname);
            tvtotal=itemView.findViewById(R.id.tv_finalamount);
            tvquantity=itemView.findViewById(R.id.tv_quantity);
            tvrate=itemView.findViewById(R.id.tv_rate);
            tvitemsubtotal=itemView.findViewById(R.id.tv_subtotalamount);
            tvdiscountperc=itemView.findViewById(R.id.tv_discountperc);
            tvdiscountamount=itemView.findViewById(R.id.tv_discountamount);
            tvgstname=itemView.findViewById(R.id.tv_gstname);
            tvgstperc=itemView.findViewById(R.id.tv_gstperc);
            tvgstamount=itemView.findViewById(R.id.tv_taxamount);



        }
    }
}
