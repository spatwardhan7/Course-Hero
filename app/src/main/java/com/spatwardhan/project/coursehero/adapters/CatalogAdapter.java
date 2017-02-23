package com.spatwardhan.project.coursehero.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spatwardhan.project.coursehero.R;
import com.spatwardhan.project.coursehero.activities.ElementDetailsActivity;
import com.spatwardhan.project.coursehero.models.CatalogElement;
import com.spatwardhan.project.coursehero.models.Partner;
import com.spatwardhan.project.coursehero.models.Specialization;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private List<CatalogElement> elementList;
    private Context context;
    private static final String CATALOG_EXTRA = "extra";


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CatalogElement catalogElement;
        Context context;

        @BindView(R.id.logoImageView)
        ImageView imageView;

        @BindView(R.id.nameTextView)
        TextView courseName;

        @BindView(R.id.universityNameTextView)
        TextView universityName;

        @BindView(R.id.numberOfCoursesTextView)
        TextView numberOfCourses;

        @BindView(R.id.descriptionTextView)
        TextView description;

        ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, ElementDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CATALOG_EXTRA, catalogElement);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
    }

    public CatalogAdapter(Context context, List<CatalogElement> elements) {
        this.context = context;
        elementList = elements;
    }

    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.item_catalog, parent, false);
        return new ViewHolder(context, row);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder viewHolder, int position) {
        CatalogElement catalogElement = elementList.get(position);
        viewHolder.catalogElement = catalogElement;

        StringBuilder sb = new StringBuilder();
        if (catalogElement.getPartners() != null && catalogElement.getPartners().size() > 0) {
            Partner partner = catalogElement.getPartners().get(0);
            sb.append(partner.getName());
        }

        if (catalogElement instanceof Specialization) {
            viewHolder.numberOfCourses.setVisibility(View.VISIBLE);
            viewHolder.numberOfCourses.setText(((Specialization) catalogElement).getCoursesString());
        } else {
            viewHolder.numberOfCourses.setVisibility(View.GONE);
        }

        viewHolder.imageView.setImageResource(0);
        Picasso.with(getContext()).load(catalogElement.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imageView);
        viewHolder.courseName.setText(catalogElement.getName());
        viewHolder.universityName.setText(sb.toString());
        viewHolder.description.setText(catalogElement.getDescription());

    }

    @Override
    public int getItemCount() {
        return elementList.size();
    }

    private Context getContext() {
        return context;
    }
}
