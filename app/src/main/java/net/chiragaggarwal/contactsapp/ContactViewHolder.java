package net.chiragaggarwal.contactsapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    private final TextView textName;
    private View itemView;

    public ContactViewHolder(View itemView) {
        super(itemView);
        textName = (TextView) itemView.findViewById(R.id.text_name);
        this.itemView = itemView;
    }

    public void bind(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        textName.setText(fullName);
    }

    public void setOnClickListener(final OnContactClickListener onContactClickListener, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactClickListener.onContactClick(position);
            }
        });
    }
}
