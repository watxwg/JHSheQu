package com.jhcms.waimaiV3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.dialog.PhoneDialog;
import com.jhcms.common.model.WebJsPreviewImage;
import com.jhcms.common.model.WebJson;
import com.jhcms.common.model.WebviewShareModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.WebviewMoreAdapter;
import com.jhcms.waimaiV3.dialog.ShareDialog;
import com.jhcms.waimaiV3.model.ShareItem;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Wyj on 2017/5/9
 * TODO:网页
 */
public class WebViewActivity extends SwipeBaseActivity {
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private static final int REQUEST_FILE_PICKER = 1;
    public static String URL = "WEB_URL";
    public static String TYPE = "TYPE";
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.Progress)
    ProgressBar progressBar;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.seach_iv)
    ImageView seachIv;
    @Bind(R.id.rightTitle)
    TextView rightTitle;
    @Bind(R.id.share_iv)
    ImageView shareIv;
    @Bind(R.id.LoadMore)
    ImageView LoadMore;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.PHone_iv)
    ImageView PHoneIv;
    private PopupWindow mMorePopupWindows;
    private String webUrl;
    private String cookieStr;
    private ValueCallback<Uri> mFilePathCallback4;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private JavaScriptInterface myJavaScriptInterface;
    private String type;
    private PhoneDialog phoneDialog;//打电话
    private ShareDialog dialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case 0:
                        close();
                        break;
                    case 2:
                        String json = (String) msg.obj;
                        Gson gson = new Gson();
                        final WebJson webJson = gson.fromJson(json, WebJson.class);
                        if (webJson.getType().equals("phone")) {
                            PHoneIv.setVisibility(View.VISIBLE);
                            PHoneIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShowPhoneDialog(webJson, true, -1);
                                }
                            });

                        } else if (webJson.getType().equals("search")) {
                            seachIv.setVisibility(View.VISIBLE);
                            seachIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    webView.loadUrl(webJson.getParams().getLink());
                                }
                            });

                        } else if (webJson.getType().equals("text")) {
                            rightTitle.setVisibility(View.VISIBLE);
                            rightTitle.setText(webJson.getText().getTitle());
                            rightTitle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    webView.loadUrl(webJson.getText().getLink());
                                }
                            });
                        } else if (webJson.getType().equals("share")) {
                            shareIv.setVisibility(View.VISIBLE);
                            shareIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShareItem shareItems = new ShareItem();
                                    if (webJson.getParams().getImg() != null) {
                                        shareItems.setLogo(webJson.getParams().getImg());
                                    } else {
                                        shareItems.setREImageRocs( R.mipmap.ic_launcher);
                                    }
                                    if (webJson.getParams().getTitle() != null) {
                                        shareItems.setTitle(webJson.getParams().getTitle());
                                    } else {
                                        shareItems.setTitle(getString(R.string.app_name));
                                    }
                                    shareItems.setDescription(webJson.getParams().getDesc());
                                    shareItems.setUrl(webJson.getParams().getLink());
                                    dialog = new ShareDialog(WebViewActivity.this);
                                    dialog.setItem(shareItems);
                                    dialog.show();
                                }
                            });
                        } else if (webJson.getType().equals("more")) {
                            LoadMore.setVisibility(View.VISIBLE);
                            LoadMore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    View view = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.phone_dialog_layout, null);
                                    ListViewForScrollView mlistview = (ListViewForScrollView) view.findViewById(R.id.Listview);
                                    WebviewMoreAdapter madapter = new WebviewMoreAdapter(webJson, WebViewActivity.this);
                                    mlistview.setAdapter(madapter);
//                                mMorePopupWindows = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                    mMorePopupWindows =new PopupWindow();
                                    mlistview.measure(View.MeasureSpec.UNSPECIFIED,
                                            View.MeasureSpec.UNSPECIFIED);
                                    mMorePopupWindows.setWidth((int) (mlistview.getMeasuredWidth()+mlistview.getMeasuredWidth()*0.5));
                                    mMorePopupWindows.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                                    mMorePopupWindows.setContentView(view);
                                    mMorePopupWindows.setOutsideTouchable(true);
                                    mMorePopupWindows.setBackgroundDrawable(WebViewActivity.this.getResources().getDrawable(
                                            R.drawable.bg_popupwindow));
                                    mMorePopupWindows.setFocusable(true);// 获取焦点
                                    mMorePopupWindows.setClippingEnabled(false);

                                    if (mMorePopupWindows != null && !mMorePopupWindows.isShowing()) {
//                                    mMorePopupWindows.showAtLocation(LoadMore,Gravity.TOP,0,0);
                                        mMorePopupWindows.showAsDropDown(LoadMore,0,0,Gravity.RIGHT);
                                    }


                                    mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                            if (webJson.getParams().getItems().get(position).getType().equals("search")) {
                                                webView.loadUrl(webJson.getParams().getItems().get(position).getParams().getLink());
                                            } else if (webJson.getParams().getItems().get(position).getType().equals("phone")) {
                                                ShowPhoneDialog(webJson, false, position);
                                            } else if (webJson.getParams().getItems().get(position).getType().equals("share")) {
                                                ShareItem shareItems = new ShareItem();
                                                if (webJson.getParams().getItems().get(position).getParams().getImg() != null) {
                                                    shareItems.setLogo(webJson.getParams().getItems().get(position).getParams().getImg());
                                                } else {
                                                    shareItems.setREImageRocs(R.mipmap.ic_launcher);
                                                }
                                                if (webJson.getParams().getItems().get(position).getParams().getTitle() != null) {
                                                    shareItems.setTitle(webJson.getParams().getItems().get(position).getParams().getTitle());
                                                } else {
                                                    shareItems.setTitle(getString(R.string.app_name));
                                                }
                                                shareItems.setDescription(webJson.getParams().getItems().get(position).getParams().getDesc());
                                                shareItems.setUrl(webJson.getParams().getItems().get(position).getParams().getLink());
                                                dialog = new ShareDialog(WebViewActivity.this);
                                                dialog.setItem(shareItems);
                                                dialog.show();
                                            } else if (webJson.getParams().getItems().get(position).getType().equals("text")) {
                                                webView.loadUrl(webJson.getParams().getItems().get(position).getParams().getLink());
                                            }
                                            if (mMorePopupWindows != null && mMorePopupWindows.isShowing()) {
                                                mMorePopupWindows.dismiss();
                                            }

                                        }
                                    });

                                }
                            });


                        }

                        break;
                }
            } catch (Exception e) {

            }

        }
    };


    @Override
    protected void initData() {
        setToolBar(titleLayout);
        webUrl = getIntent().getStringExtra(URL);
//        webUrl = "http://192.168.1.10/demo/appwebview.html";
        type = getIntent().getStringExtra(TYPE);
        ivClose.setVisibility(View.VISIBLE);
        initWebView();
        backIv.setOnClickListener(v -> close());
    }

    private void initWebView() {
        seachIv = (ImageView) WebViewActivity.this.findViewById(R.id.seach_iv);
        initWebSetting();
        setAcceptThirdPartyCookies();
        initWebClient();
        loadUrl();
    }

    /**
     * 加载地址
     */
    private void loadUrl() {
        cookieStr = "KT-TOKEN=" + Api.TOKEN;
        Utils.synCookies(this, webUrl, cookieStr);
        webView.loadUrl(webUrl);
    }

    /**
     * 设置client
     */
    private void initWebClient() {
        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                return webViewJump(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return webViewJump(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            // Android < 3.0 调用这个方法
            public void openFileChooser(ValueCallback<Uri> filePathCallback) {
                mFilePathCallback4 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            // 3.0 + 调用这个方法
            public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
                mFilePathCallback4 = filePathCallback;
                //      Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            // Android > 4.1.1 调用这个方法
            public void openFileChooser(ValueCallback<Uri> filePathCallback, String
                    acceptType, String capture) {
                mFilePathCallback4 = filePathCallback;

                //        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            // For Android > 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }
        });
    }

    /**
     * @param view
     * @param url
     * @return
     */
    private boolean webViewJump(WebView view, String url) {//web 跳转
        if (url.contains("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            startActivity(intent);
            return true;
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieStr = cookieManager.getCookie(url);
        if (!TextUtils.isEmpty(cookieStr) && cookieStr.startsWith("KT-GUID")) {
            cookieStr = "KT-TOKEN=" + Api.TOKEN;
        }
        Utils.synCookies(WebViewActivity.this, url, cookieStr);
        cookieStr = cookieManager.getCookie(url);
        if (Utils.isNative(url)) {
            Utils.toNative(WebViewActivity.this, url);
            return true;
        }
        view.loadUrl(url);
        return true;
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    /**
     * 设置WebSetting
     */
    private void initWebSetting() {
        WebSettings settings = webView.getSettings();
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "com.jhcms.android");
        settings.setJavaScriptEnabled(true);
        settings.getAllowFileAccess();
        settings.getDatabaseEnabled();
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        myJavaScriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(myJavaScriptInterface, "JHAPP");
    }

    /**
     * 设置跨域cookie读取
     */
    public final void setAcceptThirdPartyCookies() {
        //target 23 default false, so manual set true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(type) && type.equals("splash")) {
            startActivity(new Intent(WebViewActivity.this, WaiMaiMainActivity.class));
        }
        this.finish();
    }

    //    图库回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        String mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        if (requestCode == REQUEST_FILE_PICKER) {
            if (null == mFilePathCallback4) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result == null && data == null && resultCode == Activity.RESULT_OK) {
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            mFilePathCallback4.onReceiveValue(result);
            mFilePathCallback4 = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 打电话dialog 功能框
     *
     * @param webJson
     */
    private void ShowPhoneDialog(WebJson webJson, boolean flag, int postion) {
        if (flag) {
            phoneDialog = new PhoneDialog(WebViewActivity.this, webJson.getParams().getPhone());
        } else {
            phoneDialog = new PhoneDialog(WebViewActivity.this, webJson.getParams().getItems().get(postion).getParams().getPhone());
        }
        phoneDialog.setCancelable(true);
        if (phoneDialog != null && !phoneDialog.isShowing()) {
            phoneDialog.show();
        }
    }

    /**
     * 实现WebView的回退栈
     *
     * @return
     */
    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (!TextUtils.isEmpty(type) && type.equals("splash")) {
                startActivity(new Intent(WebViewActivity.this, WaiMaiMainActivity.class));
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class JavaScriptInterface {

        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        //  界面跳转
        @JavascriptInterface
        public void goBack() {
            Message msg = new Message();
            msg.what = 0;
            mHandler.sendMessage(msg);
        }


        //  界面跳转
        @JavascriptInterface
        public void onContextMenu(String json) {
            Message message = Message.obtain();
            message.what = 2;
            message.obj = json;
            mHandler.sendMessage(message);

        }
        //  分享
        @JavascriptInterface
        public void onShare(String json,Object object) {
          if(json.contains("{")){
              Gson gson=new Gson();
              WebviewShareModel mmodel=gson.fromJson(json,WebviewShareModel.class);
              ShareItem shareItems = new ShareItem();
              if (mmodel.getImg() != null) {
                  shareItems.setLogo(mmodel.getImg());
              } else {
                  shareItems.setREImageRocs(R.mipmap.ic_launcher);
              }
              if (mmodel.getTitle() != null) {
                  shareItems.setTitle(mmodel.getTitle());
              } else {
                  shareItems.setTitle(getString(R.string.app_name));
              }
              shareItems.setDescription(mmodel.getDesc());
              shareItems.setUrl(mmodel.getLink());
              dialog = new ShareDialog(WebViewActivity.this);
              dialog.setItem(shareItems);
              dialog.show();

          }else {
              ShareItem shareItems = new ShareItem();
              shareItems.setREImageRocs(R.mipmap.ic_launcher);
              shareItems.setTitle(getString(R.string.app_name));
              shareItems.setDescription(json);
              dialog = new ShareDialog(WebViewActivity.this);
              dialog.setItem(shareItems);
              dialog.show();

          }

        }


        //  分享
        @JavascriptInterface
        public void onShare(String json) {
            if(json.contains("{")){
                Gson gson=new Gson();
                WebviewShareModel mmodel=gson.fromJson(json,WebviewShareModel.class);
                ShareItem shareItems = new ShareItem();
                if (mmodel.getImg() != null) {
                    shareItems.setLogo(mmodel.getImg());
                } else {
                    shareItems.setREImageRocs(R.mipmap.ic_launcher);
                }
                if (mmodel.getTitle() != null) {
                    shareItems.setTitle(mmodel.getTitle());
                } else {
                    shareItems.setTitle(getString(R.string.app_name));
                }
                shareItems.setDescription(mmodel.getDesc());
                shareItems.setUrl(mmodel.getLink());
                dialog = new ShareDialog(WebViewActivity.this);
                dialog.setItem(shareItems);
                dialog.show();

            }else {
                ShareItem shareItems = new ShareItem();
                shareItems.setREImageRocs(R.mipmap.ic_launcher);
                shareItems.setTitle(getString(R.string.app_name));
                shareItems.setDescription(json);
                shareItems.setUrl(Api.APP_URL);
                dialog = new ShareDialog(WebViewActivity.this);
                dialog.setItem(shareItems);
                dialog.show();

            }

        }




        /**
         * 图片预览器
         *
         * @param json
         */
        @JavascriptInterface
        public void previewImage(String json) {
            Gson gson = new Gson();
            WebJsPreviewImage mmodel = gson.fromJson(json, WebJsPreviewImage.class);
            ArrayList<String> mdatalist = new ArrayList<>();
            for (int i = 0; i < mmodel.getItems().size(); i++) {
                mdatalist.add(mmodel.getItems().get(i).getImg());
            }
            Intent intent = new Intent(WebViewActivity.this, PicturePreviewActivity.class);
            intent.putExtra(PicturePreviewActivity.POSITION, mmodel.getIndex());
            intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, mdatalist);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(webView,
                    webView.getWidth() / 2, webView.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(WebViewActivity.this, intent,
                    compat.toBundle());

        }

    }
}
