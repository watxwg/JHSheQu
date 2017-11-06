package com.jhcms.waimaiV3.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.HackyViewPager;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.SamplePagerAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class PicturePreviewActivity extends AppCompatActivity {

    public static String POSITION = "position";
    public static String IMAGELIST = "imagelist";
    @Bind(R.id.view_pager)
    HackyViewPager viewPager;

    @Bind(R.id.tab_viewpager)
    TextView tabViewpager;
    @Bind(R.id.save)
    TextView save;
    private ArrayList<String> imageList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture_preview);
        ButterKnife.bind(this);
        initData();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if (imageList.get(position).contains("http")) {
                    url = imageList.get(position);
                } else {
                    url = Api.IMAGE_URL + imageList.get(position);
                }
                OkGo.get(url)//
                        .tag(PicturePreviewActivity.this)//
                        .execute(new BitmapCallback() {
                            @Override
                            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                                Date date = new Date();
                                boolean flag = saveBitmap(date.getTime() + "", bitmap);
                                if (flag) {
                                    ToastUtil.show("保存成功");
                                } else {
                                    ToastUtil.show("保存失败");
                                }
                            }

                            @Override
                            public void onBefore(BaseRequest request) {
                                super.onBefore(request);
                                ShowLoadingDialog();

                            }

                            @Override
                            public void onAfter(@Nullable Bitmap bitmap, @Nullable Exception e) {
                                super.onAfter(bitmap, e);
                                DismissDialog();
                            }
                        });
            }
        });
    }

    public boolean saveBitmap(String picName, Bitmap bm) {

        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath(), picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    public void ShowLoadingDialog() {
        ProgressDialogUtil.showProgressDialog(PicturePreviewActivity.this);
    }

    public void DismissDialog() {
        ProgressDialogUtil.dismiss(PicturePreviewActivity.this);
    }

    private void initData() {
        imageList = getIntent().getStringArrayListExtra(IMAGELIST);
        position = getIntent().getIntExtra(POSITION, 1);
        viewPager.setAdapter(new SamplePagerAdapter(this, imageList));
        viewPager.setCurrentItem(position);
        tabViewpager.setText((position + 1) + "/" + imageList.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabViewpager.setText((position + 1) + "/" + imageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);
    }
}
