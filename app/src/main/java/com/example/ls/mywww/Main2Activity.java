
package com.example.ls.mywww;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {



//    public static void launch(Context context, Intent nextIntent) {
//        Main2Activity.nextIntent = nextIntent;
//        Intent intent = new Intent(context, Main2Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(intent);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        spl = (SplashView) findViewById(R.id.splash_ciew);
//        if (nextIntent != null) {
//            spl.setVisibility(View.GONE);
//            startActivity(nextIntent);
//            nextIntent = null;
//        }

    }


}
