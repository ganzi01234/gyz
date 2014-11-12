package com.kaixin.android.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactUtil {
	static ArrayList<Map<String, Object>> listData;
	static ArrayList<Map<String, String>> phoneData;
	static final String LASTNAME = "lastname";
	static final String PHONES = "phones";
	static final String DISPLAYNAME = "displayname";
	public static ArrayList<Map<String, Object>> getContacts(Context context){
		listData = new ArrayList<Map<String, Object>>();
		Cursor cur=context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cur.moveToNext()) {
			Map<String, Object> mp = new HashMap<String, Object>();

			long id = cur.getLong(cur.getColumnIndex("_id"));
			Cursor pcur = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ Long.toString(id), null, null);

			// 处理多个号码的情况
			String phoneNumbers = "";
			phoneData = new ArrayList<Map<String, String>>();
			while (pcur.moveToNext()) {
				Map<String, String> m = new HashMap<String, String>();
				String strPhoneNumber = pcur
						.getString(pcur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				String strPhoneType = pcur
						.getString(pcur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
				m.put("phone", strPhoneNumber);
				m.put("type", strPhoneType);
				m.put("desc", null);
				phoneData.add(m);
			}
			pcur.close();

			String name = cur.getString(cur.getColumnIndex("display_name"));
			mp.put(LASTNAME, name);
			mp.put(PHONES, phoneData);
			mp.put(DISPLAYNAME, name);
			listData.add(mp);
		}
		cur.close();
		return listData;
	}

}
