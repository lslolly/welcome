
package com.example.ls.mywww;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lizeng on 16/2/1.
 */
public class Util {

    /**
     * 获取IMEI 非手机android设备（平板等）获取不到该编号
     *
     * @param context
     * @return
     */
    @Deprecated
    public static String getIMEI(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telMgr.getDeviceId();
    }



    /**
     * 获取Meta数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            return bundle.get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 固定屏幕方向
     *
     * @param activity
     */
    public static void fixedOrientation(Activity activity) {
        switch (activity.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                break;
        }
    }

    /**
     * EditText设置错误信息
     *
     * @param editText  显示错误信息的EditText
     * @param message   显示的错误信息
     * @param editTexts 取消显示的错误信息的ET
     */
    public static void setErrorMessage(EditText editText, String message, EditText... editTexts) {
        if (editText != null && message != null) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.setError(Html.fromHtml("<font color=#ffffff>" + message + "</font>"));
            for (EditText et : editTexts) {
                et.setError(null);
            }
        }


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
     * Returns whether the network is available
     */
    public static boolean isNetworkAvailable(Context context) {

        if (context == null) {
            return false;
        }

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.w("TAG", "couldn't get connectivity manager");
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0, length = info.length; i < length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置TextView上的字体不同颜色
     * 颜色和文本依次出现
     */

    public static Spanned setTextColors(String... text) {
        String fontF = "<font color=\'";
        String fontM = "\'>";
        String fontB = "</font>";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length - 1; i += 2) {
            sb.append(fontF).append(text[i]).append(fontM).append(text[i + 1]).append(fontB);
        }

        return Html.fromHtml(sb.toString());
    }

    private static int actionBarHeight;

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        if (actionBarHeight == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.textAppearanceLarge, typedValue, true);
            int[] attribute = new int[]{android.R.attr.actionBarSize};
            TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
            actionBarHeight = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
            array.recycle();
        }
        return actionBarHeight;
    }

    private static int sysStautsHeight;

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getSysStatusHeight(Context context) {
        if (sysStautsHeight == 0) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sysStautsHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sysStautsHeight;
    }

    /**
     * byte转MB
     *
     * @param b
     * @return
     */
    public static float byte2mb(long b) {
        return b / 1024f / 1024f;
    }

    public static String byte2mbStr(long b) {
        return String.format("%.1f", byte2mb(b)) + "M";
    }

    public static String bStr2mbStr(String bStr) {
        try {
            return String.format("%.1f", byte2mb(Long.parseLong(bStr))) + "M";
        } catch (Exception e) {
            return bStr;
        }
    }

    public static String byte2Speed(long b) {
        BigDecimal filesize = new BigDecimal(b);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 1, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB/s");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 1, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB/s");
    }

    /**
     * bitmap 转换为字节数组
     */
    // 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    public static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
                    80, 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    /**
     * 获取网络状态
     */
    public static int getNetState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return -1;
        } else {
            return networkInfo.getType();
        }

    }

    /**
     * list转jsonArray
     *
     * @param list
     * @return
     */
    public static JSONArray list2Json(List list) {
        JSONArray array = new JSONArray();
        for (Object obj : list) {
            if (obj instanceof Map) {
                JSONObject jsonObject = new JSONObject();
                Map<String, Object> map = (Map) obj;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    try {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                array.put(jsonObject);
            } else {
                array.put(obj);
            }
        }
        return array;
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * 复制文本内容到粘贴板
     */
    public static void copyText(Context context, String content){
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setPrimaryClip(new ClipData("",new String[]{},new ClipData.Item((CharSequence) content)));
        //下面代码同样可以实现复制 只是API已过时 上面为最新
//        android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
//                .getSystemService(Context.CLIPBOARD_SERVICE);
//        cmb.setText(content);
    }
}
