package cn.wehax.demo.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.wehax.common.util.MoveUtils;
import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActivity {

    @InjectView(R.id.origin_view_btn)
    Button originViewBtn;

    @InjectView(R.id.custom_view_btn)
    Button customViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveUtils.moveToActivity(MainActivity.this, OriginViewActivity.class);
            }
        });

        customViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveUtils.moveToActivity(MainActivity.this, CustomViewActivity.class);
            }
        });

    }


}
