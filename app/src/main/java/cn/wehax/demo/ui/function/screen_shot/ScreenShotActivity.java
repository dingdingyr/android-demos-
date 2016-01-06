package cn.wehax.demo.ui.function.screen_shot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.wehax.demo.R;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import wehax.util.ImageUtils;
import wehax.util.ScreenShotUtils;
import wehax.util.ToastUtils;

public class ScreenShotActivity extends RoboActionBarActivity implements View.OnClickListener {
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
