package com.kaixin.android.activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.UserResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.SoapToObject;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.Utils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 登录界面
 * 
 * @author gyz
 * 
 */
public class LoginActivity extends KXActivity {
	/**
	 * 登录按钮
	 */
	private Button mLogin;
	public static UserResult mUser;
	private EditText metAccount;
	private EditText metPassword;
	private TextView mtxtRegister;
	private TextView mQQLogin;
	private Tencent mTencent;
	private ProgressDialog dialog;
	
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
	
//	// 填写从短信SDK应用后台注册得到的APPKEY
//	private static String APPKEY = "27512ef42665";
//	// 填写从短信SDK应用后台注册得到的APPSECRET
//	private static String APPSECRET = "e762fcd4ffbfd0e9646427644afde078";

//	private NewMessageBroadcastReceiver msgReceiver;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		findViewById();
		setListener();
//		initSDK();
	}
	
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
	}*/

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mLogin = (Button) findViewById(R.id.login_activity_login);
		mQQLogin = (TextView) findViewById(R.id.qqlogin);
		metAccount = (EditText) findViewById(R.id.etAccount);
		metPassword = (EditText) findViewById(R.id.etPassword);
		mtxtRegister = (TextView) findViewById(R.id.txt_register);
		metAccount.setText(StorageUtil.getString(this, "username"));
		metPassword.setText(StorageUtil.getString(this, "password"));
		if(!StringUtil.isNull(StorageUtil.getString(LoginActivity.this, "isLogin"))){
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		// 登陆QQ监听
		mQQLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTencent = Tencent.createInstance(Constants.TENCENT_APP_ID,LoginActivity.this); 
				mTencent.login(LoginActivity.this,Constants.TENCENT_SCOPE, new BaseUiListener()); 
//				Tencent.createInstance(mAppid, LoginActivity.this);
			}
			
		});
		// 登录按钮监听
		mLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				if(StringUtil.isNull(metAccount.getText().toString()) || StringUtil.isNull(metPassword.getText().toString())){
					MessageUtil.showMsg(LoginActivity.this, "账号或者密码不能为空！");
					return;
				}
				
				String serviceUrl = Constants.getUrl() + "/services/kxw_login?wsdl";
				// 定义调用的WebService方法名
				String methodName = "login";
				// 第1步：创建SoapObject对象，并指定WebService的命名空间和调用的方法名
				SoapObject request = new SoapObject("http://service", methodName);
				// 第2步：设置WebService方法的参数
				request.addProperty("username", metAccount.getText().toString());
				String passwordMD5 = Encrypter.md5(metPassword.getText()
						.toString());
				request.addProperty("password", passwordMD5);
				
				//  设置MyObject中的username和passwords
				CallService.setUsername(metAccount.getText().toString());
				CallService.setPasswordMD5(passwordMD5);
				
				// 第3步：创建SoapSerializationEnvelope对象，并指定WebService的版本
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				// 设置bodyOut属性
				envelope.bodyOut = request;
				// 第4步：创建HttpTransportSE对象，并指定WSDL文档的URL
				HttpTransportSE ht = new HttpTransportSE(serviceUrl);
				try
				{
					// 第5步：调用WebService
					ht.call(null, envelope);

					if (envelope.getResponse() != null)
					{

						// 第6步：使用getResponse方法获得WebService方法的返回结果
						SoapObject soapObject = (SoapObject) envelope.getResponse();

						// 通过getProperty方法获得Product对象的属性值

						mUser = (UserResult) SoapToObject.to(UserResult.class, soapObject);
						mUser.setPasswordMD5(Encrypter.md5(metPassword.getText()
								.toString()));
						StorageUtil.saveString(LoginActivity.this, "username", metAccount.getText()
								.toString());
						StorageUtil.saveString(LoginActivity.this, "nickname", mUser.getName());
						StorageUtil.saveString(LoginActivity.this, "userid", String.valueOf(mUser.getId()));
						StorageUtil.saveString(LoginActivity.this, "password", metPassword.getText()
								.toString());
						StorageUtil.saveString(LoginActivity.this, "isLogin", "true");
						String isFirstLogin = StorageUtil.getString(LoginActivity.this, "isFirstLogin");
						String isLogin = StorageUtil.getString(LoginActivity.this, "isLogin");
						toMain(isFirstLogin, isLogin);
						
					}
					else
					{
						MessageUtil.showMsg(LoginActivity.this, "登录失败.");
					}

				}
				catch (Exception e)
				{
					MessageUtil.showMsg(LoginActivity.this, e.getMessage());
				}
				
			}

		});
		
		mtxtRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegActivity.class);//RegActivity.class
				startActivity(intent);
				//打开注册页面
				/*SmsRegisterActivity registerPage = new SmsRegisterActivity();
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
				registerPage.show(LoginActivity.this);*/
			}
		});
		
	}
	
	private void toMain(String isFirstLogin, String isLogin) {
		if(!StringUtil.isNull(isLogin)){
			EMChatManager.getInstance().login(StorageUtil.getString(LoginActivity.this, "username"), Encrypter.md5(StorageUtil.getString(LoginActivity.this, "password")), new EMCallBack() {

				@Override
				public void onError(int arg0, final String errorMsg) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(LoginActivity.this, "登录聊天服务器失败：" + errorMsg, Toast.LENGTH_SHORT).show();
						}
					});
				}

				@Override
				public void onProgress(int arg0, String arg1) {
					
				}

				@Override
				public void onSuccess() {
					
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						}
					});

				}
			});
		}
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
		if(StringUtil.isNull(isFirstLogin)){
			Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
			startActivity(intent);
			finish();
		}else{
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	private class BaseUiListener implements IUiListener {        
		private String nickname;  
		private int gender;
		private String avatar;
		
		@Override       
		public void onComplete(Object response) {  
			runOnUiThread(new Runnable() {
				public void run() {
					dialog = new ProgressDialog(LoginActivity.this);
					dialog.setMessage("登陆中...");
					dialog.show();
				}
			});
			Toast.makeText(getApplicationContext(), "QQ登陆成功", 0).show();  
			JSONObject responseJsonobject = (JSONObject) response; 
			final String openid = responseJsonobject.optString("openid"); 
//			final String access_token = responseJsonobject.optString("access_token"); 
//			final String expires_in = responseJsonobject.optString("expires_in");            
			QQToken qqToken = mTencent.getQQToken();        
			UserInfo info = new UserInfo(getApplicationContext(), qqToken);    
			info.getUserInfo(new IUiListener() {         
				
				@Override            
				public void onError(UiError arg0) { 
					
				}                     
				@Override             
				public void onComplete(Object response) {    
					JSONObject jsonObject = (JSONObject) response;    
					nickname = jsonObject.optString("nickname"); 
					if(jsonObject.has("figureurl")){
						avatar = jsonObject.optString("figureurl_qq_2"); 
					}
					gender = Utils.getGenderNum(jsonObject.optString("gender")); 
					StorageUtil.saveString(LoginActivity.this, "username", openid);
					StorageUtil.saveString(LoginActivity.this, "nickname", nickname);
					StorageUtil.saveString(LoginActivity.this, "password", "123456");
					StorageUtil.saveString(LoginActivity.this, "isLogin", "true");
					reg(openid, nickname, avatar, String.valueOf(gender));
					
				}                               
				@Override           
				public void onCancel() {            

				}           
			});       
		}        
		@Override    
		public void onError(UiError e) {    

		}         
		@Override    
		public void onCancel() {    

		}   
	} 
	
	public void reg(String openid, String nickname, String avatar, String gender) {
		
		String result = CallService.register(openid, Encrypter.md5("123456"), nickname, avatar, gender);
		System.out.println(result+"===================result");
		try {
			JSONObject json = new JSONObject(result);
			if(json.getBoolean("success")){
				Toast.makeText(this, "注册成功！", 1).show();
				try {
					//调用sdk注册方法
					EMChatManager.getInstance().createAccountOnServer(openid, Encrypter.md5("123456"));
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							if(e!=null&&e.getMessage()!=null)
							{
								String errorMsg=e.getMessage();
								if(errorMsg.indexOf("EMNetworkUnconnectedException")!=-1)
								{
									Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", 0).show();
								}else if(errorMsg.indexOf("conflict")!=-1)
								{
									//Toast.makeText(getApplicationContext(), "用户已存在！", 0).show();
								}else{
									Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), 1).show();
								}

							}else{
								Toast.makeText(getApplicationContext(), "注册失败: 未知异常", 1).show();

							}
						}
					});
				}
			}
			String isFirstLogin = StorageUtil.getString(LoginActivity.this, "isFirstLogin");
			String isLogin = StorageUtil.getString(LoginActivity.this, "isLogin");
			toMain(isFirstLogin, isLogin);
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	public void onBackPressed() {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

}