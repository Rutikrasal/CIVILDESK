package com.dg.info.civildesk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.models.ItemResponse;
import com.dg.info.civildesk.models.PartyResponse;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    //private List<Contact> contactList;
   // private List<Contact> contactListFiltered;
    private ArrayList<PartyResponse.Datum> contactList;
    private ArrayList<PartyResponse.Datum> contactListFiltered;
    private ContactsAdapterListener listener;
    String citys,stateid;
    ArrayList<String> state=new ArrayList<>();
    ArrayList<String> city=new ArrayList<>();

    String TAG="TAG",number;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvName,tvType,tvCity,tvEmail,tvContact;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) itemView.findViewById(R.id.tv_cm_name);
            tvType = (TextView) itemView.findViewById(R.id.tv_group_type);
            tvContact = (TextView) itemView.findViewById(R.id.tv_contact);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);


            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }


    public SiteAdapter(Context context, ArrayList<PartyResponse.Datum> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customer_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PartyResponse.Datum contact = contactListFiltered.get(position);

        holder.tvName.setText(contact.getPartyName());
        holder.tvType.setText(contact.getGstNo());
        holder.tvContact.setText(contact.getPhoneNo());

            holder.tvCity.setText(contact.getPartyAddress()+","+contact.getState());

         // holder.tvCity.setText(contact.getCityNew());





        //  holder.tvEmail.setText(contact.getEmail());
       // holder.tvCity.setText(contact.getCity());
      //  Log.d(TAG, "onBindViewHolder: customersid"+contact.getAccountID());


        }




    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    ArrayList<PartyResponse.Datum> filteredList = new ArrayList<>();
                    for (PartyResponse.Datum row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPartyName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<PartyResponse.Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(CustomerResponse.Datum contact);
    }
}
