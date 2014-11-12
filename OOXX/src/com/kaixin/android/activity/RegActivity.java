package com.kaixin.android.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;

/**
 * 注册界面
 * 
 * @author gyz
 * 
 */
public class RegActivity extends KXActivity {
	/**
	 * 注册按钮
	 */
	private Button mRegister;
	private EditText metAccount;
	private EditText metNick;
	private EditText metPassword;
	
//	private NewMessageBroadcastReceiver msgReceiver;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		findViewById();
		setListener();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mRegister = (Button) findViewById(R.id.register_activity_register);
		metAccount = (EditText) findViewById(R.id.etAccount);
		metNick = (EditText) findViewById(R.id.etNick);
		metPassword = (EditText) findViewById(R.id.etPassword);
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		// 登录按钮监听
		mRegister.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if(StringUtil.isNull(metAccount.getText().toString()) || StringUtil.isNull(metPassword.getText().toString())){
					MessageUtil.showMsg(RegActivity.this, "账号或者密码不能为空！");
					return;
				}
				reg();
			}

		});

	}
	
	public void reg() {
		try {
			//调用sdk注册方法
//			EMChatManager.getInstance().createAccountOnServer(metAccount.getText().toString().trim(), Encrypter.md5(metPassword.getText().toString().trim()));
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
							Toast.makeText(getApplicationContext(), "用户已存在！", 0).show();
						}else{
							Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), 1).show();
						}

					}else{
						Toast.makeText(getApplicationContext(), "注册失败: 未知异常", 1).show();

					}
				}
			});
		}
		
		String result = CallService.register(metAccount.getText().toString().toLowerCase(), Encrypter.md5(metPassword.getText()
						.toString()), metNick.getText().toString(), "", "0");
		try {
			JSONObject json = new JSONObject(result); 
			if(json.getBoolean("success")){
				Toast.makeText(RegActivity.this, "注册成功！", 1).show();
				StorageUtil.saveString(RegActivity.this, "username", metAccount.getText()
						.toString().toLowerCase());
				StorageUtil.saveString(RegActivity.this, "nickname", metNick.getText().toString());
				StorageUtil.saveString(RegActivity.this, "password", metPassword.getText()
						.toString());
				
				Intent intent = new Intent(RegActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(RegActivity.this, "注册失败！"+ json.getString("data"), 1).show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onBackPressed() {
		finish();
	}
}
