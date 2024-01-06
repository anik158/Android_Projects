package com.ahsan.mycontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contactList;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Contact contact);
    }

    public ContactAdapter(List<Contact> contactList, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener) {
        this.contactList = contactList;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.homeNumberTextView.setText(contact.getPhoneHome());
        holder.officeNumberTextView.setText(contact.getPhoneHome());

        if (contact.getPhoto() != null && !contact.getPhoto().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(contact.getPhoto())
                    .placeholder(R.drawable.person_icon_contact_list)
                    .error(R.drawable.person_icon_contact_list)
                    .into(holder.photoImageView);
        } else {

            holder.photoImageView.setImageResource(R.drawable.person_icon_contact_list);
        }

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(contact));
        holder.itemView.setOnLongClickListener(v -> {
            itemLongClickListener.onItemLongClick(contact);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView homeNumberTextView;
        public TextView officeNumberTextView;
        public ImageView photoImageView;  // Corrected ID

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewContactTitle);
            homeNumberTextView = itemView.findViewById(R.id.textViewHomeNumber);
            officeNumberTextView = itemView.findViewById(R.id.textViewOfficeNumber);
            photoImageView = itemView.findViewById(R.id.profile_icon);  // Corrected ID
        }
    }
}
