package cn.wehax.demo.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.wehax.common.util.MoveUtils;
import cn.wehax.demo.R;
import cn.wehax.demo.function.screen_shot.ScreenShotActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity implements View.OnClickListener{

    @InjectView(R.id.tab_origin_view_btn)
    Button originViewBtn;

    @InjectView(R.id.tab_custom_view_btn)
    Button customViewBtn;

    @InjectView(R.id.tab_screen_shot)
    TextView screenShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        originViewBtn.setOnClickListener(this);
        customViewBtn.setOnClickListener(this);
        screenShot.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_origin_view_btn:
                MoveUtils.moveToActivity(MainActivity.this, OriginViewActivity.class);
                break;
            case R.id.tab_custom_view_btn:
                MoveUtils.moveToActivity(MainActivity.this, CustomViewActivity.class);
                break;
            case R.id.tab_screen_shot:
                MoveUtils.moveToActivity(MainActivity.this, ScreenShotActivity.class);
                break;
        }

    }
}
