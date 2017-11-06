package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_GetCity;
import com.jhcms.common.model.Response_Get_City;
import com.jhcms.common.stickylistheaders.ExpandableStickyListHeadersListView;
import com.jhcms.common.stickylistheaders.StickyListHeadersListView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.IndexView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.SelectCityAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectCityActivity extends SwipeBaseActivity {

    public static String CITY = "CITY";

    @Bind(R.id.iv_words)
    IndexView ivWords;
    @Bind(R.id.itemListView)
    ExpandableStickyListHeadersListView itemListView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_word)
    TextView tvWord;
    private SelectCityAdapter cityAdapter;
    private WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<View, Integer>();
    private List<Data_GetCity.ItemsEntity> items;
    private Handler handler = new Handler();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("选择城市");
        toolbar.setNavigationIcon(R.mipmap.icon_web_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestCity();
        String city = getIntent().getStringExtra(CITY);
        cityAdapter = new SelectCityAdapter(this);
        itemListView.setAnimExecutor(new AnimationExecutor());
        itemListView.setAdapter(cityAdapter);
        TextView tvCity = (TextView) getLayoutInflater().inflate(R.layout.city_list_header, null);
        tvCity.setText("当前选择城市:" + city);
        itemListView.addHeaderView(tvCity);
        itemListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if (itemListView.isHeaderCollapsed(headerId)) {
                    itemListView.expand(headerId);
                } else {
                    itemListView.collapse(headerId);
                }
            }
        });
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(SelectAddressActivity.CITY_CODE, items.get(position - 1).city_code);
                    intent.putExtra(SelectAddressActivity.CITY_NAME, items.get(position - 1).city_name);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        ivWords.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                updateWord(word);
                updateListView(word);

            }
        });

    }

    private void updateWord(String word) {
        /*显示*/
        tvWord.setVisibility(View.VISIBLE);
        tvWord.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*也运行在主线程*/
                tvWord.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void updateListView(String word) {
        for (int i = 0; i < items.size(); i++) {
            if (word.equals(items.get(i).py)) {
                itemListView.setSelection(i + 1);
                return;
            }
        }
    }

    private void requestCity() {
        HttpUtils.postUrl(this, Api.GET_CITY, null, true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                try {
                    Gson gson = new Gson();
                    Response_Get_City response = gson.fromJson(Json, Response_Get_City.class);
                    if (response.error.equals("0")) {
                        items = response.data.items;
                        Collections.sort(items, new Comparator<Data_GetCity.ItemsEntity>() {
                            @Override
                            public int compare(Data_GetCity.ItemsEntity lhs, Data_GetCity.ItemsEntity rhs) {
                                return lhs.py.compareTo(rhs.py);
                            }
                        });
                        cityAdapter.setData(items);
                    } else {
                        ToastUtil.show(response.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show("解析异常");
                    Utils.saveCrashInfo2File(e);
                }

            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }

    class AnimationExecutor implements ExpandableStickyListHeadersListView.IAnimationExecutor {

        @Override
        public void executeAnim(final View target, final int animType) {
            if (ExpandableStickyListHeadersListView.ANIMATION_EXPAND == animType && target.getVisibility() == View.VISIBLE) {
                return;
            }
            if (ExpandableStickyListHeadersListView.ANIMATION_COLLAPSE == animType && target.getVisibility() != View.VISIBLE) {
                return;
            }
            if (mOriginalViewHeightPool.get(target) == null) {
                mOriginalViewHeightPool.put(target, target.getHeight());
            }
            final int viewHeight = mOriginalViewHeightPool.get(target);
            float animStartY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? 0f : viewHeight;
            float animEndY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? viewHeight : 0f;
            final ViewGroup.LayoutParams lp = target.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofFloat(animStartY, animEndY);
            animator.setDuration(200);
            target.setVisibility(View.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND) {
                        target.setVisibility(View.VISIBLE);
                    } else {
                        target.setVisibility(View.GONE);
                    }
                    target.getLayoutParams().height = viewHeight;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.height = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    target.setLayoutParams(lp);
                    target.requestLayout();
                }
            });
            animator.start();

        }
    }
}
