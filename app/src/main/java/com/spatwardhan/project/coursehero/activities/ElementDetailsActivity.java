package com.spatwardhan.project.coursehero.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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

public class ElementDetailsActivity extends AppCompatActivity {
    // Layout Views
    @BindView(R.id.logoImageView)
    ImageView logoImageView;

    @BindView(R.id.courseNameTextView)
    TextView courseName;

    @BindView(R.id.universityNameTextView)
    TextView universityName;

    @BindView(R.id.numberOfCoursesTextView)
    TextView numberOfCourses;

    @BindView(R.id.descriptionTextView)
    TextView description;

    CatalogElement catalogElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        catalogElement = (CatalogElement) bundle.getSerializable(CatalogSearchActivity.CATALOG_EXTRA);

        if (catalogElement != null) {
            updateUI();
        }
    }

    private void updateUI() {
        getSupportActionBar().setTitle(catalogElement.getName());
        Picasso.with(this).load(catalogElement.getPhotoUrl())
                .into(logoImageView);

        courseName.setText(catalogElement.getName());
        description.setText(catalogElement.getDescription());

        if (catalogElement instanceof Specialization) {
            numberOfCourses.setText(((Specialization) catalogElement).getCoursesString());
            numberOfCourses.setVisibility(View.VISIBLE);
        }

        if (catalogElement.getPartners().size() > 1) {
            StringBuilder sb = new StringBuilder();
            List<Partner> partners = catalogElement.getPartners();
            int size = partners.size();
            for (int i = 0; i < size; i++) {
                sb.append(partners.get(i).getName());
                if (i != size - 1) {
                    sb.append(" | ");
                }
            }
            universityName.setText(sb.toString());
        } else {
            universityName.setText(catalogElement.getPartners().get(0).getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
