package com.jhcms.waimaiV3.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhcms.common.widget.PinnedHeaderListView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.OnSiteServiceLeftAdapter;
import com.jhcms.waimaiV3.adapter.OnSiteServiceRightAdapter;
import com.jhcms.waimaiV3.model.DishItem;
import com.jhcms.waimaiV3.model.DishModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Wyj on 2017/5/9
 * TODO:
 */
public class OnSiteServiceListActivity extends SwipeBaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.left_listview)
    ListView leftListview;
    @Bind(R.id.pinnedheader_listview)
    PinnedHeaderListView pinnedheaderListview;
    private DishModel dishModel;
    private OnSiteServiceLeftAdapter leftAdapter;
    private OnSiteServiceRightAdapter rightAdapter;
    private boolean isScroll = false;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_on_site_service_list);
        ButterKnife.bind(this);
    }
    @Override
    protected void initData() {
        tvTitle.setText("全部服务");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dishModel = new DishModel();
        String[] title = getResources().getStringArray(R.array.shangmenfuwu);
        /*添加数据*/
        for (int i = 0; i < title.length; i++) {
            /*左边列表*/
            dishModel.addLeft(title[i]);

            List<DishItem> itemList = new ArrayList<>();
            itemList.add(new DishItem(title[i] + i));
             /*右边列表*/
            dishModel.addRight(title[i], itemList);
        }
        leftAdapter = new OnSiteServiceLeftAdapter(this, dishModel);
        leftListview.setAdapter(leftAdapter);

        rightAdapter = new OnSiteServiceRightAdapter(this, dishModel);
        pinnedheaderListview.setAdapter(rightAdapter);

        leftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;
                /*设置 选中的位置*/
                setLeft(position);

                /*右边选中的位置*/
                int rightSection = 0;

                for (int i = 0; i < position; i++) {
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                pinnedheaderListview.setSelection(rightSection);
            }
        });
        leftListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScroll = false;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isScroll = false;
            }
        });
        pinnedheaderListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    int leftSection = rightAdapter.getSectionForPosition(firstVisibleItem);
                    if (dishModel.getLeftPositionSelected() != leftSection) {
                        setLeft(leftSection);
                    }
                    isScroll = false;
                } else {
                    isScroll = true;
                }
            }
        });
    }



    private void setLeft(int position) {
        dishModel.setLeftPositionSelected(position);
        leftAdapter.notifyDataSetChanged();
        if (position <= leftListview.getFirstVisiblePosition() || position >= leftListview.getLastVisiblePosition()) {
            leftListview.smoothScrollToPosition(position);
        }
    }
}
