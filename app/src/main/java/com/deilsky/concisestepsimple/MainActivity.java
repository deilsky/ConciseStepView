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
    private HorizontalScrollView scrollView1, scrollView2;
    private ConciseStepView stepView1, stepView2;
    private ArrayList<ConciseData> data1 = new ArrayList<ConciseData>();
    private ArrayList<ConciseData> data2 = new ArrayList<ConciseData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView1 = findViewById(R.id.stepView1);
        scrollView2 = findViewById(R.id.stepView2);

        ConciseData conciseData;
        conciseData = new ConciseData();
        conciseData.setId(1);
        conciseData.setFinish(true);
        conciseData.setName("已付款");
        data1.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(2);
        conciseData.setFinish(true);
        conciseData.setName("已发货");
        data1.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(3);
        conciseData.setFinish(false);
        conciseData.setName("已收货");
        data1.add(conciseData);


        conciseData = new ConciseData();
        conciseData.setId(1);
        conciseData.setFinish(true);
        conciseData.setName("已付款");
        data2.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(2);
        conciseData.setFinish(true);
        conciseData.setName("已发货");
        data2.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(3);
        conciseData.setFinish(false);
        conciseData.setName("已收货");
        data2.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(4);
        conciseData.setFinish(false);
        conciseData.setName("已评价");
        data2.add(conciseData);

        conciseData = new ConciseData();
        conciseData.setId(5);
        conciseData.setFinish(false);
        conciseData.setName("已完成");
        data2.add(conciseData);


        stepView1 = ConciseStepView.create().attach(scrollView1)
                .defaults(R.color.colorPrimaryDark, R.color.colorPrimaryDark, R.drawable.ic_radio_unchecked)
                .currents(R.color.colorAccent, R.color.colorAccent, R.drawable.ic_radio_checked)
                .stepViewWidth(360)
                .stepLineHeight(3)
                .stepTextSize(12)
                .steps(data1).setOnStepClickListener(new ConciseStepView.OnStepClickListener() {
                    @Override
                    public void onStepClick(ConciseData data) {
                        Log.d("onStepClick", data.toString());
                        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        stepView1.build();

        stepView2 = ConciseStepView.create().attach(scrollView2)
                .defaults(R.color.colorPrimaryDark, R.color.colorPrimaryDark, R.drawable.ic_radio_unchecked)
                .currents(R.color.colorAccent, R.color.colorAccent, R.drawable.ic_radio_checked)
                .stepViewWidth(360)
                .stepLineHeight(3)
                .stepTextSize(12)
                .steps(data2).setOnStepClickListener(new ConciseStepView.OnStepClickListener() {
                    @Override
                    public void onStepClick(ConciseData data) {
                        Log.d("onStepClick", data.toString());
                        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        stepView2.build();
    }

    @Override
    public void onClick(View v) {
//        int vId = v.getId();
    }
}
