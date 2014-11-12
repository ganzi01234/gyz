/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
package com.kaixin.android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.view.ContactItemMaker;
import com.kaixin.android.view.ContactsListView;
import com.kaixin.android.view.DefaultContactViewItem;

/**联系人详细信息页面*/
public class ContactDetailPage extends KXActivity implements OnClickListener{

	private String phoneName = "";
	private ArrayList<String> phoneList = new ArrayList<String>();
	private Button mBack;
	HashMap<String, Object> user = new HashMap<String, Object> ();
	private TextView tvContactName;
	private TextView tvPhonesList;
	private TextView tvInviteHint;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smssdk_contact_detail_page);
		findViewById();
		setListener();
		init();
	}
	
	private void findViewById() {
		mBack = (Button) findViewById(R.id.contacts_back);
		tvContactName = (TextView) findViewById(R.id.tv_contact_name);
		
		tvPhonesList = (TextView) findViewById(R.id.tv_contact_phones);
		tvInviteHint = (TextView) findViewById(R.id.tv_invite_hint);
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		user = (HashMap<String, Object>) getIntent().getSerializableExtra("user");
		setContact(user);
		tvContactName.setText(phoneName);
		StringBuilder phones = new StringBuilder();
		for(String phone : phoneList){
			phones.append("\n");
			phones.append(phone);
		}
		if(phones.length() > 0){
			phones.deleteCharAt(0);
			tvPhonesList.setText(phones.toString());
		}
		String hint = this.getResources().getString(R.string.smssdk_not_invite, phoneName);
		tvInviteHint.setText(Html.fromHtml(hint));
	}
	
	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//关闭当前界面
				finish();
			}
		});
		findViewById(R.id.btn_invite).setOnClickListener(this);
		/*findViewById(R.id.ivSearch).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				findViewById(R.id.llTitle).setVisibility(View.GONE);
				findViewById(R.id.llSearch).setVisibility(View.VISIBLE);
				etSearch.requestFocus();
				etSearch.getText().clear();
			}
		});
		
		findViewById(R.id.iv_clear).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				etSearch.getText().clear();
			}
		});*/
		
	}

	/**
	 * 设置联系人对象
	 * @param HashMap<String, Object> contact
	 */
	@SuppressWarnings("unchecked")
	public void setContact(HashMap<String, Object> contact){
		if(contact.containsKey("displayname")){
			phoneName = String.valueOf(contact.get("displayname"));
		} else if (contact.containsKey("phones")) {
			ArrayList<HashMap<String, Object>> phones
					= (ArrayList<HashMap<String, Object>>) contact.get("phones");
			if (phones != null && phones.size() > 0) {
				phoneName = (String) phones.get(0).get("phone");
			}
		}
		ArrayList<HashMap<String, Object>> phones = (ArrayList<HashMap<String, Object>>) contact.get("phones");
		if (phones != null && phones.size() > 0) {
			for (HashMap<String, Object> phone : phones) {
				String pn = (String) phone.get("phone");
				phoneList.add(pn);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.ll_back) {
			finish();
		} else if (id == R.id.btn_invite) {
			//发送短信，如果有多个号码，就弹出对话框，让用户自己选择
			if(phoneList.size()>1){
				showDialog();
				return;
			}else{
				String phone = phoneList.size() > 0 ? phoneList.get(0) : "";
				sendMsg(phone);
			}
		}
	}

	/**
	 * 发送消息
	 * @param String phone
	 */
	private void sendMsg(String phone){
		Uri smsToUri = Uri.parse("smsto:"+phone);
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", this.getResources().getString(R.string.smssdk_invite_content));
		startActivity(intent);
	}

	/**有多个电话号码时，弹出的选择对话框*/
	private void showDialog() {
		String[] phones = new String[phoneList.size()];
		phones = phoneList.toArray(phones);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getResources().getString(R.string.smssdk_invite_content));
		builder.setCancelable(true);
		builder.setNegativeButton(this.getResources().getString(R.string.smssdk_cancel), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}

		});
		builder.setItems(phones, new DialogInterface.OnClickListener() {
		@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				sendMsg(phoneList.get(which));
			}
		});
		builder.create().show();
	}


}
