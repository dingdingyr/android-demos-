package cn.wehax.demo.ui.widget.recycler_view.header_footer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展原生RecyclerView
 * <p/>
 * 新增：
 * （1）Header和Footer
 * （2）
 * @note WrapAdapter看起来高端大气，用起来实在不怎么样～还是把WrapAdapter和Adapter合并封装比较好！
 *
 */
public class WxRecyclerView extends RecyclerView {

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFootViews = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;

    public WxRecyclerView(Context context) {
        super(context);
    }

    public WxRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view) {
        mHeaderViews.add(view);
        if (mAdapter != null && !(mAdapter instanceof WrapAdapter)) {
            setAdapter(mAdapter);
        }
    }

    public void addFootView(View view) {
        mFootViews.add(view);
        if (mAdapter != null && !(mAdapter instanceof WrapAdapter)) {
            setAdapter(mAdapter);
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
            super.setAdapter(adapter);
        } else {
            adapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }

    /**
     * WxRecyclerView加入Header和Footer后，原Adapter的notifyDataSetChanged方法将失效，
     * 因为实际Adapter是WrapAdapter
     */
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 当RecycleView添加了Header或Footer时使用的Adapter
     *
     * @note 实现时，运用了装饰设计模式
     */
    public static class WrapAdapter extends RecyclerView.Adapter {
        private RecyclerView.Adapter mSourceAdapter;

        private ArrayList<View> mHeaderViews;

        private ArrayList<View> mFootViews;

        static final ArrayList<View> EMPTY_INFO_LIST =
                new ArrayList<View>();

        private int mCurrentPosition;

        public WrapAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFootViews, RecyclerView.Adapter mAdapter) {
            this.mSourceAdapter = mAdapter;

            if (mHeaderViews == null) {
                this.mHeaderViews = EMPTY_INFO_LIST;
            } else {
                this.mHeaderViews = mHeaderViews;
            }

            if (mHeaderViews == null) {
                this.mFootViews = EMPTY_INFO_LIST;
            } else {
                this.mFootViews = mFootViews;
            }
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == RecyclerView.INVALID_TYPE) {
                return new HeaderViewHolder(mHeaderViews.get(0));
            } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
                return new HeaderViewHolder(mFootViews.get(0));
            }
            return mSourceAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return;
            }
            int adjPosition = position - numHeaders;
            int adapterCount = 0;
            if (mSourceAdapter != null) {
                adapterCount = mSourceAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mSourceAdapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mSourceAdapter != null) {
                return getHeadersCount() + getFootersCount() + mSourceAdapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            mCurrentPosition = position;
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return RecyclerView.INVALID_TYPE;
            }
            int adjPosition = position - numHeaders;
            int adapterCount = 0;
            if (mSourceAdapter != null) {
                adapterCount = mSourceAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mSourceAdapter.getItemViewType(adjPosition);
                }
            }
            return RecyclerView.INVALID_TYPE - 1;
        }


        @Override
        public long getItemId(int position) {
            int numHeaders = getHeadersCount();
            if (mSourceAdapter != null && position >= numHeaders) {
                int adjPosition = position - numHeaders;
                int adapterCount = mSourceAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mSourceAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        public RecyclerView.Adapter getWrappedAdapter() {
            return mSourceAdapter;
        }

        private static class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


    /**
     * 对原生RecyclerView.Adapter进行简单封装，使之更易使用
     * <p/>
     * 添加OnItemClickListener方法，OnItemLongClickListener方法
     *
     * @param <D> 　数据源
     */
    public abstract static class Adapter<D> extends RecyclerView.Adapter {
        private List<D> mDataList;

        private OnItemClickListener mListener;
        private OnItemLongClickListener mLongListener;

        public Adapter(List<D> mDataList) {
            this.mDataList = mDataList;
        }

        public List<D> getDataList() {
            return mDataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            holder = createViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
            if (holder == null) {
                holder = createViewHolder(LayoutInflater.from(parent.getContext()), viewType);
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(v, holder.getPosition(), getItemId(holder.getPosition()));
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mLongListener != null) {
                        return mLongListener.onItemLongClick(v, holder.getPosition(), getItemId(holder.getPosition()));
                    }
                    return false;
                }
            });

            onBindViewHolder(holder, position, mDataList.get(position));
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener listener) {
            mLongListener = listener;
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Deprecated
        public ViewHolder createViewHolder(LayoutInflater inflater, int viewType) {
            return null;
        }

        public ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return null;
        }

        public abstract void onBindViewHolder(ViewHolder holder, int position, D data);

        public interface OnItemClickListener {
            void onItemClick(View view, int position, long id);

        }

        public interface OnItemLongClickListener {
            boolean onItemLongClick(View view, int position, long id);
        }
    }
}
