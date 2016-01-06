package cn.wehax.demo.ui.widget.recycler_view;

import cn.wehax.demo.framework.simple_list.SimpleListActivity;
import cn.wehax.demo.ui.widget.recycler_view.header_footer.HeaderFooterActivity;
import cn.wehax.demo.ui.widget.recycler_view.normal.NormalRecyclerViewActivity;
import cn.wehax.demo.ui.widget.recycler_view.pj_sense.SenseRecyclerViewActivity;

public class RecyclerViewActivity extends SimpleListActivity {

    @Override
    public void initData() {
        addItem("普通列表", "", NormalRecyclerViewActivity.class);
        addItem("Header和Footer", "", HeaderFooterActivity.class);
        addItem("Sense", "Sense项目推荐页", SenseRecyclerViewActivity.class);
    }

}
