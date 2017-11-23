package com.shinaier.laundry.snlfactory.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.common.utils.LogUtil;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 公共类
 */
public class CommonTools {


	private static SharedPreferences app_search_preferences = null;
	private static int MAX_HISTORY_NUM=15;
	/**
	 * 判断手机是否有SD卡
	 * */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	
	/**
	 * View转换Bitmap
	 */
	public static Bitmap convertViewToBitmap(View view){
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	/**
	 * Bitmap转换成File
	 * @param mContext
	 * @param bitmap
	 * @return
	 */
	public static File convertBitmap2File(Context mContext, Bitmap bitmap){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		if (bitmap != null){
			bitmap.compress(Bitmap.CompressFormat.PNG, 100,os);
		}
		byte[] bytes = os.toByteArray();

		File file  = new File(mContext.getCacheDir(),"temp" + new Date().getTime() + ".jpg");
		try {
			if(bytes != null){
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes,0,bytes.length);
				fos.flush();
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 跳转应用市场
	 * @param paramContext
	 * @return
	 */
	public static Intent getIntent(Context paramContext) {
		StringBuilder localStringBuilder = new StringBuilder()
				.append("market://details?id=");
		String str = paramContext.getPackageName();
		localStringBuilder.append(str);
		Uri localUri = Uri.parse(localStringBuilder.toString());
		return new Intent("android.intent.action.VIEW", localUri);
	}

	
	/**
     * install package normal by system intent
     * 
     * @param context
     * @param filePath file path of package
     * @return
     */
    public static void installNormal(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return;
        }

        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    
    /**获取本机MAC地址*/
	public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();  
    }  
	
	/**获取本地IP地址*/
	public static String getLocalIpAddress() {
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {  
                        return inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {
            LogUtil.e("WifiPreference IpAddress", ex.toString());
        }  
        return null;  
    } 

	public static void saveSearchHistory(String name, String shareKey, Activity activity){
		if(name!=null&&name.trim().length()>0){
			CommonTools.app_search_preferences = activity.getSharedPreferences("app_search_preferences", activity.MODE_PRIVATE);

			String history = CommonTools.app_search_preferences.getString(shareKey, "");
			
				String[] results = history.split(",");
				boolean contain = CommonTools.isContain(name,results);
				if(!contain){
					if(results!=null&&results.length>=MAX_HISTORY_NUM){
						history = history.substring(history.indexOf(",") + 1);
					}
					StringBuilder sb = new StringBuilder(history);
					sb.append(name.trim() + ",");
					CommonTools.app_search_preferences.edit().putString(shareKey, sb.toString()).commit();
				}
		}
	}
	public static boolean isContain(String msg, String[] results){
		if (results!=null&&results.length>0) {
			for (int i = 0; i < results.length; i++) {
				if(results[i].equals(msg.trim())){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**检查手机号是否合法*/
	public static boolean checkPhoneNum(String phoneNum) {

		boolean flag = false;
		try {
			if(!TextUtils.isEmpty(phoneNum)){
				String check = "^[1][3-8]\\d{9}$";
				boolean matches = Pattern.compile(check).matcher(phoneNum).matches();
				flag = matches;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 获取当前应用程序的版本名称
	 * 
	 * @return
	 */
	public static String getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			// can't reach
			return "";
		}

	}
	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			// can't reach
			return 0;
		}
	}
	
	/**
	 * 
	 * 获取时间格式
	 *  @return
	 */
	
	public static String getDateString(long time) {
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 检查手机号码
	 * @param context
	 * @param isAddMember 是否是创建会员的标识
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkPhoneNumber(Context context, boolean isAddMember, String phoneNum) {
		if (StringUtil.isEmpty(phoneNum)){
			if (isAddMember){
				ToastUtil.shortShow(context, "会员不存在，请输入手机号!");
			}else {
				ToastUtil.shortShow(context, "请输入手机号!");
			}
			return false;
		} else if(!"1".equals(phoneNum.trim().substring(0,1))) {
			if (isAddMember){
				ToastUtil.shortShow(context, "会员不存在，请输入正确手机号！");
			}else {
				ToastUtil.shortShow(context, "请输入正确手机号");
			}
			return false;
		} else if(phoneNum.trim().length() != 11){
			if (isAddMember){
				ToastUtil.shortShow(context, "会员不存在，请输入正确手机号！");
			}else {
				ToastUtil.shortShow(context, "请输入正确手机号");
			}
			return false;
		} else if(!CommonTools.checkPhoneNum(phoneNum)){
			if (isAddMember){
				ToastUtil.shortShow(context, "会员不存在，请输入正确手机号！");
			}else {
				ToastUtil.shortShow(context, "请输入正确手机号");
			}
			return false;
		}
		return true;
	}

	public static void StringInterceptionChangeRed(TextView numtext,
                                                   String string, String string2, String string3) {
		int fstart = string.indexOf(string2);
		int fend = fstart + string2.length();
		SpannableStringBuilder style = new SpannableStringBuilder(string);
		if(!"".equals(string3)&&string3!=null){
			int bstart=string.indexOf(string3);
			int bend=bstart+string3.length();
			style.setSpan(new ForegroundColorSpan(Color.RED), bstart, bend,
					Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}

		style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		numtext.setText(style);
	}

	public static void StringInterceptionChangeGreen(Context context, TextView numtext,
                                                     String string, String string2, String string3) {
		int fstart = string.indexOf(string2);
		int fend = fstart + string2.length();
		SpannableStringBuilder style = new SpannableStringBuilder(string);
		if(!"".equals(string3)&&string3!=null){
			int bstart=string.indexOf(string3);
			int bend=bstart+string3.length();
			style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.base_color)), bstart, bend,
					Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}

		style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.base_color)), fstart, fend,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		numtext.setText(style);
	}
}
