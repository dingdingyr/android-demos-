package cn.wehax.demo.ui.main;

import cn.wehax.demo.framework.simple_list.SimpleListFragment;
import cn.wehax.demo.ui.function.screen_shot.ScreenShotActivity;

public class FunctionFragment extends SimpleListFragment {

    public static FunctionFragment newInstance() {
        FunctionFragment fragment = new FunctionFragment();
        return fragment;
    }

    public FunctionFragment() {
        // Required empty public constructor
    }


    @Override
    public void initData() {
        addItem("截图", "控件截图，全屏截图...", ScreenShotActivity.class);
    }
}
