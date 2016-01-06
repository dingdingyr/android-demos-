package cn.wehax.demo.ui.widget.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import wehax.util.MoveUtils;

public class ViewActivity extends RoboActivity{
    @InjectView(R.id.on_touch_listener_btn)
    Button onTouchListenerBtn;

    @InjectView(R.id.on_click_listener_btn)
    Button onClickListenerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        onTouchListenerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveUtils.moveToActivity(ViewActivity.this, OnTouchListenerActivity.class);
            }
        });

        onClickListenerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveUtils.moveToActivity(ViewActivity.this, OnClickListenerActivity.class);
            }
        });
    }
}
