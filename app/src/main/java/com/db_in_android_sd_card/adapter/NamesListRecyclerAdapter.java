package com.db_in_android_sd_card.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db_in_android_sd_card.R;
import com.db_in_android_sd_card.models.NameModel;

import java.util.ArrayList;


/**
 * Adapter for RecyclerView to show list of names.
 */

public class NamesListRecyclerAdapter extends RecyclerView.Adapter<NamesListRecyclerAdapter.ViewHolder> {

    private final ArrayList<NameModel> nameModelArrayList;

    public NamesListRecyclerAdapter(Context context, ArrayList<NameModel> nameModelArrayList) {
        this.nameModelArrayList = nameModelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_name_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView_nameData.setText(nameModelArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (nameModelArrayList != null)
            return nameModelArrayList.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameData;

        ViewHolder(View itemView) {
            super(itemView);
            textView_nameData = (TextView) itemView.findViewById(R.id.textView_nameData);
        }
    }
}