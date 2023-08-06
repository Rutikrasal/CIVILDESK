package com.dg.info.civildesk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dg.info.civildesk.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaxlistAdapter extends RecyclerView.Adapter<TaxlistAdapter.ItemViewHolder> {
    ArrayList<String> taxname=new ArrayList<>();
     ArrayList<String> taxvalue=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TaxlistAdapter(ArrayList<String> taxname, ArrayList<String> taxvalue, OnItemClickListener onItemClickListener) {
        this.taxname=taxname;
        this.taxvalue=taxvalue;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.taxdesign, parent, false);
        return new TaxlistAdapter.ItemViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder,  int position) {
holder.tvtaxname.setText(taxname.get(position));
        holder.tvtaxvalue.setText(taxvalue.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(taxvalue.get(position),taxname.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return taxname.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(String tax,String taxname);

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvtaxname, tvtaxvalue;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvtaxname = itemView.findViewById(R.id.tv_taxname);
            tvtaxvalue = itemView.findViewById(R.id.tv_taxvalue);


        }
    }

}
