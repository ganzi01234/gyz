package com.kaixin.android.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.exceptions.EaseMobException;
import com.kaixin.android.R;
import com.kaixin.android.activity.ChatActivity;
import com.kaixin.android.activity.MainActivity;
import com.kaixin.android.db.SaveConversationDao;
import com.kaixin.android.menu.Home;
import com.kaixin.android.result.ConversationResult;
import com.kaixin.android.utils.AppShortCutUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;

public class MessagePushService extends Service {
	private Notification mNotification = null;
	private NotificationManager mNotifyManager = null;
	private Intent mIntent = null;
	private PendingIntent mPendingIntent = null;
	private int messageNotificationID = 100;
	private NewMessageBroadcastReceiver msgReceiver;
	private Timer timer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		EMChat.getInstance().setDebugMode(false);
		EMChat.getInstance().init(this);
		// AppClassInfoFactory.init(new AppConfig());
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);
		
		mNotification = new Notification(R.drawable.icon, "聊天消息", System.currentTimeMillis());
		mNotification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		long[] vibrate = { 0, 100, 200, 300 };
		mNotification.vibrate = vibrate;

		// 获取到配置options对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		options.setNotificationEnable(true);
		// 设置自定义的文字提示
		options.setNotifyText(new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				// 可以根据message的类型提示不同文字，这里为一个简单的示例
				try {
					return "你的好基友" + message.getStringAttribute("mName") + "发来了一条消息";
				} catch (EaseMobException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
			}

			@Override
			public String onLatestMessageNotify(EMMessage message,
					int fromUsersNum, int messageNum) {
				return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
			}

		});
		
		options.setOnNotificationClickListener(new OnNotificationClickListener() {
			
			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent(MessagePushService.this,
						ChatActivity.class);
				intent.putExtra("email", message.getFrom());
				StorageUtil.saveString(getApplicationContext(), "hasUnreadMsg", "true");
				try {
					SaveConversationDao.getInstance(getApplicationContext())
					.saveConversation(
							new ConversationResult(message
									.getStringAttribute("mName"), message
									.getFrom(), message
									.getStringAttribute("mAvatar")));
					intent.putExtra("name", message.getStringAttribute("mName"));
					intent.putExtra("avatar", message.getStringAttribute("mAvatar"));
				} catch (EaseMobException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*intent.putExtra("isNews", "yes");
				intent.putExtra("isNews", message.getMsgId());*/
				
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if (msgReceiver != null) {
					try {
						unregisterReceiver(msgReceiver);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return intent;
			}
		});
		
		
//		timer = new Timer();
//	    timer.schedule(new RemindTask(), 1* 1000, 4 * 1000);
		
		//app初始化完毕
		EMChat.getInstance().setAppInited();
	}

	class RemindTask extends TimerTask {
	    public void run() {
	        System.out.println("Time is running!!");
	    }
	}


	
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 消息id
			String msgId = intent.getStringExtra("msgid");
			// 发消息的人的username(userid)
			String msgFrom = intent.getStringExtra("from");
			// 消息类型，文本，图片，语音消息等,这里返回的值为msg.type.ordinal()。
			// 所以消息type实际为是enum类型
			int msgType = intent.getIntExtra("type", 0);
			// 消息body，为一个json字符串
			String msgBody = intent.getStringExtra("body");
			Log.d("main", "new message id:" + msgId + " from:" + msgFrom
					+ " type:" + msgType + " body:" + msgBody);

			// 更方便的方法是通过msgId直接获取整个message
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			
			initNotiIntent(message);
			StorageUtil.saveString(getApplicationContext(), "hasUnreadMsg", "true");
			if(!StringUtil.isNull(StorageUtil.getString(MessagePushService.this, "hasUnreadMsg"))){
				Home.mMsgNumber.setVisibility(View.VISIBLE);
			}else{
				Home.mMsgNumber.setVisibility(View.GONE);
			}
			try {
				SaveConversationDao.getInstance(getApplicationContext())
				.saveConversation(
						new ConversationResult(message
								.getStringAttribute("mName"), message
								.getFrom(), message
								.getStringAttribute("mAvatar")));
				mNotification.setLatestEventInfo(MessagePushService.this, "聊天消息",
						"你的好基友" + message.getStringAttribute("mName") + "发来了一条消息", mPendingIntent);
			} catch (EaseMobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mNotifyManager.notify(messageNotificationID, mNotification);
			messageNotificationID = messageNotificationID + 1;
			
			/*intent = new Intent(KXApplication.this,
					ChatActivity.class);
			intent.putExtra("email", message.getFrom() + "@189.com");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			KXApplication.this.startActivity(intent);*/
		}
	}
	
	
	
	@Override
	public void onDestroy() {
		if (msgReceiver != null) {
			try {
				unregisterReceiver(msgReceiver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onDestroy();
	}



	public void initNotiIntent(EMMessage message) {
		// 推送
		mIntent = new Intent(this, ChatActivity.class);
		mIntent.putExtra("email", message.getFrom());
		mIntent.putExtra("isNews", "yes");
		try {
			mIntent.putExtra("name", message.getStringAttribute("mName"));
			mIntent.putExtra("avatar", message.getStringAttribute("mAvatar"));
		} catch (EaseMobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		mPendingIntent = PendingIntent.getActivity(this, messageNotificationID, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
