
package com.example.ls.mywww;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gl_activity_guide);
        initView();
    }

    private void initView() {
        final Guideview2 guideView = (Guideview2) findViewById(R.id.guide_view);
        guideView.setData(R.drawable.gm3_guide1_iv1, R.drawable.gm3_guide1_iv3, R.drawable.gm3_guide1_iv2, R.drawable.gm3_guide1_iv3);
        guideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                SharedPreferencesUtil.putBoolean(MainActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);

                finish();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
        SharedPreferencesUtil.putBoolean(MainActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
        finish();
    }
}
