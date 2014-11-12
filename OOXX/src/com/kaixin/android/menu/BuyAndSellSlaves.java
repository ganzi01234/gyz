package com.kaixin.android.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.activity.FriendInfoActivity;
import com.kaixin.android.activity.InviteActivity;
import com.kaixin.android.activity.SlaveInfoActivity;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.SlaveResult;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.ui.base.MyLetterListView;
import com.kaixin.android.ui.base.MyLetterListView.OnTouchingLetterChangedListener;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.TextUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 买卖奴隶类
 * 
 * @author gyz
 * 
 */
public class BuyAndSellSlaves {
	private Context mContext;
	private KXApplication mKXApplication;
	private View mSlaves;

	private Button mMenu;
	private EditText mSearch;
	private ListView mDisplay;
	private MyLetterListView mLetter;
	private TextUtil mTextUtil;
	private Adapter mAdapter;

	private OnOpenListener mOnOpenListener;
	// 当前显示的好友数据
	private List<SlaveResult> mMySlavesResults = new ArrayList<SlaveResult>();
	// 当前显示的好友的姓名的首字母的在列表中的位置
	private List<Integer> mMySlavesPosition = new ArrayList<Integer>();
	// 当前显示的好友的姓名的首字母数据
	private List<String> mMySlavesFirstName = new ArrayList<String>();

	// 是否显示的是好友内容
	private boolean mIsAll = true;
	private TextView mNoDisplay;
	private RelativeLayout mDisplayLayout;
	private Button mAdd;

	public BuyAndSellSlaves(Context context, KXApplication application) {
		mContext = context;
		mKXApplication = application;
		mSlaves = LayoutInflater.from(context).inflate(R.layout.buy_and_sell_friends, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mSlaves.findViewById(R.id.friends_menu);
		mSearch = (EditText) mSlaves.findViewById(R.id.friends_search);
		mLetter = (MyLetterListView) mSlaves.findViewById(R.id.friends_letter);
		mDisplay = (ListView) mSlaves.findViewById(R.id.friends_display);
		mAdd = (Button) mSlaves.findViewById(R.id.friends_add);
		mDisplayLayout = (RelativeLayout) mSlaves.findViewById(R.id.friends_display_layout);
		mNoDisplay = (TextView) mSlaves.findViewById(R.id.friends_nodisplay);
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mSlaves.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mSlaves.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
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
		mAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext, InviteActivity.class);
				mContext.startActivity(intent);
			}
		});
		mLetter.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			public void onTouchingLetterChanged(String s) {
				// 根据触摸的字母,跳转到响应位置
				if (mKXApplication.mMySlavesFirstNamePosition.get(s) != null) {
					mDisplay.setSelection(mKXApplication.mMySlavesFirstNamePosition
							.get(s));
				}
			}
		});
		mSearch.addTextChangedListener(new TextWatcher() {
			// 当文本改变时调用
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 获取当前输入的内容并大写
				String searchChar = s.toString().toUpperCase();
				// 如果显示的是好友内容
				if (mIsAll) {
					// 清除当前所有的数据
					mMySlavesResults.clear();
					mMySlavesPosition.clear();
					mMySlavesFirstName.clear();
					// 判断输入内容的长度
					if (searchChar.length() > 0) {
						// 判断是否是字母
						if (searchChar.matches("^[a-z,A-Z].*$")) {
							// 判断当前好友里是有存在这个字母,有的话则取出数据更新界面,否则直接更新界面
							if (mKXApplication.mMySlavesGroupByFirstName
									.containsKey(searchChar)) {
								List<SlaveResult> results = mKXApplication.mMySlavesGroupByFirstName
										.get(searchChar);
								mMySlavesResults.addAll(results);
								mMySlavesFirstName.add(searchChar);
								mMySlavesPosition.add(0);
								mAdapter.notifyDataSetChanged();
							} else {
								mAdapter.notifyDataSetChanged();
							}
						} else {
							mAdapter.notifyDataSetChanged();
						}
					} else {
						// 输入框没内容时,获取全部好友并更新界面
						mMySlavesResults
								.addAll(mKXApplication.mMySlaveResults);
						mMySlavesPosition
								.addAll(mKXApplication.mMySlavesPosition);
						mMySlavesFirstName
								.addAll(mKXApplication.mMySlavesFirstName);
						mAdapter.notifyDataSetChanged();
					}
				} 
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void afterTextChanged(Editable s) {

			}
		});
		
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				int section = getSectionForPosition(position);
				SlaveResult friend = mKXApplication.mMySlavesGroupByFirstName
						.get(mMySlavesFirstName.get(section)).get(
								position - getPositionForSection(section));
//				SlavesResult friend = mKXApplication.mMySlavesResults.get(position);
				Intent intent = new Intent();
				intent.setClass(mContext, SlaveInfoActivity.class);
				intent.putExtra("email", friend.getEmail());
				mContext.startActivity(intent);
			}
			
			private int getPositionForSection(int section) {
				if (section < 0 || section >= mMySlavesFirstName.size()) {
					return -1;
				}
				return mMySlavesPosition.get(section);
			}

			private int getSectionForPosition(int position) {
				if (position < 0 || position >= mMySlavesResults.size()) {
					return -1;
				}
				int index = Arrays.binarySearch(mMySlavesPosition.toArray(),
						position);
				return index >= 0 ? index : -index - 2;
			}
		});
	}

	private void init() {
		// 实例化文本工具
		mTextUtil = new TextUtil(mKXApplication);
		// 获取我的好友
		getMySlaves();
		// 添加适配器
		mAdapter = new Adapter();
		mDisplay.setAdapter(mAdapter);

	}
	
	/**
	 * 获取我的好友
	 */
	private void getMySlaves() {
		if (mKXApplication.mMySlaveResults.isEmpty()) {
			String json = CallService.getMySlaves();
			try {
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray("data");
				SlaveResult result = null;
				if(array.length() == 0){
					mDisplayLayout.setVisibility(View.GONE);
					mNoDisplay.setVisibility(View.VISIBLE);
					return;
				}
				for (int i = 0; i < array.length(); i++) {
					result = new SlaveResult();
					result.setUid(array.getJSONObject(i).getString("uid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setAvatar(array.getJSONObject(i).getString("avatar"));
					result.setEmail(array.getJSONObject(i).getString("email"));
					result.setStates(array.getJSONObject(i).getString("state"));
					result.setName_pinyin(mTextUtil.getStringPinYin(result
							.getName()));
					if (!TextUtils.isEmpty(result.getName_pinyin())) {
						result.setName_first(result.getName_pinyin()
								.substring(0, 1).toUpperCase());
					}
					mKXApplication.mMySlaveResults.add(result);

					if (result.getName_first().matches("^[a-z,A-Z].*$")) {
						if (mKXApplication.mMySlavesFirstName.contains(result
								.getName_first())) {
							mKXApplication.mMySlavesGroupByFirstName.get(
									result.getName_first()).add(result);
						} else {
							mKXApplication.mMySlavesFirstName.add(result
									.getName_first());
							List<SlaveResult> list = new ArrayList<SlaveResult>();
							list.add(result);
							mKXApplication.mMySlavesGroupByFirstName.put(
									result.getName_first(), list);
						}
					} else {
						if (mKXApplication.mMySlavesFirstName.contains("#")) {
							mKXApplication.mMySlavesGroupByFirstName.get("#")
									.add(result);
						} else {
							mKXApplication.mMySlavesFirstName.add("#");
							List<SlaveResult> list = new ArrayList<SlaveResult>();
							list.add(result);
							mKXApplication.mMySlavesGroupByFirstName.put("#",
									list);
						}
					}
				}
				Collections.sort(mKXApplication.mMySlavesFirstName);
				int position = 0;
				for (int i = 0; i < mKXApplication.mMySlavesFirstName.size(); i++) {
					mKXApplication.mMySlavesFirstNamePosition
							.put(mKXApplication.mMySlavesFirstName.get(i),
									position);
					mKXApplication.mMySlavesPosition.add(position);
					position += mKXApplication.mMySlavesGroupByFirstName.get(
							mKXApplication.mMySlavesFirstName.get(i)).size();
				}
			} catch (Exception e) {

			}
		}
		mMySlavesResults.addAll(mKXApplication.mMySlaveResults);
		mMySlavesPosition.addAll(mKXApplication.mMySlavesPosition);
		mMySlavesFirstName.addAll(mKXApplication.mMySlavesFirstName);
	}

	private class Adapter extends BaseAdapter implements SectionIndexer {

		public int getCount() {
			return mMySlavesResults.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.friends_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView
						.findViewById(R.id.friends_item_alpha);
				holder.alpha_line = (ImageView) convertView
						.findViewById(R.id.friends_item_alpha_line);
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.friends_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.friends_item_name);
				holder.arrow = (ImageView) convertView
						.findViewById(R.id.friends_item_arrow);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (mIsAll) {
				int section = getSectionForPosition(position);
				final SlaveResult result = mKXApplication.mMySlavesGroupByFirstName
						.get(mMySlavesFirstName.get(section)).get(
								position - getPositionForSection(section));
				if (getPositionForSection(section) == position) {
					holder.alpha.setVisibility(View.VISIBLE);
					holder.alpha_line.setVisibility(View.VISIBLE);
					holder.alpha.setText(mMySlavesFirstName.get(section));
				} else {
					holder.alpha.setVisibility(View.GONE);
					holder.alpha_line.setVisibility(View.GONE);
				}
				holder.name.setText(result.getName());
//				holder.avatar.setImageBitmap(mKXApplication.getAvatar(result
//						.getAvatar()));
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.avatar, ImageUtil.getOption());
				
				holder.arrow.setVisibility(View.GONE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView alpha;
			ImageView alpha_line;
			ImageView avatar;
			TextView name;
			ImageView arrow;
		}

		public Object[] getSections() {
			return mMySlavesFirstName.toArray();
		}

		public int getPositionForSection(int section) {
			if (section < 0 || section >= mMySlavesFirstName.size()) {
				return -1;
			}
			return mMySlavesPosition.get(section);
		}

		public int getSectionForPosition(int position) {
			if (position < 0 || position >= mMySlavesResults.size()) {
				return -1;
			}
			int index = Arrays.binarySearch(mMySlavesPosition.toArray(),
					position);
			return index >= 0 ? index : -index - 2;
		}
	}

	public View getView() {
		return mSlaves;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
