package cn.wehax.demo.ui.widget.recycler_view.pj_sense;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.List;

import cn.wehax.demo.R;
import cn.wehax.demo.support.factory.DataFactory;
import cn.wehax.demo.ui.widget.recycler_view.adapter.RecyclerViewAdapter;
import roboguice.activity.RoboActionBarActivity;

public class SenseRecyclerViewActivity extends RoboActionBarActivity {
    private static final long DELAY = 500L;
    private static final String TAG = "Activity";

    private List<String> mList;
    private RecyclerViewAdapter mAdapter;
    private SenseRecyclerView mListView;
    private TextView headerView;

    private final Handler handler = new Handler();

    int page = 1;
    int pageNum = 3;

    @Inject
    DataFactory dataFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sense_recycler_view);

        mList = dataFactory.getStringList(10);

        headerView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.margin_huge));
        headerView.setLayoutParams(params);
        headerView.setGravity(Gravity.CENTER);
        headerView.setText("我是头部View001");
        headerView.setBackgroundColor(getResources().getColor(R.color.gray));

        mListView = (SenseRecyclerView) findViewById(R.id.recycler_view);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setHeaderView(headerView);
        mListView.setLoadable(true);
        mListView.setThreshold(5);
        mListView.setProgressView(R.layout.item_progress);
        mListView.setAdapter(new RecyclerViewAdapter(mList));
        mListView.setOnLoadMoreListener(new SenseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e(TAG, "onLoadMore");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.addAll(dataFactory.getStringList(mList.size(), 10));
                        mListView.finishLoadingMore();

//                        if(++page >= pageNum){
//                            mListView.setLoadable(false);
//                        }
                    }
                }, DELAY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
