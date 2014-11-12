package com.kaixin.android.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.FriendsResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StringUtil;

public class SearchFriendActivity extends KXActivity {
	private Context mContext;

	private Button mBack;
	private Button mSearch;
	private ListView mDisplay;
	private EditText mFriend;
	
	private String key = "";

	private SearchFriendAdapter mAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.searchfriend);
		findViewById();
		setListener();
		init();
	}
	
	private void findViewById() {
		mBack = (Button) this.findViewById(R.id.search_back);
		mDisplay = (ListView) this.findViewById(R.id.search_list);
		mSearch = (Button) this.findViewById(R.id.btn_search);
		mFriend =  (EditText) this.findViewById(R.id.friends_search);
	}
	
	

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		
		mSearch.setOnClickListener(new OnClickListener() {
			
			private ArrayList<FriendsResult> mFriends;

			@Override
			public void onClick(View v) {
				if(StringUtil.isNull(mFriend.getText().toString())){
					MessageUtil.showMsg(mContext, "请输入关键字查询");
					return;
				}
				getFriendByKey();
			}

			private void getFriendByKey() {
				if(!key.equals(mFriend.getText().toString())){
					if(mKXApplication.mMySearchResults.isEmpty()){
						String json = CallService.getFriendByKey(mFriend.getText().toString());
						try {
							JSONObject jsonObject = new JSONObject(json);
							JSONArray array = jsonObject.getJSONArray(
									"data");
							mFriends = new ArrayList<FriendsResult>();
							for (int j = 0; j < array.length(); j++) {
								FriendsResult user = new FriendsResult();
								user.setUid(array.getJSONObject(j).getString("id"));
								user.setEmail(array.getJSONObject(j).getString("email"));
								user.setName(array.getJSONObject(j).getString("name"));
								user.setFriend(false);
								mFriends.add(user);
							}
							mKXApplication.mMySearchResults.addAll(mFriends);
							mAdapter.notifyDataSetChanged();
							key = mFriend.getText().toString();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
			}
		});
	}

	private void init() {
		//添加适配器
		mAdapter = new SearchFriendAdapter();
		mDisplay.setAdapter(mAdapter);
	}
	
	private class SearchFriendAdapter extends BaseAdapter {
		ViewHolder holder = null;
		public int getCount() {
			return mKXApplication.mMySearchResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMySearchResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.add_friend_item, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView
						.findViewById(R.id.friend_item_title);
				holder.added = (TextView) convertView
						.findViewById(R.id.added_txt);
				holder.add = (Button) convertView
						.findViewById(R.id.add_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CallService.addFriend(mKXApplication.mMySearchResults.get(position).getEmail());
					mKXApplication.mMySearchResults.get(position).setFriend(true);
					Toast.makeText(mContext, "添加好友成功", Toast.LENGTH_LONG).show();
					Constants.isAddFriend = true;
					mAdapter.notifyDataSetChanged();
				}
			});
			
			if(mKXApplication.mMySearchResults.get(position).isFriend()){
				holder.add.setVisibility(View.GONE);
				holder.added.setVisibility(View.VISIBLE);
			}else{
				holder.add.setVisibility(View.VISIBLE);
				holder.added.setVisibility(View.GONE);
			}
			
			holder.title.setText(mKXApplication.mMySearchResults.get(position).getName());
			
//			holder.messageCount.setText("0条新");
			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView added;
			Button add;
		}
	}
	
}
