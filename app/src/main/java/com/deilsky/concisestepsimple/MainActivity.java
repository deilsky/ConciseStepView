package com.deilsky.concisestepsimple;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deilsky.ConciseData;
import com.deilsky.ConciseStepView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HorizontalScrollView scrollView;
    private ConciseStepView stepView;
    private ArrayList<ConciseData> data = new ArrayList<ConciseData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.stepView);


        ConciseData conciseData;
        conciseData = new ConciseData();
        conciseData.setId(1);
        conciseData.setFinish(true);
        conciseData.setName("已付款");
        data.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(2);
        conciseData.setFinish(true);
        conciseData.setName("已发货");
        data.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(3);
        conciseData.setFinish(false);
        conciseData.setName("已收货");
        data.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(4);
        conciseData.setFinish(false);
        conciseData.setName("已评价");
        data.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(5);
        conciseData.setFinish(false);
        conciseData.setName("已完成");
        data.add(conciseData);


        stepView = ConciseStepView.create().attach(scrollView)
                .defaults(R.color.colorPrimaryDark, R.color.colorPrimaryDark, R.drawable.ic_radio_unchecked)
                .currents(R.color.colorAccent, R.color.colorAccent, R.drawable.ic_radio_checked)
                .stepViewWidth(360)
                .stepLineHeight(5)
                .stepTextSize(12)
                .steps(data).setOnStepClickListener(new ConciseStepView.OnStepClickListener() {
                    @Override
                    public void onStepClick(ConciseData data) {
                        Log.d("onStepClick", data.toString());
                        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        stepView.build();
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
    }
}
