package cn.wehax.demo.ui.widget.recycler_view.header_footer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.wehax.demo.R;

public class HeaderFooterActivity extends AppCompatActivity {

    private List<String> mList ;
    private HeaderAdapter mAdapter ;
    private WxRecyclerView mListView ;

    private TextView headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_recycler_view);

        mList = new ArrayList<>() ;
        for (int i = 0 ; i < 30 ; i ++){
            mList.add("my love "+ i);
        }

        headerView = new TextView(this) ;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.normal_size)) ;
        headerView.setLayoutParams(params);
        headerView.setGravity(Gravity.CENTER);
        headerView.setText("我是头部View001");

        mListView = (WxRecyclerView) findViewById(R.id.recycler_view);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.addHeaderView(headerView);
        mListView.setAdapter(new HeaderAdapter(mList));
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
