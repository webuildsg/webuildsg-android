package com.trinitystudio.webuildsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.trinitystudio.core.GlobalConstant;
import com.trinitystudio.core.customtabs.CustomTabActivityHelper;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.config.KeyConfig;
import com.trinitystudio.webuildsg.model.repos.RepoSingleModel;

import java.text.SimpleDateFormat;

/**
 * Created by liccowee on 6/14/16.
 */
public class RepoDetailActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tvDate;
    private RepoSingleModel repo;
    private TextView tvDescription;
    private View btnViewCode;
    private TextView tvLanguage;
    private TextView tvStarCount;
    private TextView tvForksCount;
    private TextView tvOpenIssuesCount;
    private SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstant.DATE_TIME_FORMAT3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            repo = (RepoSingleModel) bundle.getSerializable(KeyConfig.BUNDLE_REPO);
            if(repo == null)
            {
                return;
            }
        }
        else
            return;

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        btnViewCode = findViewById(R.id.btn_view_code);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvStarCount = (TextView) findViewById(R.id.tv_star_count);
        tvForksCount = (TextView) findViewById(R.id.tv_forks_count);
        tvOpenIssuesCount = (TextView) findViewById(R.id.tv_open_issue_count);

        tvTitle.setText(repo.getName());
        tvDate.setText(repo.getFormatted_time());
        tvDescription.setText(repo.getDescription());
        tvLanguage.setText(repo.getLanguage());
        tvStarCount.setText(String.valueOf(repo.getStargazers_count()));
        tvForksCount.setText(String.valueOf(repo.getForks_count()));
        tvOpenIssuesCount.setText(String.valueOf(repo.getOpen_issues_count()));

        btnViewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(repo.getHtml_url());
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().setToolbarColor(ContextCompat.getColor(RepoDetailActivity.this, R.color.colorPrimary)).build();
                CustomTabActivityHelper.openCustomTab(RepoDetailActivity.this, customTabsIntent, uri, new CustomTabActivityHelper.CustomTabFallback() {

                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
