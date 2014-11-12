package com.kaixin.android.activity;

import java.util.Random;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;

/**
 * 导入好友至手机通讯录类
 * 
 * @author gyz
 * 
 */
public class InviteActivity extends KXActivity implements OnClickListener{
	private Button mBack;
	private Button mInvite;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite_activity);
//		initSDK();
		findViewById();
		setListener();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.export_back);
		mInvite = (Button) findViewById(R.id.btn_export);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new
//						 Intent(Intent.ACTION_PICK,ContactsContract
//						 .Contacts.CONTENT_URI); 
//				startActivityForResult(intent,2);
				Intent intent = new Intent(InviteActivity.this, ContactsActivity.class); 
				startActivity(intent);
//				ContactsPage contactsPage = new ContactsPage();
//				contactsPage.show(InviteActivity.this);
			}
		});
	}
	
	// 填写从短信SDK应用后台注册得到的APPKEY
//	private static String APPKEY = "27512ef42665";
//	// 填写从短信SDK应用后台注册得到的APPSECRET
//	private static String APPSECRET = "e762fcd4ffbfd0e9646427644afde078";

	//短信注册，随机产生头像
	private static final String[] AVATARS = {
		"http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
		"http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
		"http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
		"http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
		"http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
		"http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
	};

	private boolean ready;
	private Dialog pd;
	private TextView tvNum;

	/*private void initSDK() {
		// 初始化短信SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		final Handler handler = new Handler(this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		//注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;

		// 获取新好友个数
		showDialog();
		SMSSDK.getNewFriendsCount();

	}

	protected void onDestroy() {
		if (ready) {
			//销毁回调监听接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ready) {
			// 获取新好友个数
			showDialog();
		}
	}*/


	/*public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bind_phone:
			//打开注册页面
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");

						// 提交用户信息
						registerUser(country, phone);
					}
				}
			});
			registerPage.show(this);
			break;
		case R.id.rl_contact:
			tvNum.setVisibility(View.GONE);
			//打开通信录好友列表页面
			ContactsPage contactsPage = new ContactsPage();
			contactsPage.show(this);
			break;
		}
	}*/

	/*public boolean handleMessage(Message msg) {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}

		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (result == SMSSDK.RESULT_COMPLETE) {
			//短信注册成功后，返回MainActivity,然后提示新好友
			if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
				Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
			} else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT){
				refreshViewCount(data);
			}
		} else {
			((Throwable) data).printStackTrace();
		}
		return false;
	}*/

	//更新，新好友个数
	/*private void refreshViewCount(Object data){
		int newFriendsCount = 0;
		try {
			newFriendsCount = Integer.parseInt(String.valueOf(data));
		} catch (Throwable t) {
			newFriendsCount = 0;
		}
		if(newFriendsCount > 0){
			tvNum.setVisibility(View.VISIBLE);
			tvNum.setText(String.valueOf(newFriendsCount));
		}else{
			tvNum.setVisibility(View.GONE);
		}
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
	}*/

	//弹出加载框
	/*private void showDialog(){
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		pd = CommonDialog.ProgressDialog(this);
		pd.show();
	}

	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "SmsSDK_User_" + uid;
		String avatar = AVATARS[id % 12];
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
