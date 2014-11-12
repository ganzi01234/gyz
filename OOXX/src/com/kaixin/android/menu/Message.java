package com.kaixin.android.menu;

import android.content.Context;
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

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.utils.CommonUtils;

/**
 * 菜单消息类
 * 
 * @author gyz
 * 
 */
public class Message {
	private Context mContext;
	private View mMessage;
	private KXApplication mKXApplication;

	private Button mMenu;
	private Button mRefresh;
	private Button mWriteMessage;
	private ListView mDisplay;
	private OnOpenListener mOnOpenListener;
	private String[] mTitles = { "消息", "评论" };

	public Message(Context context, KXApplication application) {
		mContext = context;
		mKXApplication = application;
		mMessage = LayoutInflater.from(context).inflate(R.layout.message, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mMessage.findViewById(R.id.message_menu);
		mRefresh = (Button) mMessage.findViewById(R.id.message_refresh);
		mWriteMessage = (Button) mMessage
				.findViewById(R.id.message_write_message);
		mDisplay = (ListView) mMessage.findViewById(R.id.message_display);
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mMessage.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mMessage.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
		}
	}

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position == 0){
					
				}else{
					
				}
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//暂时不做任何操作
			}
		});
		mWriteMessage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//暂时不做任何操作
			}
		});
	}

	private void init() {
		//添加适配器
		mDisplay.setAdapter(new MessageAdapter());
	}

	public View getView() {
		return mMessage;
	}

	private class MessageAdapter extends BaseAdapter {

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
			holder.messageCount.setText("0条新");
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
