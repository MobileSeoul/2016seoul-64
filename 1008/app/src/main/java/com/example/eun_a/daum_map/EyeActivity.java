package com.example.eun_a.daum_map;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class EyeActivity extends AppCompatActivity {

    String h_call = Map.hp_pn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.eun_a.daum_map.R.layout.activity_infoeye);

        String h_name = Map.hp_name;
        String h_introduction = Map.hp_intro;
        String h_location = Map.hp_location;
        String h_time = Map.hp_time;
        final String h_url = Map.hp_url;

        String locationtext = "Location";
        String hstimetext = "Office hours";
        String introtext = "Introduction";

        TabHost tab_host = (TabHost) findViewById(com.example.eun_a.daum_map.R.id.tabhost);
        tab_host.setup();

        TabHost.TabSpec ts1 = tab_host.newTabSpec("Introduce");
        ts1.setIndicator("");
        ts1.setContent(com.example.eun_a.daum_map.R.id.tab1);
        tab_host.addTab(ts1);

        TabHost.TabSpec ts2 = tab_host.newTabSpec("Location");
        ts2.setIndicator("");
        ts2.setContent(com.example.eun_a.daum_map.R.id.tab2);
        tab_host.addTab(ts2);

        TabHost.TabSpec ts3 = tab_host.newTabSpec("Time");
        ts3.setIndicator("");
        ts3.setContent(com.example.eun_a.daum_map.R.id.tab3);
        tab_host.addTab(ts3);

        TabHost.TabSpec ts4 = tab_host.newTabSpec("HomePage");
        ts4.setIndicator("");
        ts4.setContent(com.example.eun_a.daum_map.R.id.tab4);
        tab_host.addTab(ts4);

        for (int tab = 0; tab < tab_host.getTabWidget().getChildCount(); ++tab) {
            tab_host.getTabWidget().getChildAt(tab).getLayoutParams().height = 200;
            tab_host.getTabWidget().getChildAt(tab).getLayoutParams().width = 200;
        }

        tab_host.getTabWidget().getChildAt(0).setBackgroundResource(com.example.eun_a.daum_map.R.drawable.tab_introduction);
        tab_host.getTabWidget().getChildAt(1).setBackgroundResource(com.example.eun_a.daum_map.R.drawable.tab_location);
        tab_host.getTabWidget().getChildAt(2).setBackgroundResource(com.example.eun_a.daum_map.R.drawable.tab_time);
        tab_host.getTabWidget().getChildAt(3).setBackgroundResource(com.example.eun_a.daum_map.R.drawable.tab_homepage);
        tab_host.getTabWidget().getChildAt(3).setOnClickListener(
                new TabHost.OnClickListener() {
                    public void onClick(View V) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(h_url));
                        startActivity(intent);
                    }
                }
        );
        tab_host.setCurrentTab(0);


        TextView textView1 = (TextView) findViewById(com.example.eun_a.daum_map.R.id.hospitalname);
        textView1.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView1.setText(h_name);

        TextView textView2 = (TextView) findViewById(com.example.eun_a.daum_map.R.id.introduction);
        textView2.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView2.setText(h_introduction);

        TextView textView3 = (TextView) findViewById(com.example.eun_a.daum_map.R.id.location);
        textView3.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView3.setText(h_location);

        TextView textView4 = (TextView) findViewById(com.example.eun_a.daum_map.R.id.hstime);
        textView4.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView4.setText(h_time);

        TextView textView6 = (TextView) findViewById(R.id.locationtext);
        textView6.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView6.setText(locationtext);

        TextView textView7 = (TextView) findViewById(R.id.hstimetext);
        textView7.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView7.setText(hstimetext);

        TextView textView8 = (TextView) findViewById(R.id.introtext);
        textView8.setTypeface(Typeface.createFromAsset(getAssets(), "DOHYEON.ttf"));
        textView8.setText(introtext);
    }

    public void onButton11Clicked(View v) {
        Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+h_call));
        startActivity(intent);
    }
}
