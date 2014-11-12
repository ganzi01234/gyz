/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
package com.kaixin.android.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.kaixin.android.activity.ContactDetailPage;

public class DefaultContactViewItem implements ContactItemMaker {

	@Override
	public View getView(final HashMap<String, Object> user, View convertView, final ViewGroup parent) {

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
						ContactDetailPage contactDetailPage = new ContactDetailPage();
						contactDetailPage.setContact(user);
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

}
