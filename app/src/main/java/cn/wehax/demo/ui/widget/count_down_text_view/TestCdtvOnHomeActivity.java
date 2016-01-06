package cn.wehax.demo.ui.widget.count_down_text_view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import wehax.widget.CountDownTextView;

/**
 * 测试程序：<br>
 * 在本活动页面中有一个CountDownTextView控件，如果该控件正在倒计，点击Home键。<br>
 * 此时，控件是否还在倒计时？<br>
 * 换种说法，活动页面中有个线程任务正在执行，点击Home键。<br>
 * 此时，线程任务是否还在运行？<br>
 *
 * 测试结果：
 */
public class TestCdtvOnHomeActivity extends RoboActivity {
    public static final String TAG = "test";

    @InjectView(R.id.count_down_text_view)
    CountDownTextView countDownTextView;

    @InjectView(R.id.start_count_down)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cdtv_on_home);

        countDownTextView.setOnCountDownLisenter(new CountDownTextView.OnCountDownLisenter() {
            @Override
            public void onFinish() {
                Log.e(TAG, "倒计时结束");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("test", "onTick");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTextView.startCountDown(10 * 1000, 1000);
            }
        });

    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }
}
