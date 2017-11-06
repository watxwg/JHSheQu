package com.jhcms.mall.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.mall.adapter.MallMembershipCardAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 会员卡界面
 */
public class MallMembershipCardActivity extends BaseActivity {
    private SpringView RefreshLayout;
    private ListViewForScrollView mLvlistview;
    private View mTitleview;
    private ImageView IvBack;
    private TextView mTvtitle;
    private MallMembershipCardAdapter mAdapter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_membership_card);
    }

    @Override
    protected void initData() {
        initRefreshlayout();
        inintListview();
    }

    private void inintListview() {
        mAdapter=new MallMembershipCardAdapter(MallMembershipCardActivity.this);
        mLvlistview.setAdapter(mAdapter);
    }

    private void initRefreshlayout() {
        RefreshLayout.setGive(SpringView.Give.BOTH);
        RefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMembershipCardActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMembershipCardActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        RefreshLayout.setHeader(new DefaultHeader(MallMembershipCardActivity.this));
        RefreshLayout.setFooter(new DefaultFooter(MallMembershipCardActivity.this));
        RefreshLayout.setType(SpringView.Type.FOLLOW);
    }
    @Override
    protected void initActionBar() {
        IvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallMembershipCardActivity.this.finish();
            }
        });
        mTvtitle.setText("我的会员卡");
    }

    @Override
    protected void initFindViewById() {
        RefreshLayout= (SpringView) this.findViewById(R.id.refreshlayout);
        mLvlistview= (ListViewForScrollView) this.findViewById(R.id.listview);
        mTitleview=this.findViewById(R.id.title);
        IvBack= (ImageView) mTitleview.findViewById(R.id.back_iv);
        mTvtitle= (TextView) mTitleview.findViewById(R.id.title_tv);

    }

    @Override
    protected void initEvent() {

    }
}
