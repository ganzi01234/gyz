package com.kaixin.android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.ContactEntry;
import com.kaixin.android.utils.ContactUtil;
import com.kaixin.android.view.ContactItemMaker;
import com.kaixin.android.view.ContactsAdapter;
import com.kaixin.android.view.ContactsListView;
import com.kaixin.android.view.DefaultContactViewItem;
import com.kaixin.android.view.LoadingDailog;

/**
 * 手机通讯录类(暂时只存在界面,没有任何数据)
 * 
 * @author gyz
 * 
 */
public class ContactsActivity extends KXActivity implements TextWatcher {
	private Button mBack;
	private EditText etSearch;
	private ContactsListView listView;
	private ContactsAdapter adapter;

	private Dialog pd;
	private Handler handler;
	private ArrayList<HashMap<String,Object>> friendsInApp;
	private ArrayList<HashMap<String,Object>> contactsInMobile;
	private static final int RESULT_COMPLETE = 8;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_activity);
		findViewById();
		setListener();
		init();
		initData();
	}

	private void init() {
		friendsInApp = new ArrayList<HashMap<String,Object>>();
		contactsInMobile = new ArrayList<HashMap<String,Object>>();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.contacts_back);
		listView = (ContactsListView) findViewById(R.id.clContact);
		etSearch = (EditText) findViewById(R.id.contacts_search);
	}
	
	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//关闭当前界面
				finish();
			}
		});
		
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
		
		etSearch.addTextChangedListener(this);
	}
	
	private void initData(){
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		pd = new LoadingDailog(this);
		if (pd != null) {
			pd.show();
		}

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch(msg.what){
					case ContactsActivity.RESULT_COMPLETE:
						ArrayList<HashMap<String,Object>> rawList = (ArrayList<HashMap<String,Object>>) msg.obj;
						System.out.println(msg.obj+ "==============msg.obj");
						contactsInMobile = (ArrayList<HashMap<String,Object>>) rawList.clone();
						refreshContactList();
					break;
				}
			}
			
		};

		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				 Message msg = new Message();
				 msg.obj = ContactUtil.getContacts(ContactsActivity.this);
				 msg.what = RESULT_COMPLETE;
				 handler.sendMessage(msg);
			}
		}, 1000);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		adapter.search(s.toString());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	//TODO 获取联系人列表
	@SuppressWarnings("unchecked")
	private void refreshContactList() {
		// 造一个“电话号码-联系人”映射表，加速查询
		ArrayList<ContactEntry> phone2Contact = new ArrayList<ContactEntry>();
		for (HashMap<String, Object> contact : contactsInMobile) {
			ArrayList<HashMap<String, Object>> phones
					= (ArrayList<HashMap<String, Object>>) contact.get("phones");
			if (phones != null && phones.size() > 0) {
				for (HashMap<String, Object> phone : phones) {
					String pn = (String) phone.get("phone");
					ContactEntry ent = new ContactEntry(pn, contact);
					phone2Contact.add(ent);
				}
			}
		}

		// 组织应用内好友分组
		ArrayList<HashMap<String,Object>> tmpFia = new ArrayList<HashMap<String,Object>>();
		int p2cSize = phone2Contact.size();
		for (HashMap<String, Object> friend : friendsInApp) {
			String phone = String.valueOf(friend.get("phone"));
			if (phone != null) {
				for (int i = 0; i < p2cSize; i++) {
					ContactEntry ent = phone2Contact.get(i);
					String cp = ent.getKey();
					if (phone.equals(cp)) {
						friend.put("contact", ent.getValue());
						friend.put("fia", true);
						tmpFia.add((HashMap<String, Object>) friend.clone());
					}
				}
			}
		}
		friendsInApp = tmpFia;

		// 移除本地联系人列表中，包含已加入APP的联系人
		HashSet<HashMap<String, Object>> tmpCon = new HashSet<HashMap<String,Object>>();
		for (ContactEntry ent : phone2Contact) {
			String cp = ent.getKey();
			HashMap<String, Object> con = ent.getValue();
			if (cp != null && con != null) {
				boolean shouldAdd = true;
				for (HashMap<String, Object> friend : friendsInApp) {
					String phone = String.valueOf(friend.get("phone"));
					if (cp.equals(phone)) {
						shouldAdd = false;
						break;
					}
				}
				if (shouldAdd) {
					tmpCon.add(con);
				}
			}
		}
		contactsInMobile.clear();
		contactsInMobile.addAll(tmpCon);

		// 删除非应用内好友分组联系人电话列表中已经注册了的电话号码
		for (HashMap<String, Object> friend : friendsInApp) {
			HashMap<String, Object> contact = (HashMap<String, Object>) friend.remove("contact");
			if (contact != null) {
				String phone = String.valueOf(friend.get("phone"));
				if (phone != null) {
					ArrayList<HashMap<String, Object>> phones
							= (ArrayList<HashMap<String, Object>>) contact.get("phones");
					if (phones != null && phones.size() > 0) {
						ArrayList<HashMap<String, Object>> tmpPs = new ArrayList<HashMap<String,Object>>();
						for (HashMap<String, Object> p : phones) {
							String cp = (String) p.get("phone");
							if (!phone.equals(cp)) {
								tmpPs.add(p);
							}
						}
						contact.put("phones", tmpPs);
					}
				}

				// 添加本地联系人名称
				friend.put("displayname", contact.get("displayname"));
			}
		}

		// 更新listview
		runOnUiThread(new Runnable() {
			public void run() {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}

				adapter = new ContactsAdapter(listView, friendsInApp, contactsInMobile, ContactsActivity.this);
				listView.setAdapter(adapter);
			}
		});


//		if (pd != null && pd.isShowing()) {
//			pd.dismiss();
//		}
//
//		try {
//
//			// 造一个“电话号码-联系人”映射表，加速查询
//			HashMap<String, HashMap<String, Object>> phone2Contact = new HashMap<String, HashMap<String,Object>>();
//			for (HashMap<String, Object> contact : contactsInMobile) {
//				ArrayList<HashMap<String, Object>> phones = (ArrayList<HashMap<String, Object>>) contact.get("phones");
//				if (phones != null && phones.size() > 0) {
//					for (HashMap<String, Object> phone : phones) {
//						String pn = (String) phone.get("phone");
//						//有号码，木有名字；名字  = 号码
//						if(!contact.containsKey("displayname")){
//							contact.put("displayname", pn);
//						}
//						phone2Contact.put(pn, contact);
//					}
//				}
//			}
//
//			// 移除本地联系人列表中，包含已加入APP的联系人
//			ArrayList<HashMap<String, Object>> tmpList = new ArrayList<HashMap<String,Object>>();
//			for (int i = 0; i < friendsInApp.size(); i++) {
//				HashMap<String, Object> friend = friendsInApp.get(i);
//				String phoneNum = String.valueOf(friend.get("phone"));
//				HashMap<String, Object> contact = phone2Contact.remove(phoneNum);
//				if (contact != null) {
//					String namePhone = String.valueOf(contact.get("displayname"));
//					if(TextUtils.isEmpty(namePhone)){
//						namePhone = phoneNum;
//					}
//					// 已加入应用的联系人，显示contact name, 否则显示 phoneNumber
//					friend.put("displayname", namePhone);
//					tmpList.add(friend);
//				}
//			}
//			friendsInApp = tmpList;
//			//重新对号码进行过滤，排除重复的contact(一人多码)
//			HashSet<HashMap<String, Object>> contactsSet = new HashSet<HashMap<String,Object>>(phone2Contact.values());
//			contactsInMobile = new ArrayList<HashMap<String,Object>>(contactsSet);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//TODO 更新listview
//		adapter = new ContactsAdapter(listView, friendsInApp, contactsInMobile);
//		adapter.setContactItemMaker(itemMaker);
//		//adapter.setOnItemClickListener(this);
//		listView.setAdapter(adapter);
	}
	
}
