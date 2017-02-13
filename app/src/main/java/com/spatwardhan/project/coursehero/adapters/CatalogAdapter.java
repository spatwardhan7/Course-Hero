package com.spatwardhan.project.coursehero.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spatwardhan.project.coursehero.R;
import com.spatwardhan.project.coursehero.models.CatalogElement;
import com.spatwardhan.project.coursehero.models.Partner;
import com.spatwardhan.project.coursehero.models.Specialization;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zky447 on 2/12/17.
 */

public class CatalogAdapter extends ArrayAdapter<CatalogElement> {
    public CatalogAdapter(Context context, List<CatalogElement> elementList) {
        super(context, R.layout.item_catalog, elementList);
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatalogElement catalogElement = getItem(position);
        CatalogAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_catalog, parent, false);
            viewHolder = new CatalogAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CatalogAdapter.ViewHolder) convertView.getTag();
        }

        StringBuilder sb = new StringBuilder();
        for (Partner partner : catalogElement.getPartners()) {
            sb.append(partner.getName());
        }

        if (catalogElement instanceof Specialization) {
            viewHolder.numberOfCourses.setVisibility(View.VISIBLE);
            viewHolder.numberOfCourses.setText(((Specialization) catalogElement).getCoursesString());
        }

        viewHolder.courseName.setText(catalogElement.getName());
        viewHolder.universityName.setText(sb.toString());
        viewHolder.description.setText(catalogElement.getDescription());
        Picasso.with(getContext()).load(catalogElement.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imageView);

        return convertView;
    }
}
