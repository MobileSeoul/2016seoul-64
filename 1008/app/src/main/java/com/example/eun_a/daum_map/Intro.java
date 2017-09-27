package com.example.eun_a.daum_map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Intro extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.eun_a.daum_map.R.layout.activity_intro);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 2000); // 2초 후에 hd Handler 실행
    }

    private class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
            Intro.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }

}