package cn.wehax.demo.widget.count_down_text_view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class CountDownTextViewActivity extends RoboActivity {

    @InjectView(R.id.count_down_text_view_btn)
    Button mCountDownTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_text_view);

        mCountDownTextViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
