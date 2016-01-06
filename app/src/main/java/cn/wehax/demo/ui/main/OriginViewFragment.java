package cn.wehax.demo.ui.main;

import cn.wehax.demo.framework.simple_list.SimpleListFragment;
import cn.wehax.demo.ui.widget.recycler_view.RecyclerViewActivity;
import cn.wehax.demo.ui.widget.view.ViewActivity;

public class OriginViewFragment extends SimpleListFragment {

    public static OriginViewFragment newInstance() {
        OriginViewFragment fragment = new OriginViewFragment();
        return fragment;
    }

    public OriginViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void initData() {
        addItem("View", "控件基类", ViewActivity.class);
        addItem("RecyclerView", "列表", RecyclerViewActivity.class);
    }
}
