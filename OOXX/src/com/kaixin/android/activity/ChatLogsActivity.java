package com.kaixin.android.activity;

import java.util.List;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.ChatResult;
import com.kaixin.android.utils.PhotoUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;

/**
 * 聊天记录类
 * 
 * @author gyz
 * 
 */
public class ChatLogsActivity extends KXActivity {
	private Button mBack;
	private ListView mDisplay;

	private String mName;// 当前聊天记录的所属用户姓名
	private String mAvatar; // 当前聊天记录的所属用户头像
	private String mEmail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlogs_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.chatlogs_back);
		mDisplay = (ListView) findViewById(R.id.chatlogs_display);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
	}

	private void init() {
		// 接收当前聊天记录所属用户的姓名和头像
		mName = getIntent().getStringExtra("name");
		mEmail = getIntent().getStringExtra("email");
		mAvatar = getIntent().getStringExtra("avatar");
		mBack.setText(mName);
		getChat(mEmail);
		// 添加适配器
		mDisplay.setAdapter(new ChatLogsAdapter());
	}
	
	/**
	 * 获取聊天记录
	 */
	private void getChat(String username) {
		// 判断存储的聊天记录是否已经存在,存在则不在获取
		if (!mKXApplication.mChatResults.isEmpty()) {
			mKXApplication.mChatResults.clear();
			EMConversation conversation = EMChatManager.getInstance().getConversation(username);
			//获取此会话的所有消息
			List<EMMessage> messages = conversation.getAllMessages();

			ChatResult result = null;
			for (int i = 0; i < messages.size(); i++) {
				result = new ChatResult();
				result.setTime(Utils.formatDate(ChatLogsActivity.this, messages.get(i).getMsgTime()));
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

	public class ChatLogsAdapter extends BaseAdapter {

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
				convertView = LayoutInflater.from(ChatLogsActivity.this)
						.inflate(R.layout.chat_activity_item, null);
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
				holder.outAvatar.setImageBitmap(PhotoUtil.toRoundCorner(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.head), 15));
				holder.outName.setText("我");
				holder.outTime.setText(result.getTime());
				holder.outContent.setText(new TextUtil(mKXApplication)
						.replace(result.getContent()));
				break;

			case 2:
				holder.out.setVisibility(View.GONE);
				holder.in.setVisibility(View.VISIBLE);
				holder.inAvatar.setImageBitmap(mKXApplication
						.getAvatar(mAvatar));
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
