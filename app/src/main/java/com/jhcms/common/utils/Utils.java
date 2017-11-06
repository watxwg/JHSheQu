package com.jhcms.common.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.run.activity.RunMainActivity;
import com.jhcms.shequ.activity.LoginActivity;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.tuangou.activity.TuanSearchGoodsActivity;
import com.jhcms.tuangou.activity.TuanShopDetailActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiBusinessListActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.WaiMaiMainActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.jhcms.waimaiV3.dialog.CallDialog;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/23.
 */
public class Utils {

    public static boolean isEmpty(String paramString) {
        return (paramString == null) || (paramString.trim().length() <= 0);
    }


    /**
     * 检查是否有可用网络
     *
     * @param context 上下文环境
     * @return 有可用网络返回true 否则返回false
     */
    public static boolean isHasNet(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();// 获取联网状态网络
        if (info == null || !info.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    public static void LoadUrlImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }


    /**
     * @param context
     * @return int
     * @throws
     * @author chenzheng
     * @Description: 获取屏幕宽度
     * @since 2014-5-9
     */
    public static int getScreenW(Context context) {
        return getScreenSize(context, true);
    }

    /**
     * @param context
     * @return int
     * @throws
     * @author chenzheng
     * @Description: 获取屏幕高度
     * @since 2014-5-9
     */
    public static int getScreenH(Context context) {
        return getScreenSize(context, false);
    }

    private static int getScreenSize(Context context, boolean isWidth) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return isWidth ? dm.widthPixels : dm.heightPixels;
    }


    /**
     * @param timeStamp
     * @param format    yyyy-MM-dd HH:mm:ss
     * @return String
     * @throws
     * @author chenzheng
     * @Description: 将时间戳转化为时间字符串
     * @since 2014-5-26
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDate(String timeStamp, String format) {
        if (Utils.isEmpty(timeStamp)) {
            return "";
        }
        if (Utils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long lcc_time = Long.valueOf(timeStamp);
        String newDate = sdf.format(new Date(lcc_time * 1000L));
        return newDate;
    }


    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String convertTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 高德转百度
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gd_To_Bd(double lat, double lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat, tempLon};
        return gps;
    }


    /**
     * @return true
     */
    public static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public static String saveCrashInfo2File(Exception ex) {
        File file;
        StringBuffer sb = new StringBuffer();

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis() / 1000;
            String fileName = "异常-" + Utils.convertDate(timestamp, "yyyy-MM-dd HH:mm:ss");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/外卖2.0错误日志/";
                file = new File(path);
                file = FileUtils.createFile(path, "异常" + System.currentTimeMillis() / 1000, sb.toString());
            }
            return fileName;
        } catch (Exception e) {
            LogUtil.e("an error occured while writing file..." + e);
        }
        return null;
    }


    public static void LoadPicture(Context context, Integer path, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(path)
                .placeholder(R.mipmap.home_banner_default) //占位图
                .error(R.mipmap.home_banner_default)  //出错的占位图
                .centerCrop()
                .fitCenter()
                .into(imageView);
    }

    /**
     * 通过返回链接加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void LoadStrPicture(Context context, String path, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(path)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.home_banner_default) //占位图
                .error(R.mipmap.home_banner_default)  //出错的占位图
                .into(imageView);
    }

    /**
     * 通过URI加载图片
     *
     * @param imageView
     */
    public static void LoadRoundPicture(Context mContext, String path, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(path)
                .centerCrop()
                .thumbnail(0.6f)
                .crossFade()
                .placeholder(R.mipmap.home_banner_default)
                .error(R.mipmap.home_banner_default)
                .transform(new GlideRoundTransform(mContext, 5))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void LoadCircslPicture(Context mContext, String path, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(path)
                .centerCrop()
                .thumbnail(0.6f)
                .crossFade()
                .placeholder(R.mipmap.home_banner_default)
                .error(R.mipmap.home_banner_default)
                .transform(new GlideCircleTransform(mContext, 1, R.color.round))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(14[5-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
//        return m.matches();
        return true;
    }


    public static void showMissingPermissionDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private static void startAppSettings(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        context.startActivity(intent);
    }

    /**
     * 格式化时间
     *
     * @param timeStamp
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDate(long timeStamp, String format) {
        if (Utils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String newDate = sdf.format(new Date(timeStamp * 1000L));
        return newDate;
    }

    public static void GoLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void synCookies(Context context, String url, String cookies) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();
            String[] split = cookies.split(";");
            for (int i = 0; i < split.length; i++) {
                String cookie = split[i] + "; path=/" + "; domain=" + Api.URL + ";isSecure=FALSE";
//                String cookie = split[i] + "; path=/" + "; domain=" + ".o2o.jhcms.cn" + ";isSecure=FALSE";
                LogUtil.e("发送Cookies的链接=" + url);
                LogUtil.e("发送的Cookies=" + cookie);
                cookieManager.setCookie(url, cookie);
            }
            CookieSyncManager.getInstance().sync();

        } catch (Exception e) {
            LogUtil.e("Exception" + e.toString());
            ToastUtil.show(e.toString());
        }

    }

    /**
     * 获取虚拟键的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }


    /**
     * 设置RecyclerView 的分割线
     *
     * @param context
     * @param heightResource
     * @param colorRresource
     * @return
     */
    static DividerListItemDecoration divider;

    public static DividerListItemDecoration setDivider(Context context, int heightResource, int colorRresource) {
        if (null == divider) {
            divider = new DividerListItemDecoration.Builder(context)
                    .setHeight(heightResource)
                    .setColorResource(colorRresource)
                    .build();
        }
        return divider;
    }


    /**
     * 是否调到原生界面
     *
     * @param link
     * @return
     */
    public static boolean isNative(String link) {
        if (!TextUtils.isEmpty(link) && link.contains(Api.URL)) {
            if (link.contains("tuan")) {
               /*团购链接*/
                if (link.contains("shop/detail-") || link.contains("product/detail-") || link.contains("shop/index")) {
                    return true;
                }
            } else if (link.contains("waimai")) {
                 /*外卖链接*/
                if (link.contains("/shoplist/index") || link.contains("/shop/detail-")||link.contains("paotui/index")) {
                    return true;
                }
            } else if (link.contains("passport/login")) {
                /*登录界面*/
                return true;
            }
        }
        return false;
    }


    /**
     * 适用于WebView以外的内部跳转
     *
     * @param context
     * @param link
     * @param type
     */
    public static void dealWithHomeLink(Context context, String link, String type) {
        if (!TextUtils.isEmpty(link)) {
            if (isNative(link)) {
                toNative(context, link);
            } else {
                Intent intent = new Intent();
                intent.setClass(context, WebViewActivity.class);
                if (!TextUtils.isEmpty(type)) {
                    intent.putExtra(WebViewActivity.TYPE, type);
                }
                intent.putExtra(WebViewActivity.URL, link);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 适用于WebView内部跳转
     *
     * @param context
     * @param link
     */
    public static void toNative(Context context, String link) {
        Intent intent = new Intent();
        if (link.contains("tuan")) {
            /*团购链接*/
            if (link.contains("shop/detail-")) {
                /**
                 * "http://tuan.o2o.jhcms.cn/shop/detail-164.html"
                 * 商家详情
                 * */
                intent.setClass(context, TuanShopDetailActivity.class);
                String[] shoplist = link.split("shop/detail-");
                String str = shoplist[1];/*1.html*/
                String[] shopId = str.split("[.]");
                String shop_id = shopId[0];
                intent.putExtra(TuanShopDetailActivity.SHOP_ID, shop_id);
            } else if (link.contains("product/detail-")) {
                /**
                 * http://tuan.o2o.jhcms.cn/product/detail-19.html
                 *商品详情
                 * */
                intent.setClass(context, TuanProductDetailsActivity.class);
                String[] shoplist = link.split("product/detail-");
                String str = shoplist[1];/*19.html*/
                String[] tuan_Id = str.split("[.]");
                String tuan_id = tuan_Id[0];
                intent.putExtra(TuanProductDetailsActivity.TUAN_ID, tuan_id);
            } else if (link.contains("shop/index")) {
                /**
                 * http://tuan.o2o.jhcms.cn/shop/index.html?cat_id=4
                 * http://tuan.o2o.jhcms.cn/shop/index/?cat_id=1
                 * http://tuan.o2o.jhcms.cn/shop/index.html
                 * 商家商品分类列表
                 * */
                intent.setClass(context, TuanSearchGoodsActivity.class);
                /*获取cat_id*/
                String[] shoplist = link.split("[?]");
                String str = shoplist[shoplist.length - 1];/*cat_id=1 或 null*/
                if (str.contains("cat_id")) {
                    String[] catId = str.split("=");
                    String cat_id = catId[catId.length - 1];
                    if (cat_id.contains("cat_id")) {
                        cat_id = "";
                    }
                    intent.putExtra(TuanSearchGoodsActivity.CATE_ID, cat_id);
                } else {
                    intent.putExtra(TuanSearchGoodsActivity.CATE_ID, "");
                }
            }
        } else if (link.contains("waimai")) {
            /*外卖链接*/
            if (link.contains("/shoplist/index")) {
                /**
                 * http://waimai.o2o.jhcms.cn/shoplist/index-1.html
                 * 跳转到商家列表
                 * */
                intent.setClass(context, WaiMaiBusinessListActivity.class);
                if (link.contains("/shoplist/index-")) {
                    /*获取cat_id*/
                    String[] shoplist = link.split("shoplist/index-");
                    String str = shoplist[1];/*1.html*/
                    String[] catId = str.split("[.]");
                    String cat_id = catId[0];
                    intent.putExtra(WaiMaiBusinessListActivity.CAT_ID, cat_id);
                }
            } else if (link.contains("/shop/detail-")) {
                /**
                 * http://waimai.o2o.jhcms.cn/shop/detail-1.html
                 * 跳到店铺详情页
                 * */
                intent.setClass(context, WaiMaiShopActivity.class);
                String[] shoplist = link.split("shop/detail-");
                String str = shoplist[1];/*1.html*/
                String[] shopId = str.split("[.]");
                String shop_id = shopId[0];
                intent.putExtra(WaiMaiShopActivity.SHOP_ID, shop_id);
            } else if (link.contains("waimai/index.html")) {
                intent.setClass(context, WaiMaiMainActivity.class);
            }else if (link.contains("paotui/index")) {
                intent.setClass(context, RunMainActivity.class);
            }
        }  else if (link.contains("passport/login")) {
            intent.setClass(context, LoginActivity.class);
        }
        context.startActivity(intent);
    }

    /**
     * 将手机号中间隐藏为 *
     *
     * @param mobile
     * @return
     */
    public static String dealWithPhone(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "";
        } else {
            if (mobile.length() > 6) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mobile.length(); i++) {
                    char c = mobile.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                return sb.toString();
            } else {
                return mobile;
            }
        }
    }


    /**
     * 判断是否获取通知栏权限
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 跳到通知栏设置界面
     *
     * @param context
     */
    public static void toSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * @param context
     * @param link    网页链接
     * @return 跳转的intent
     */
    @NonNull
    public static Intent getMallSwitchIntent(Context context, String link) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL, link);
        return intent;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = MyApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param context
     * @param resourceName 包名
     * @return 资源id
     */
    public static int getMipmapId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "mipmap");
    }

    private static int getIdentifierByType(Context context, String resourceName, String defType) {
        return context.getResources().getIdentifier(resourceName,
                defType,
                context.getPackageName());
    }

    public static void callPhone(Context context, String phone) {
        SwipeBaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
            @Override
            public void onGranted() {
                new CallDialog(context)
                        .setMessage(phone)
                        .setTipTitle("拨打商家电话")
                        .setPositiveButton("确定", v -> {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                    .parse("tel:" + phone));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            context.startActivity(intent);
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }
}
