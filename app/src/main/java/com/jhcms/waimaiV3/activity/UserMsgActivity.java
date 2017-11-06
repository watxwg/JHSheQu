package com.jhcms.waimaiV3.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data;
import com.jhcms.common.model.Data_WaiMai_Msg;
import com.jhcms.common.model.Response_WaiMai_Msg;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.UserMsgAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

public class UserMsgActivity extends SwipeBaseActivity {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_view)
    LRecyclerView rvMsg;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private UserMsgAdapter msgAdapter;
    private int pageNum = 1;
    private List<Data_WaiMai_Msg.ItemsEntity> items;
    private int readId;


    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("消息中心");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        msgAdapter = new UserMsgAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(msgAdapter);
        rvMsg.setAdapter(lRecyclerViewAdapter);

        rvMsg.addItemDecoration(Utils.setDivider(this, R.dimen.dp_1, R.color.background));

        rvMsg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置头部加载颜色
        rvMsg.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        rvMsg.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        rvMsg.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        rvMsg.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rvMsg.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        rvMsg.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                requestMsg();

            }
        });
        rvMsg.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                requestMsg();

            }
        });
        msgAdapter.setOnMsgClickListener(new UserMsgAdapter.OnMsgClickListener() {
            @Override
            public void onMsgClick(int position) {
                readId = position;
                if (null != data && data.size() > 0 && data.get(position).is_read.equals("0")) {
                    requestReadMsg(data.get(position).message_id);
                }
            }
        });
        rvMsg.refresh();
    }

    private void requestReadMsg(final String message_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("message_id", message_id);
            HttpUtils.postUrl(this, Api.WAIMAI_READMSG, object.toString(), true, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    try {
                        Gson gson = new Gson();
                        Data json = gson.fromJson(Json, Data.class);
                        if (json.error.equals("0")) {
                            List<Data_WaiMai_Msg.ItemsEntity> items = data;
                            Data_WaiMai_Msg.ItemsEntity item = items.get(readId);
                            item.is_read = "1";
                            items.set(readId, item);
                            msgAdapter.setData(items);
                        } else {
                            ToastUtil.show(json.message);
                        }
                    } catch (Exception e) {
                        ToastUtil.show(getString(R.string.数据解析异常));
                        saveCrashInfo2File(e);
                    }
                }

                @Override
                public void onBeforeAnimate() {

                }

                @Override
                public void onErrorAnimate() {

                }
            });
        } catch (Exception e) {

        }
    }

    List<Data_WaiMai_Msg.ItemsEntity> data = new ArrayList<>();

    private void requestMsg() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", 0);
            object.put("is_read", 2);
            object.put("page", pageNum);
            HttpUtils.postUrl(this, Api.WAIMAI_MSG, object.toString(), true, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    Gson gson = new Gson();
                    Response_WaiMai_Msg waiMaiMsg = gson.fromJson(Json, Response_WaiMai_Msg.class);
                    if (waiMaiMsg.error.equals("0")) {
                        if (pageNum == 1 && data.size() > 0) {
                            data.clear();
                        }
                        items = waiMaiMsg.data.items;
                        statusview.showContent();
                        if (pageNum == 1 && items.size() == 0) {
                            statusview.showEmpty();
                        } else {
                            if (items.size() == 0) {
                                rvMsg.setNoMore(true);
                            } else {
                                data.addAll(items);
//                                        MsgBean bean = new MsgBean();

                                msgAdapter.setData(data);
                            }
                        }
                        rvMsg.refreshComplete(items.size());
                    } else {
                        statusview.showError();
                        ToastUtil.show(waiMaiMsg.message);
                    }
                    try {
                    } catch (Exception e) {
                        ToastUtil.show(getString(R.string.数据解析异常));
                        saveCrashInfo2File(e);
                        statusview.showError();
                    }
                }

                @Override
                public void onBeforeAnimate() {
                }

                @Override
                public void onErrorAnimate() {
                    statusview.showError();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_msg);
        ButterKnife.bind(this);
    }
}
