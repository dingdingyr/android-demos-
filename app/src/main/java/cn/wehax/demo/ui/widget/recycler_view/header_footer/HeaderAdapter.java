package cn.wehax.demo.ui.widget.recycler_view.header_footer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.wehax.demo.R;

public class HeaderAdapter extends WxRecyclerView.Adapter<String> {

    public HeaderAdapter(List<String> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_text_list, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, String data) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
