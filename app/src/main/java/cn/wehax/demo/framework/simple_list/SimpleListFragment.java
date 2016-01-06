package cn.wehax.demo.framework.simple_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.wehax.demo.R;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import wehax.util.MoveUtils;

public abstract class SimpleListFragment extends RoboFragment {
    @InjectView(R.id.list_view)
    ListView mListView;

    List<ItemBean> mDataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.framework_simple_list_fragment, container, false);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        mListView.setAdapter(new SimpleListAdapter(getActivity(), mDataList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean itemBean = mDataList.get(position);
                MoveUtils.moveToActivity(getActivity(), itemBean.getTargetClazz(), itemBean.getBundle());
            }
        });
    }

    /**
     * 初始化列表数据
     */
    public abstract void initData();

}
