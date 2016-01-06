package cn.wehax.demo.ui.widget.recycler_view.pj_sense;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Sense项目，推荐页使用，满足以下需求：
 * （1）Header
 * （2）Load More
 */
public class SenseRecyclerView extends RecyclerView {
    private static final String TAG = "RV";
    /**
     * RecyclerView仅支持单一OnScrollListener，
     * 但我们需要至少一个，可能多个，比如：
     * （1）一个内部使用，通过监听用户的Scroll行为，控制加载行为
     * （2）一个提供给外部使用
     */
    private final List<OnScrollListener> onScrollListeners = new ArrayList<>();

    private EndlessScrollListener endlessScrollListener;
    private AdapterWrapper adapterWrapper;

    /**
     * true, 开启加载更多功能
     */
    private boolean loadable = false;

    /**
     * footer load more view
     */
    private View loadMoreView;
    /**
     * header view
     */
    private View headerView;

    /**
     * true, is loading more
     */
    private boolean isLoadingMore = false;

    /**
     * This value is used to determine if
     * loading should start when user scrolls the view down.
     */
    private int threshold = 1;

    private OnLoadMoreListener onLoadMoreListener;

    public SenseRecyclerView(Context context) {
        this(context, null);
    }

    public SenseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SenseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setOnScrollListener(new OnScrollListenerImpl());
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        adapterWrapper = new AdapterWrapper(adapter);
        super.setAdapter(adapterWrapper);
    }

    @Override
    public Adapter getAdapter() {
        return adapterWrapper.getAdapter();
    }

    /**
     * listen load more event
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public void setHeaderView(View view) {
        headerView = view;
    }

    /**
     * Sets progress view to show on the bottom of the list when loading starts.
     *
     * @param layoutResId layout resource ID
     */
    public void setLoadMoreView(int layoutResId) {
        setProgressView(LayoutInflater
                .from(getContext())
                .inflate(layoutResId, this, false));
    }

    /**
     * Sets progress view to show on the bottom of the list when loading starts.
     *
     * @param view the view
     */
    public void setProgressView(View view) {
        loadMoreView = view;
    }

    /**
     * Sets progress view to show on the bottom of the list when loading starts.
     *
     * @param layoutResId layout resource ID
     */
    public void setProgressView(int layoutResId) {
        setProgressView(LayoutInflater
                .from(getContext())
                .inflate(layoutResId, this, false));
    }

    /**
     * 开启或关闭加载更多功能
     *
     * @param loadable
     * @note call before setAdapter
     */
    public void setLoadable(boolean loadable) {
        this.loadable = loadable;

        if (loadable == false && isLoadingMore) {
            finishLoadingMore();
        }

        if (!loadable) {
            removeOnScrollListener(endlessScrollListener);
            endlessScrollListener = null;
        } else {
            endlessScrollListener = new EndlessScrollListener();
            addOnScrollListener(endlessScrollListener);
        }
    }

    /**
     * call this method to show loading view
     */
    private void startLoadingMore() {
        if (this.isLoadingMore == true) {
            return;
        }
        this.isLoadingMore = true;
        this.adapterWrapper.notifyDataSetChanged();
    }

    /**
     * If load more completed you may want to call this method to hide loading view.
     */
    public void finishLoadingMore() {
        if (this.isLoadingMore == false) {
            return;
        }
        this.isLoadingMore = false;
        this.adapterWrapper.notifyDataSetChanged();
    }

    /**
     * Sets threshold to use. Only positive numbers are allowed. This value is used to determine if
     * loading should start when user scrolls the view down. Default value is 1.
     *
     * @param threshold positive number
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

//    /**
//     * WxRecyclerView加入Header和Footer后，原Adapter的notifyDataSetChanged方法将失效，
//     * 因为实际Adapter是WrapAdapter
//     */
//    public void notifyDataSetChanged() {
//        mAdapter.notifyDataSetChanged();
//    }


    /**
     * @param layout instances of {@link LinearLayoutManager} only
     */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new IllegalArgumentException(
                    "layout manager must be an instance of LinearLayoutManager");
        }
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) super.getLayoutManager();
    }

    /**
     * Adds {@link RecyclerView.OnScrollListener} to use with this view.
     *
     * @param listener listener to add
     */
    public void addOnScrollListener(OnScrollListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null");
        }
        onScrollListeners.add(listener);
    }

    /**
     * Removes {@link RecyclerView.OnScrollListener} to use with this view.
     *
     * @param listener listener to remove
     */
    public void removeOnScrollListener(OnScrollListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null");
        }
        onScrollListeners.remove(listener);
    }

    /**
     * 将RecyclerView的单一OnScrollListener，转变化支持多个OnScrollListener
     */
    private final class OnScrollListenerImpl extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            for (OnScrollListener listener : onScrollListeners) {
                listener.onScrolled(recyclerView, dx, dy);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            for (OnScrollListener listener : onScrollListeners) {
                listener.onScrollStateChanged(recyclerView, newState);
            }
        }
    }

    /**
     * 监听用户Scroll行为，在合适的时候开始加载更多
     */
    private final class EndlessScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (isLoadingMore || !loadable || dy <= 0)
                return;

            int lastVisibleItemPosition = getLayoutManager()
                    .findLastVisibleItemPosition();
            int itemCount = getLayoutManager().getItemCount();

            Log.e(TAG, "onScrolled");
            Log.e(TAG, "lastVisibleItemPosition = " + lastVisibleItemPosition);
            Log.e(TAG, "itemCount = " + itemCount);

//            dy >0 向上滑动
            if (itemCount - lastVisibleItemPosition <= threshold) {
                startLoadingMore();
                onLoadMoreListener.onLoadMore();
            }
        }
    }

    private final class AdapterWrapper extends Adapter<ViewHolder> {
        private static final int TYPE_HEADER_VIEW = -1;
        private static final int TYPE_LOAD_MORE_VIEW = -2;

        private final Adapter<ViewHolder> adapter;

        private HeaderFooterViewHolder progressViewHolder;

        public AdapterWrapper(Adapter<ViewHolder> adapter) {
            if (adapter == null) {
                throw new NullPointerException("adapter is null");
            }
            this.adapter = adapter;
            setHasStableIds(adapter.hasStableIds());
        }

        @Override
        public int getItemCount() {
            int count = adapter.getItemCount();

            if (isLoadingMore && loadMoreView != null)
                ++count;

            if (headerView != null)
                ++count;

            Log.e(TAG, "getItemCount = " + count);
            return count;
        }

        @Override
        public long getItemId(int position) {
            if (headerView != null)
                --position;

            if (position >= 0 && position < adapter.getItemCount()) {
                return adapter.getItemId(position);
            }

            return NO_ID;
        }

        @Override
        public int getItemViewType(int position) {
            int headerNum = 0;
            if (headerView != null)
                ++headerNum;

            if (position < headerNum) {
                return TYPE_HEADER_VIEW;
            }

            position -= headerNum;
            if (position < adapter.getItemCount()) {
                return adapter.getItemViewType(position);
            }

            return TYPE_LOAD_MORE_VIEW;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // header 和 footer 视图是固定的，无须绑定数据

//            Log.e(TAG, "onBindViewHolder");
//            Log.e(TAG, "position = " + position);
//            Log.e(TAG, "getItemCount = " + getItemCount());
//            Log.e(TAG, "getItemViewType = " + getItemViewType(position));

            if (headerView != null)
                --position;

            if (position >= 0 && position < adapter.getItemCount()) {
                adapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_LOAD_MORE_VIEW) {
                // 设置加载更多视图
                return new HeaderFooterViewHolder(loadMoreView);
            } else if (viewType == TYPE_HEADER_VIEW) {
                return new HeaderFooterViewHolder(headerView);
            } else {
                return adapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return holder == progressViewHolder || adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewRecycled(holder);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            adapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            adapter.unregisterAdapterDataObserver(observer);
        }

        public Adapter<ViewHolder> getAdapter() {
            return adapter;
        }

        private final class HeaderFooterViewHolder extends ViewHolder {
            public HeaderFooterViewHolder(View view) {
                super(view);
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
