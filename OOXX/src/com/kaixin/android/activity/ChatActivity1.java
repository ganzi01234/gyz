package com.kaixin.android.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.LocationMessageBody;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VoiceMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.exceptions.EaseMobException;
import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.ChatResult;
import com.kaixin.android.service.MessagePushService;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.PhotoUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 聊天类
 * 
 * @author gyz
 * 
 */
public class ChatActivity1 extends KXActivity {
	private static final int REQUEST_CODE_PICK_PICTURE = 1;
	private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CONTEXT_MENU = 3;
	private static final int REQUEST_CODE_MAP = 4;
	public static final int REQUEST_CODE_TEXT = 5;
	public static final int REQUEST_CODE_VOICE = 6;
	public static final int REQUEST_CODE_PICTURE = 7;
	public static final int REQUEST_CODE_LOCATION = 8;
	public static final int REQUEST_CODE_NET_DISK = 9;
	public static final int REQUEST_CODE_RESEND_NET_DISK = 10;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
	public static final int REQUEST_CODE_PICK_VIDEO = 12;
	public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
	public static final int REQUEST_CODE_VIDEO = 14;
	public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
	public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
	public static final int REQUEST_CODE_SEND_USER_CARD = 17;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
	public static final int REQUEST_CODE_GROUP_DETAIL = 21;

	public static final int RESULT_CODE_COPY = 1;
	public static final int RESULT_CODE_DELETE = 2;
	public static final int RESULT_CODE_FORWARD = 3;
	public static final int RESULT_CODE_OPEN = 4;
	public static final int RESULT_CODE_DWONLOAD = 5;
	public static final int RESULT_CODE_TO_CLOUD = 6;
	public static final int RESULT_CODE_EXIT_GROUP = 7;

	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_GROUP = 2;

	public static final String COPY_IMAGE = "EASEMOBIMG";
	
	private LinearLayout mParent;
	private Button mBack;
	private TextView mTitle;
	private Button mAction;
	private ListView mDisplay;
	private Button mFace;
	private EditText mContent;
	private Button mSend;

	private ChatAdapter mAdapter;

	private String mName;// 当前聊天用户名称
	private String mAvatar;// 当前聊天用户的头像
	private String mEmail;
	private ChatMessageBroadcastReceiver msgReceiver;
	private String mMessageId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity1);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mParent = (LinearLayout) findViewById(R.id.chat_parent);
		mBack = (Button) findViewById(R.id.chat_back);
		mTitle = (TextView) findViewById(R.id.chat_title);
		mAction = (Button) findViewById(R.id.chat_action);
		mDisplay = (ListView) findViewById(R.id.chat_display);
		mFace = (Button) findViewById(R.id.chat_face);
		mContent = (EditText) findViewById(R.id.chat_content);
		mSend = (Button) findViewById(R.id.chat_send);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		getUnreadChat(mEmail.substring(0, mEmail.indexOf("@")));
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mAction.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 查看聊天记录的对话框
				new AlertDialog.Builder(ChatActivity1.this)
						.setTitle("OOXX")
						.setItems(new String[] { "聊天记录" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// 跳转到聊天记录界面,并传递当前聊天用户的姓名和头像
										Intent intent = new Intent();
										intent.setClass(ChatActivity1.this,
												ChatLogsActivity.class);
										intent.putExtra("name", mName);
										intent.putExtra("email", mEmail);
										intent.putExtra("avatar", mAvatar);
										startActivity(intent);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								}).create().show();
			}
		});
		mFace.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示表情对话框
				showFace(mParent);
			}
		});
		mSend.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获取当前输入的聊天内容
				String content = mContent.getText().toString().trim();
				// 聊天内容不为空时执行
				if (!TextUtils.isEmpty(content)) {
					// 添加聊天信息
					ChatResult result = new ChatResult();
					result.setTime(Utils.getTime(ChatActivity1.this));
					result.setType(1);
					result.setContent(content);
					mKXApplication.mChatResults.add(result);
					// 更新界面并滚动到最后一条信息,并清空输入框
					mAdapter.notifyDataSetChanged();
					mDisplay.setSelection(mKXApplication.mChatResults.size());
					onSendTxtMsg(mEmail);
					mContent.setText("");
				}
			}
		});
		
		mFaceClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭表情对话框
				dismissFace();
			}
		});
		
		mFaceGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取当前光标所在位置
				int currentPosition = mContent.getSelectionStart();
				// 添加有表情的文字
				mContent.setText(new TextUtil(mKXApplication).replace(mContent
						.getText().insert(currentPosition,
								mKXApplication.mFacesText.get(position))));
				// 关闭表情对话框
				dismissFace();
			}
		});
		
	}

	private void init() {
		
		Intent intent = new Intent(this, MessagePushService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		stopService(intent);
		// 获取当前聊天的用户的姓名和头像,并显示姓名到标题栏
		mEmail = getIntent().getStringExtra("email");
		mName = getIntent().getStringExtra("name");
		mAvatar = getIntent().getStringExtra("avatar");
		
		mTitle.setText(mName);
		// 获取聊天记录
		getChat(mEmail);
		if(!StringUtil.isNull(getIntent().getStringExtra("isNews"))){
			mMessageId = getIntent().getStringExtra("messageId");
			/*getUnreadChat(mEmail);*/
			/*EMConversation conversation = EMChatManager.getInstance()
					.getConversation(mEmail);
			conversation.resetUnsetMsgCount();*/
		}
		// 添加适配器并滚动到最后一条信息
		mAdapter = new ChatAdapter();
		mDisplay.setAdapter(mAdapter);
		mDisplay.setSelection(mKXApplication.mChatResults.size());
		
		// 获取到配置options对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		options.setNotificationEnable(true);
		// 设置自定义的文字提示
		options.setNotifyText(new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				// 可以根据message的类型提示不同文字，这里为一个简单的示例
				try {
					return "你的好基友" + message.getStringAttribute("mName")
							+ "发来了一条消息哦";
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

		// 注册message receiver 接收消息
		msgReceiver = new ChatMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
		registerReceiver(msgReceiver, intentFilter);
		//app初始化完毕
		EMChat.getInstance().setAppInited();
	}
	
	
	
	@Override
	protected void onStop() {
		super.onStop();
		Intent intent = new Intent(this, MessagePushService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);
	}

	@Override
	public void onDestroy() {
		// 注销接收聊天消息的message receiver
		if (msgReceiver != null) {
			try {
				unregisterReceiver(msgReceiver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		super.onDestroy();
	}
	
	/**
	 * 获取聊天记录
	 */
	private void getUnreadChat(String username) {
		if (mKXApplication.mChatResults != null) {
			// 判断存储的聊天记录是否已经存在,存在则不在获取
			int count = EMChatManager.getInstance().getUnreadMsgsCount();
			if (count != 0) {
				EMConversation conversation = EMChatManager.getInstance()
						.getConversation(username);
				List<EMMessage> messages = conversation.loadMoreMsgFromDB(mMessageId, count);
				ChatResult result = null;
				// 获取此会话的未读所有消息
				for (int i = 0; i < count; i++) {
					result = new ChatResult();
					result.setTime(Utils.formatDate(ChatActivity1.this, messages.get(i).getMsgTime()));
					TextMessageBody textBody = (TextMessageBody) messages.get(i).getBody();
					result.setContent(textBody.getMessage());
					if(messages.get(i).getFrom().equals(username)){
						result.setType(2);
					}else{
						result.setType(1);
					}
					mKXApplication.mChatResults.add(result);
				}
			}
		}

	}
	
	/**
	 * 接收消息的BroadcastReceiver
	 * 
	 */
	public class ChatMessageBroadcastReceiver extends BroadcastReceiver {
		

		@Override
		public void onReceive(Context context, Intent intent) {
			String msgId = intent.getStringExtra("msgid"); // 消息id
			// 从SDK 根据消息ID 可以获得消息对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			Log.d("main",
					"new message id:" + msgId + " from:" + message.getFrom() + " type:" + message.getType() + " body:" + message.getBody());
			switch (message.getType()) {
			case TXT:
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				ChatResult chatResult = new ChatResult();
				chatResult.setContent(txtBody.getMessage());
				chatResult.setType(2);
				chatResult.setTime(Utils.formatDate(ChatActivity1.this, message.getMsgTime()));
				try {
					mName = message.getStringAttribute("mName");
					mAvatar = message.getStringAttribute("mAvatar");
				} catch (EaseMobException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mKXApplication.mChatResults.add(chatResult);
				mAdapter.notifyDataSetChanged();
//				tvReceivedMsg.append("text message from:" + message.getFrom() + " text:" + txtBody.getMessage() + " \n\r");
				break;
			case IMAGE:
				ImageMessageBody imgBody = (ImageMessageBody) message.getBody();
//				tvReceivedMsg.append("img message from:" + message.getFrom() + " thumbnail:" + imgBody.getThumbnailUrl() + " remoteurl:"
//						+ imgBody.getRemoteUrl() + " \n\r");
				break;
			case VOICE:
				VoiceMessageBody voiceBody = (VoiceMessageBody) message.getBody();
//				tvReceivedMsg.append("voice message from:" + message.getFrom() + " length:" + voiceBody.getLength() + " remoteurl:"
//						+ voiceBody.getRemoteUrl() + " \n\r");
				break;
			case LOCATION:
				LocationMessageBody locationBody = (LocationMessageBody) message.getBody();
//				tvReceivedMsg.append("location message from:" + message.getFrom() + " address:" + locationBody.getAddress() + " \n\r");
				break;
			}
		}
	}
	
	/**
	 * 发送消息。本demo是发送消息给测试机器人（其账号为"bot"）。该测试机器人接收到消息后会把接收的消息原封不动的自动发送回来
	 * 
	 * @param view
	 */
	public void onSendTxtMsg(String name) {
		EMMessage msg = EMMessage.createSendMessage(EMMessage.Type.TXT);
		// 消息发送给测试机器人，bot 会把消息自动发送回来
		msg.setReceipt(name);
		TextMessageBody body = new TextMessageBody(mContent.getText().toString());
		msg.addBody(body);

		// 下面的code 展示了如何添加扩展属性
		msg.setAttribute("mName", StorageUtil.getString(this, "nickname"));
		msg.setAttribute("mAvatar", StorageUtil.getString(this, "mAvatar"));
		// msg.setAttribute("extBoolTrue", true);
		// msg.setAttribute("extBoolFalse", false);
		// msg.setAttribute("extIntAttr", 100);

		// send out msg
		try {
			EMChatManager.getInstance().sendMessage(msg);
			// Log.d("chatdemo", "消息发送成功:" + msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 发送语音
	 * 
	 * @param filePath
	 * @param fileName
	 * @param length
	 * @param isResend
	 */
	private void sendVoice(String name, String filePath, String fileName, String length, boolean isResend) {
		if (!(new File(filePath).exists())) {
			return;
		}
		try {
			final EMMessage message = EMMessage.createSendMessage(EMMessage.Type.VOICE);
			// 如果是群聊，设置chattype,默认是单聊
			String to = name;
			message.setReceipt(to);
			int len = Integer.parseInt(length);
			VoiceMessageBody body = new VoiceMessageBody(new File(filePath), len);
			message.addBody(body);
			
			message.setAttribute("mName", StorageUtil.getString(this, "nickname"));
			message.setAttribute("mAvatar", StorageUtil.getString(this, "mAvatar"));

			setResult(RESULT_OK);
			// send file
			// sendVoiceSub(filePath, fileName, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取聊天记录
	 */
	private void getChat(String username) {
		// 判断存储的聊天记录是否已经存在,存在则不在获取
		if (mKXApplication.mChatResults.isEmpty()) {
			EMConversation conversation = EMChatManager.getInstance().getConversation(username);
			//获取此会话的所有消息
			List<EMMessage> messages = conversation.getAllMessages();

			ChatResult result = null;
			for (int i = 0; i < messages.size(); i++) {
				result = new ChatResult();
				result.setTime(Utils.formatDate(ChatActivity1.this, messages.get(i).getMsgTime()));
				TextMessageBody textBody = (TextMessageBody) messages.get(i).getBody();
				result.setContent(textBody.getMessage());
				if(messages.get(i).getFrom().equals(username)){
					result.setType(2);
				}else{
					result.setType(1);
				}
				mKXApplication.mChatResults.add(result);
			}
		}
	}

	public class ChatAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mChatResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mChatResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(ChatActivity1.this).inflate(
						R.layout.chat_activity_item, null);
				holder = new ViewHolder();
				holder.in = (RelativeLayout) convertView
						.findViewById(R.id.chat_item_in_layout);
				holder.out = (RelativeLayout) convertView
						.findViewById(R.id.chat_item_out_layout);
				holder.inAvatar = (ImageView) convertView
						.findViewById(R.id.chat_item_in_avatar);
				holder.outAvatar = (ImageView) convertView
						.findViewById(R.id.chat_item_out_avatar);
				holder.inName = (TextView) convertView
						.findViewById(R.id.chat_item_in_name);
				holder.outName = (TextView) convertView
						.findViewById(R.id.chat_item_out_name);
				holder.inTime = (TextView) convertView
						.findViewById(R.id.chat_item_in_time);
				holder.outTime = (TextView) convertView
						.findViewById(R.id.chat_item_out_time);
				holder.inContent = (TextView) convertView
						.findViewById(R.id.chat_item_in_content);
				holder.outContent = (TextView) convertView
						.findViewById(R.id.chat_item_out_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ChatResult result = mKXApplication.mChatResults.get(position);
			// 根据消息的类型,显示不同的界面效果,1为用户自己发出的消息,2为用户收到的消息
			switch (result.getType()) {
			case 1:
				holder.in.setVisibility(View.GONE);
				holder.out.setVisibility(View.VISIBLE);
				/*holder.outAvatar.setImageBitmap(PhotoUtil.toRoundCorner(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.head), 15));*/
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + StorageUtil.getString(ChatActivity1.this, "mAvatar"), holder.outAvatar, ImageUtil.getOption());
				holder.outName.setText("我");
				holder.outTime.setText(result.getTime());
				holder.outContent.setText(new TextUtil(mKXApplication)
						.replace(result.getContent()));
				break;

			case 2:
				holder.out.setVisibility(View.GONE);
				holder.in.setVisibility(View.VISIBLE);
//				holder.inAvatar.setImageBitmap(mKXApplication
//						.getAvatar(mAvatar));
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + mAvatar, holder.inAvatar, ImageUtil.getOption());
				holder.inName.setText(mName);
				holder.inTime.setText(result.getTime());
				holder.inContent.setText(new TextUtil(mKXApplication)
						.replace(result.getContent()));
				break;
			}
			return convertView;
		}

		class ViewHolder {
			RelativeLayout in;
			RelativeLayout out;
			ImageView inAvatar;
			ImageView outAvatar;
			TextView inName;
			TextView outName;
			TextView inTime;
			TextView outTime;
			TextView inContent;
			TextView outContent;
		}
	}
}
