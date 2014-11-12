package com.kaixin.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;

/**
 * 菜单消息类
 * 
 * @author gyz
 * 
 */
public class AddFriendActivity extends KXActivity {
	private Context mContext;

	private Button mMenu;
	private ListView mDisplay;
	private OnOpenListener mOnOpenListener;
	private String[] mTitles = { "搜号码", "添加手机联系人" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.addfriend);
		findViewById();
		setListener();
		init();
	}



	private void findViewById() {
		mMenu = (Button) this.findViewById(R.id.friend_menu);
		mDisplay = (ListView) this.findViewById(R.id.friend_display);
	}
	
	

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position == 0){
					Intent intent = new Intent(AddFriendActivity.this, SearchFriendActivity.class);
					startActivity(intent);
				}else{
					Intent intent = new Intent(AddFriendActivity.this, InviteActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	private void init() {
		//添加适配器
		mDisplay.setAdapter(new AddFriendAdapter());
	}

	private class AddFriendAdapter extends BaseAdapter {

		public int getCount() {
			return mTitles.length;
		}

		public Object getItem(int position) {
			return mTitles[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.message_item, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView
						.findViewById(R.id.message_item_title);
				holder.messageCount = (TextView) convertView
						.findViewById(R.id.message_item_messagecount);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(mTitles[position]);
//			holder.messageCount.setText("0条新");
			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView messageCount;
		}
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
