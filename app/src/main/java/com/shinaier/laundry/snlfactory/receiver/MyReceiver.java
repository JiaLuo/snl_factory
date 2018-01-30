package com.shinaier.laundry.snlfactory.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.common.utils.DeviceUtil;
import com.common.utils.LogUtil;
import com.common.utils.PreferencesUtils;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.main.ui.LauncherActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by me on 6/5/15.
 * 极光推送消息通知接收
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "zhang";
	public static final int FROM_JPUSH = 0x1;

//	private CollectClothesDialog confirmDialog;

	@Override
	public void onReceive(final Context context, Intent intent) {
		String registrationID = JPushInterface.getRegistrationID(context);
		LogUtil.e(TAG,"registrationID = " + registrationID);
		try {
			Bundle bundle = intent.getExtras();
			LogUtil.e("", "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				LogUtil.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//				JPushInterface.requestPermission(context);
				processCustomMessage(context, bundle);
//				if (bundle != null){
//					LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的通知");
//					int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//					LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
////					processCustomMessage(context,bundle,notifactionId);
//					LogUtil.e(TAG,"ACTION_NOTIFICATION_RECEIVED title = " + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
//					LogUtil.e(TAG,"ACTION_NOTIFICATION_RECEIVED content = " + bundle.getString(JPushInterface.EXTRA_ALERT));
//					LogUtil.e(TAG,"ACTION_NOTIFICATION_RECEIVED extras  = " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//
//
				// TODO: 2017/9/9  后台加字段判断是否推送的是最新的消息 然后弹起弹窗

//					// 获得广播发送的数据
//					if (DeviceUtil.isAppOnForeground(context)){
//
//						final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//						dialogBuilder.setTitle("提示");
//						dialogBuilder.setMessage("您有新的订单，是否查看？");
//						dialogBuilder.setCancelable(false);
//						dialogBuilder.setNegativeButton("取消", null);
//						dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Intent intent1 = new Intent(context, LauncherActivity.class);
//								intent1.putExtra("extra_from",LauncherActivity.APP_ON_FOREGROUND_EXTRAS);
//								intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//								context.startActivity(intent1);
////								PreferencesUtils.putBoolean(context,"is_click",true);
//							}
//						});
//
//						AlertDialog alertDialog = dialogBuilder.create();
//						alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//
//						alertDialog.show();
//					}
//				}

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 用户点击打开了通知");
				if (DeviceUtil.isAppOnForeground(context)){
					//用户使用app时收到通知点击通知栏的通知
					Intent intent1 = new Intent(context, LauncherActivity.class);
					intent1.putExtra("extra_from", LauncherActivity.APP_ON_FOREGROUND_EXTRAS);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(intent1);


				}else {
					//用户没有使用app时收到通知点击通知栏的通知
					Intent i = new Intent(context, LauncherActivity.class);
					i.putExtra("extra_from",FROM_JPUSH);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(i);
				}

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				LogUtil.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				LogUtil.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}


	}

	private void processCustomMessage(Context context, Bundle bundle){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		LogUtil.e("zhang","extras = " +extras);
		if (!TextUtils.isEmpty(extras)) {
			try {
				JSONObject extraJson = new JSONObject(extras);
				if (null != extraJson && extraJson.length() > 0) {
					String sound = extraJson.getString("sound");
					if("new_orders.wav".equals(sound)) {
						notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.new_orders));
					}else if ("will_overtime.wav".equals(sound)){
						notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.will_overtime));
					}else {
						notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.overtime));
					}
				}
			} catch (JSONException e) {
			}
		}


//		Intent mIntent = new Intent(context,LauncherActivity.class);
//		bundle.putInt("extra_from",LauncherActivity.APP_ON_FOREGROUND_EXTRAS);
//		mIntent.putExtras(bundle);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
//		notification.setContentIntent(pendingIntent);
		notificationManager.notify(2, notification.build());
	}


	private void processCustomMessage(Context context, Bundle bundle, boolean openApp) {
		/*if (openApp) {
			Intent i = new Intent(context, MainActivity.class);
			i.putExtras(bundle);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		if (TextUtils.isEmpty(message)) {
			message = bundle.getString(JPushInterface.EXTRA_ALERT);
		}
		RetVal<PaymentOrder> messageObj = null;
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

		if (!TextUtils.isEmpty(extras)) {
			try {
				JSONObject extraJson = new JSONObject(extras);
				if (extraJson.length() > 0) {
					messageObj = new RetVal<>();
					messageObj.Code = 0;
					messageObj.Msg = message;
					messageObj.Toast = message;
					messageObj.Val = new PaymentOrder();
					messageObj.Val.Paymentno = extraJson.getString("pn");
					messageObj.Val.Amount = extraJson.getDouble("am");
					messageObj.Val.MerId = extraJson.getLong("mi");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (messageObj != null) {
			EventBus.getDefault().post(new Events.EventPaymentOrder("", messageObj));
		}*/
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					LogUtil.e(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();
					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					LogUtil.e(TAG, "Get message extra JSON error!");
				}
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}
