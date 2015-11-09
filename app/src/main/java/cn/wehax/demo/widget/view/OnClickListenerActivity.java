package cn.wehax.demo.widget.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.trinea.android.common.util.ToastUtils;
import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class OnClickListenerActivity extends RoboActivity {
    public static final String TAG = "OnClickListenerActivity";

    @InjectView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_listener);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(OnClickListenerActivity.this, "onClick");
            }
        });
    }


}
