package cn.wehax.demo.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.wehax.common.util.MoveUtils;
import cn.wehax.demo.R;
import cn.wehax.demo.widget.view.ViewActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class OriginViewActivity extends RoboActivity implements View.OnClickListener {
    @InjectView(R.id.view_btn)
    Button viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin_view);
        viewBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_btn:
                MoveUtils.moveToActivity(OriginViewActivity.this, ViewActivity.class);
                break;
        }

    }
}
