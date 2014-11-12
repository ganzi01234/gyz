/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
package com.kaixin.android.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.kaixin.android.activity.ContactDetailPage;
import com.kaixin.android.utils.Utils;
import com.kaixin.android.view.ContactsListView.GroupAdapter;
import com.kaixin.android.view.DefaultContactViewItem.ViewHolder;

/**
 * 联系人列表adapter
 */
public class ContactsAdapter extends GroupAdapter {
	private ArrayList<String> titles;
	private ArrayList<ArrayList<HashMap<String, Object>>> contacts;

	private ArrayList<HashMap<String, Object>> friendsInApp = new ArrayList<HashMap<String, Object>>();
	private ArrayList<HashMap<String, Object>> contactsOutApp = new ArrayList<HashMap<String, Object>>();
	private Context context;

	public ContactsAdapter(ContactsListView view, ArrayList<HashMap<String, Object>> friendsInApp, ArrayList<HashMap<String, Object>> contactsOutApp, Context context) {
		super(view);
		this.friendsInApp = friendsInApp;
		this.contactsOutApp = contactsOutApp;
		this.context = context;
		search(null);
	}

	/**
	 * 搜索
	 *
	 * @param token
	 *            搜索的关键字
	 */
	public void search(String token) {

		titles = new ArrayList<String>();
		contacts = new ArrayList<ArrayList<HashMap<String, Object>>>();

		if (friendsInApp.size() > 0) {
			reSortFia(token, friendsInApp);
		}
		if (contactsOutApp.size() > 0) {
			reSortFoa(token, contactsOutApp);
		}

	}

	/** 数据处理 */
	private void reSortFia(String token, ArrayList<HashMap<String, Object>> data) {
		boolean isEmptyToken = TextUtils.isEmpty(token);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, Object> contact : data) {
			String name = "";
			if (contact.containsKey("nickname")) {
				name = String.valueOf(contact.get("nickname"));
			} else if (contact.containsKey("displayname")) {
				name = String.valueOf(contact.get("displayname"));
			} else if (contact.containsKey("phone")) {
				name = String.valueOf(contact.get("phone"));
			}
			if (TextUtils.isEmpty(name)) {
				continue;
			}
			if (isEmptyToken || (!TextUtils.isEmpty(name) && name.contains(token))) {
				list.add(contact);
			}
		}

		if (list.size() > 0) {
			titles.add(view.getContext().getResources().getString(R.string.smssdk_contacts_in_app));
			contacts.add(list);
		}
	}

	/** 数据处理 */
	private void reSortFoa(String token, ArrayList<HashMap<String, Object>> data) {
		boolean isEmptyToken = TextUtils.isEmpty(token);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, Object> contact : data) {
			String name = "";
			if (contact.containsKey("displayname")) {
				name = String.valueOf(contact.get("displayname"));
			} else if (contact.containsKey("phones")) {
				@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, Object>> phones
						= (ArrayList<HashMap<String, Object>>) contact.get("phones");
				if (phones != null && phones.size() > 0) {
					name = (String) phones.get(0).get("phone");
				}
			}
			if (TextUtils.isEmpty(name)) {
				continue;
			}
			if (isEmptyToken || (!TextUtils.isEmpty(name) && name.contains(token))) {
				list.add(contact);
			}
		}

		if (list.size() > 0) {
			titles.add(view.getContext().getResources().getString(R.string.smssdk_contacts_out_app));
			contacts.add(list);
		}
	}

	public int getGroupCount() {
		return titles == null ? 0 : titles.size();
	}

	public int getCount(int group) {
		if (contacts == null) {
			return 0;
		}

		ArrayList<HashMap<String, Object>> list = contacts.get(group);
		if (list == null) {
			return 0;
		}

		return list.size();
	}

	public String getGroupTitle(int group) {
		if (titles.size() > 0) {
			return titles.get(group).toString();
		} else {
			return null;
		}
	}

	public HashMap<String, Object> getItem(int group, int position) {
		if (contacts.size() > 0) {
			return contacts.get(group).get(position);
		} else {
			return null;
		}
	}

	public TextView getTitleView(int group, TextView convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(parent.getContext());
			convertView.setBackgroundColor(0xffeae8ee);
			convertView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			convertView.setTextColor(0xff999999);
			int dp_11 = Utils.dip2px(parent.getContext(), 11);
			convertView.setPadding(dp_11, 0, 0, 0);
			convertView.setWidth(LayoutParams.MATCH_PARENT);
			int dp_26 = Utils.dip2px(parent.getContext(), 26);
			convertView.setHeight(dp_26);
			convertView.setGravity(Gravity.CENTER_VERTICAL);
		}
		String title = getGroupTitle(group);
		if (!TextUtils.isEmpty(title)) {
			convertView.setText(title);
		}
		return convertView;
	}

	@Override
	public View getView(final int group, final int position, View convertView, final ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.smssdk_contacts_listview_item, null);
			viewHolder.ivContact = (ImageView) convertView.findViewById(R.id.iv_contact);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tvContact = (TextView) convertView.findViewById(R.id.tv_contact);
			viewHolder.btnAdd = (Button) convertView.findViewById(R.id.btn_add);
			viewHolder.bg = convertView.findViewById(R.id.rl_lv_item_bg);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final HashMap<String, Object> user = getItem(group, position);
		if(user != null){
			// 如果user包含“fia”，则为应用内好友
			if (user.containsKey("fia")) {
				viewHolder.tvName.setText(String.valueOf(user.get("nickname")));
				viewHolder.tvContact.setVisibility(View.VISIBLE);
				String dspName = (String) user.get("displayname");
				if (TextUtils.isEmpty(dspName)) {
					viewHolder.tvContact.setText(String.valueOf(user.get("phone")));
				} else {
					viewHolder.tvContact.setText(dspName);
				}
				viewHolder.btnAdd.setText(R.string.smssdk_add_contact);
			} else {
				String dspName = (String) user.get("displayname");
				if (TextUtils.isEmpty(dspName)) {
					@SuppressWarnings("unchecked")
					ArrayList<HashMap<String, Object>> phones
							= (ArrayList<HashMap<String, Object>>) user.get("phones");
					if (phones != null && phones.size() > 0) {
						String cp = (String) phones.get(0).get("phone");
						viewHolder.tvName.setText(cp);
					}
				} else {
					viewHolder.tvName.setText(dspName);
				}
				viewHolder.tvContact.setVisibility(View.GONE);
				viewHolder.btnAdd.setText(R.string.smssdk_invite);
			}

			viewHolder.bg.setBackgroundColor(0xffffffff);
			//是否有新好友，如有，改变背景颜色
			if(user.containsKey("isnew")){
				boolean isNew = Boolean.valueOf(String.valueOf(user.get("isnew")));
				if(isNew){
					viewHolder.bg.setBackgroundColor(0xfff7fcff);
				}
			}

			//设置按钮事件
			viewHolder.btnAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(user.containsKey("fia")){
						//TODO 在这里添加第一组的按钮事件
						Toast.makeText(parent.getContext(), String.valueOf(user), Toast.LENGTH_SHORT).show();
					} else{
						Intent intent = new Intent(context, ContactDetailPage.class);
						intent.putExtra("user", (Serializable)user);
						context.startActivity(intent);
					}
				}
			});
		}
		return convertView;
	}

	public class ViewHolder{
		public View bg;
		public ImageView ivContact;
		public TextView tvName;
		public TextView tvContact;
		public Button btnAdd;
	}

	/*public View getView(final int group, final int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> data = getItem(group, position);
		return itemMaker.getView(data, convertView, parent);
	}*/

}
