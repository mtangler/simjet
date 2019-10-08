package com.app.mayur.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.mayur.pojo.Species;

import java.util.List;

import com.app.mayur.R;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder> {

    public List<Species.Result> data;
    private int rowLayout;
    private Context context;

    public class SpeciesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        List<Species.Result> data;
        TextView tvStatus, tvName, tvClassification, tvDesignation;

        public SpeciesViewHolder(View v, List<Species.Result> data) {

            super(v);
            this.data = data;

            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvClassification = (TextView) v.findViewById(R.id.tvClassification);
            tvDesignation = (TextView) v.findViewById(R.id.tvDesignation);

            tvStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean isExtinct = data.get(getAdapterPosition()).isExtinct();
            data.get(getAdapterPosition()).setExtinct(!isExtinct);
            notifyItemChanged(getAdapterPosition());
        }
    }

    public SpeciesAdapter(List<Species.Result> data, int rowLayout, Context context) {
        this.data = data;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SpeciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);

        return new SpeciesViewHolder(view, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SpeciesViewHolder holder, final int position) {

        String name = data.get(position).getName();
        String classification = data.get(position).getClassification();
        String designation = data.get(position).getDesignation();

        holder.tvName.setText(name);
        holder.tvClassification.setText(classification);
        holder.tvDesignation.setText(designation);

        if (!data.get(position).isExtinct()) {
            holder.tvStatus.setText("Extinct");
        } else {
            holder.tvStatus.setText("Active");
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}