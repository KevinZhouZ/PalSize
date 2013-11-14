package com.hiisniper.sizer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import com.hiisniper.sizer.R;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_home);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);

        SetupViews();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private void SetupViews() {
        ImageButton headBtn = (ImageButton) findViewById(R.id.home_btn_head);
        headBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(HomeActivity.this, DataListActivity.class);
                captureIntent.putExtra(DataListActivity.OPEN_FROM_HEAD_KEY, 0);
                startActivity(captureIntent);
            }
        });

        ImageButton upperBtn = (ImageButton) findViewById(R.id.home_btn_upper);
        upperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(HomeActivity.this, DataListActivity.class);
                captureIntent.putExtra(DataListActivity.OPEN_FROM_UPPER_KEY, 0);
                startActivity(captureIntent);
            }
        });

        ImageButton lowerBtn = (ImageButton) findViewById(R.id.home_btn_lower);
        lowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(HomeActivity.this, DataListActivity.class);
                captureIntent.putExtra(DataListActivity.OPEN_FROM_LOWER_KEY, 0);
                startActivity(captureIntent);
            }
        });

        ImageButton shoesBtn = (ImageButton) findViewById(R.id.home_btn_shoes);
        shoesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(HomeActivity.this, DataListActivity.class);
                captureIntent.putExtra(DataListActivity.OPEN_FROM_SHOES_KEY, 0);
                startActivity(captureIntent);
            }
        });
    }
}
