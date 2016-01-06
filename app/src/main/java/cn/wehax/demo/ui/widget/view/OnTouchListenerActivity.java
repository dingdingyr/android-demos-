package cn.wehax.demo.ui.widget.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import wehax.util.ToastUtils;

public class OnTouchListenerActivity extends RoboActivity {
    public static final String TAG = "OnTouchListenerActivity";

    @InjectView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_touch_listener);

        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View V, MotionEvent e) {
                int x, y;
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "-----------ACTION_DOWN----------");
                        x = (int) e.getRawX();
                        y = (int) e.getRawY();
                        Log.e(TAG, "x=" + x + ", y=" + y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "-----------ACTION_MOVE----------");
                        x = (int) e.getRawX();
                        y = (int) e.getRawY();
                        Log.e(TAG, "x=" + x + ", y=" + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "-----------ACTION_UP----------");
                        x = (int) e.getRawX();
                        y = (int) e.getRawY();
                        Log.e(TAG, "x=" + x + ", y=" + y);
                        break;
                }

                return false;
            }
        });

        /**
         * 测试：控件能否同时监听onTouch事件和onClick事件
         */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(OnTouchListenerActivity.this, "onClick");
            }
        });
    }


}
