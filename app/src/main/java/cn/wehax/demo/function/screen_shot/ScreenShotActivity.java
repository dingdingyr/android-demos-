package cn.wehax.demo.function.screen_shot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.wehax.common.util.ImageUtils;
import cn.wehax.common.util.ScreenShotUtils;
import cn.wehax.common.util.ToastUtils;
import cn.wehax.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class ScreenShotActivity extends RoboActivity implements View.OnClickListener {
    private static final String TAG = "ScreenShotActivity";

    @InjectView(R.id.screen_shot_btn)
    Button screenShot;

    @InjectView(R.id.hello_tv)
    TextView textView;

    @InjectView(R.id.screen_show_iv)
    ImageView screenShotIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        screenShot.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.screen_shot_btn:
                String imageDir = Environment.getExternalStorageDirectory() + File.separator + "image" + File.separator;
                ImageUtils.save(ScreenShotUtils.getActivityBitmap(this), imageDir + "activity.png");
                Bitmap bmp = ScreenShotUtils.getViewBitmap(textView);
                Log.e(TAG, "imagePath=" + imageDir + "textview.png");
                ImageUtils.save(bmp, imageDir + "textview.png");
                screenShotIv.setImageBitmap(bmp);
                ToastUtils.show(this, "success");
                break;
        }
    }
}
