package com.seable.potato.ui.activity;//package com.seable.withome.property.ui.activity;
//
//import android.app.AlertDialog;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;
//import cn.jpush.android.api.InstrumentedActivity;
//
//import com.leo.base.util.L;
//import com.seable.withome.property.R;
//import com.seable.withome.property.data.entity.Entity_PushMessage;
//import com.seable.withome.property.provider.PushWs;
//import com.seable.withome.property.provider.PushWs.MessageType;
//import com.seable.withome.property.provider.PushWs.ThreadTable;
//import com.seable.withome.property.util.GsonUtils;
//import com.seable.withome.property.util.JPushUtil;
//import com.seable.withome.property.util.PreferencesService;
//
///**
// * @ClassName: JPushActivity
// * @Description: 进入应用最先跳转的界面，需要继承InstrumentedActivity，做推送的初始化
// * @author 王维玉
// * @date 2015-01-21 09:18
// * 
// */
//public class Activity_JPush extends InstrumentedActivity {
//	public static boolean isForeground = false;
//	private static Context context;
//
//	private static long lastReveMsgTime = 0;
//	private static long mActiveBuddyId = -1;
//	/**
//	 * 是状态栏通知的管理类，负责发通知、clear通知等。
//	 * NotificationManager是一个系统Service，必须通过getSystemService()方法来获取
//	 */
//	private static NotificationManager mNm;
//	private static PendingIntent mPendingIntent = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		context = Activity_JPush.this;
//		initView();
//	}
//
//	private void initView() {
//		mNm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		JPushUtil.jpushInit(context);
//		registerMessageReceiver(); // used for receive msg
//		// for (int i = 18; i < 20; i++) {
//		// PushMessage msg = new PushMessage();
//		// msg.setCreate_date("2010011" + i);
//		// msg.setTitle("标题" + i);
//		// msg.setContent("内容内容内容内容内容内容内容内容内容内容内容" + i);
//		// msg.setCategory("类型" + i);
//		// msg.setSource("来源" + i);
//		// notifyPushMessage(msg);
//		// String message = "{\"create_date\":\"" + msg.getCreate_date()
//		// + "\",\"title\":\"" + msg.getTitle() + "\",\"content\":\""
//		// + msg.getContent() + "\",\"category\":\""
//		// + msg.getCategory() + "\",\"source\":\"" + msg.getSource()
//		// + "\"}";
//		// insertPushMessage(message, msg);
//		// }
//		finish();
//	}
//
//	// for receive customer msg from jpush server
//	private MessageReceiver mMessageReceiver;
//	public static final String MESSAGE_RECEIVED_ACTION = "com.seable.withome.MESSAGE_RECEIVED_ACTION";
//	public static final String KEY_TITLE = "title";
//	public static final String KEY_MESSAGE = "message";
//	public static final String KEY_EXTRAS = "extras";
//
//	public void registerMessageReceiver() {
//		mMessageReceiver = new MessageReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//		filter.addAction(MESSAGE_RECEIVED_ACTION);
//		registerReceiver(mMessageReceiver, filter);
//
//	}
//
//	@Override
//	protected void onResume() {
//		isForeground = true;
//		super.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//		isForeground = false;
//		super.onPause();
//	}
//
//	@Override
//	protected void onDestroy() {
//		unregisterReceiver(mMessageReceiver);
//		super.onDestroy();
//	}
//
//	public class MessageReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//				String messge = intent.getStringExtra(KEY_MESSAGE);
//				String extras = intent.getStringExtra(KEY_EXTRAS);
//				StringBuilder showMsg = new StringBuilder();
//				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//				if (!JPushUtil.isEmpty(extras)) {
//					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//				}
//				L.e("showMsg"+showMsg.toString());
//				L.e("messge"+messge);
//				L.e("extras"+extras);
//				setCostomMsg(messge);
//			}
//		}
//	}
//
//	private void setCostomMsg(final String message) {
//		 new AlertDialog.Builder(context)
//		 .setIcon(R.drawable.ic_launcher)
//		 .setTitle("自定义消息")
//		 .setMessage( message)
//		 .setNegativeButton("确定",
//		 new DialogInterface.OnClickListener() {
//		
//		 @Override
//		 public void onClick(DialogInterface dialog,
//		 int which) {
//						Entity_PushMessage msg = null;
//						msg = (Entity_PushMessage) GsonUtils.resolveGsonData(
//								message, msg, Entity_PushMessage.class);
//						notifyPushMessage(msg);
//		 dialog.dismiss();
//		 }
//		 }).show();
////		insertPushMessage(message, msg);
//	}
//
//	/** 将推送消息插入到数据库中 */
//	private static void insertPushMessage(final String json,
//			final Entity_PushMessage msg) {
//		ContentValues cv = new ContentValues();
//		cv.put(PushWs.MessageTable.ACCOUNT_NAME, "me");
//		// cv.put(PushWs.MessageTable.SENDER_ID,
//		// msg.getSubscribe().getSubId());
//		cv.put(PushWs.MessageTable.SENDER_NAME, msg.getSource());
//		cv.put(PushWs.MessageTable.BODY, json);
//		cv.put(PushWs.MessageTable.IS_INBOUND, PushWs.MessageType.INCOMING);
//		int msgType = 1;
//		cv.put(PushWs.MessageTable.TYPE, msgType);
//		cv.put(PushWs.MessageTable.SENT_TIME, msg.getCreate_date());
//		cv.put(PushWs.MessageTable.RECEIVED_TIME, System.currentTimeMillis());
//		cv.put(PushWs.MessageTable.BUDDY_ID, 0);
//		context.getContentResolver()
//				.insert(PushWs.MessageTable.CONTENT_URI, cv);
//		PreferencesService service = new PreferencesService(context);
//		service.saveNoticeNum(service.getNoticeNum() + 1);
//	}
//
//	/** 将推送消息弹出通知 */
//	private static void notifyPushMessage(final Entity_PushMessage msg) {
//		L.e("msg.getMsgtype()====", msg.getCategory() + "");
//		PreferencesService service = new PreferencesService(context);
//		if (mActiveBuddyId == -1) {
//
//			// if (ChatNotifyUtils.messageNotifyEnable(context, name)) {
//			String ticker = msg.getTitle();
//			String summery = msg.getContent();
//			NotificationCompat.Builder builder = new NotificationCompat.Builder(
//					context);
//			builder.setAutoCancel(true);
//			builder.setSmallIcon(R.drawable.icon_app_logo);
//			builder.setTicker(ticker);
//			builder.setContentTitle(ticker);
//			builder.setContentText(summery);
//			Intent intent = null;
//			intent = Activity_Notification_List.getIntent(context,
//					msg.getTitle());
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			mPendingIntent = PendingIntent.getActivity(context, 0, intent,
//					PendingIntent.FLAG_CANCEL_CURRENT);
//			builder.setContentIntent(mPendingIntent);
//			service.saveApkNotificationFrom(true);
//			// 设置通知属性
//			boolean isSound = service.getSoundState();
//			boolean isShake = service.getShakeState();
//			if (isSound && isShake) {
//				builder.setDefaults(Notification.DEFAULT_LIGHTS
//						| Notification.DEFAULT_VIBRATE
//						| Notification.DEFAULT_SOUND);
//			} else {
//				if (isSound) {
//					builder.setDefaults(Notification.DEFAULT_SOUND
//							| Notification.DEFAULT_LIGHTS);
//				} else if (isShake) {
//					builder.setDefaults(Notification.DEFAULT_VIBRATE
//							| Notification.DEFAULT_LIGHTS);
//				} else {
//					builder.setDefaults(Notification.DEFAULT_LIGHTS);
//				}
//			}
//
//			long now_time = System.currentTimeMillis();
//			if ((now_time - lastReveMsgTime) > 2000) {
//				lastReveMsgTime = now_time;
//
//			} else {
//				builder.setDefaults(Notification.DEFAULT_LIGHTS);
//			}
//			Notification notification = builder.build();
//			mNm.notify(R.string.app_name, notification);
//			// }
//		} else {
//			service.saveNoticeNum(0);
//			ContentValues cv = new ContentValues();
//			cv.put(PushWs.ThreadTable.UNREAD_COUNT, 0);
//			context.getContentResolver().update(
//					ThreadTable.CONTENT_URI,
//					cv,
//					ThreadTable.ACCOUNT_NAME + "=? and " + ThreadTable.BUDDY_ID
//							+ "=? " + "and " + ThreadTable.MSG_TYPE + " in "
//							+ MessageType.MSG_TYPE_CHAT,
//					new String[] { "me", String.valueOf(mActiveBuddyId) });
//		}
//	}
//
//}
