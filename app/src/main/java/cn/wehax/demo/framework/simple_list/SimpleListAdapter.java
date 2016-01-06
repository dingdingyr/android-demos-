package cn.wehax.demo.framework.simple_list;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.wehax.demo.R;

class SimpleListAdapter extends BaseAdapter {
    List<ItemBean> mDataList;
    Activity activity;

    public SimpleListAdapter(Activity activity, List<ItemBean> mDataList) {
        this.activity = activity;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public ItemBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_simple_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemBean item = getItem(position);

        viewHolder.title.setText(item.getTitle());
        if (item.getDes() != null)
            viewHolder.des.setText(item.getDes());

        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public TextView des;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.item_title);
            des = (TextView) view.findViewById(R.id.item_des);
        }
    }
}