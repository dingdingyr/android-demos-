package cn.wehax.demo.framework.simple_list;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.wehax.demo.R;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import wehax.util.MoveUtils;


public abstract class SimpleListActivity extends RoboActionBarActivity {
    @InjectView(R.id.list_view)
    ListView mListView;

    List<ItemBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.framework_simple_list_activity);

        initData();
        mListView.setAdapter(new SimpleListAdapter(this, mDataList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean itemBean = mDataList.get(position);
                MoveUtils.moveToActivity(SimpleListActivity.this, itemBean.getTargetClazz(), itemBean.getBundle());
            }
        });
    }

    protected void addItem(String title, String des, Class<?> targetClazz) {
        addItem(title, des, targetClazz, null);
    }

    protected void addItem(String title, String des, Class<?> TargetClazz, Bundle bundle) {
        ItemBean item = new ItemBean();
        item.setTitle(title);
        item.setDes(des);
        item.setTargetClazz(TargetClazz);
        item.setBundle(bundle);
        mDataList.add(item);
    }

    /**
     * 初始化列表数据
     */
    public abstract void initData();
}
