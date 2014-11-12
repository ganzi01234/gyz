package com.kaixin.android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.Diary;
import com.kaixin.android.result.FriendInfoResult;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.result.VisitorsResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 好友资料类
 * 
 * @author gyz
 * 
 */
public class FriendInfoActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private ListView mDisplay;
	private View mHead;
	private ImageView mHead_Wallpager;
	private ImageView mHead_Avatar;
	private TextView mHead_Name;
	private ImageView mHead_Gender;
	private TextView mHead_Constellation;
	private ImageView mHead_Chat;
	private ImageView mHead_Gifts;
	private TextView mHead_Sig;
	private TextView mHead_About;
	private TextView mHead_Photo;
	private TextView mHead_Diary;
	private TextView mHead_Friends;
	private LinearLayout mHead_Friends_List;
	private Button mHead_Friends_List_Count;

	private FriendInfoAdapter mAdapter;

	private String mUid;// 当前用户的ID
	private String mName;// 当前用户的姓名
	private String mAvatar;// 当前用户的头像
	private FriendInfoResult mInfoResult;// 当前用户的资料数据
	private String mEmail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendinfo_activity);
		mHead = LayoutInflater.from(this).inflate(
				R.layout.friendinfo_activity_head, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.friendinfo_back);
		mTitle = (TextView) findViewById(R.id.friendinfo_title);
		mDisplay = (ListView) findViewById(R.id.friendinfo_display);
		mHead_Wallpager = (ImageView) mHead
				.findViewById(R.id.friendinfo_head_wallpager);
		mHead_Avatar = (ImageView) mHead
				.findViewById(R.id.friendinfo_head_avatar);
		mHead_Name = (TextView) mHead.findViewById(R.id.friendinfo_head_name);
		mHead_Gender = (ImageView) mHead
				.findViewById(R.id.friendinfo_head_gender);
		mHead_Constellation = (TextView) mHead
				.findViewById(R.id.friendinfo_head_constellation);
		mHead_Chat = (ImageView) mHead.findViewById(R.id.friendinfo_head_chat);
		mHead_Gifts = (ImageView) mHead
				.findViewById(R.id.friendinfo_head_gifts);
		mHead_Sig = (TextView) mHead.findViewById(R.id.friendinfo_head_sig);
		mHead_About = (TextView) mHead.findViewById(R.id.friendinfo_head_about);
		mHead_Photo = (TextView) mHead.findViewById(R.id.friendinfo_head_photo);
		mHead_Diary = (TextView) mHead.findViewById(R.id.friendinfo_head_diary);
		mHead_Friends = (TextView) mHead
				.findViewById(R.id.friendinfo_head_friends);
		mHead_Friends_List = (LinearLayout) mHead
				.findViewById(R.id.friendinfo_head_friends_list);
		mHead_Friends_List_Count = (Button) mHead
				.findViewById(R.id.friendinfo_head_friends_list_count);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mHead_Chat.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到聊天界面并传递姓名和头像
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, ChatActivity.class);
				intent.putExtra("email", mEmail);
				intent.putExtra("name", mName);
				intent.putExtra("avatar", mAvatar);
				startActivity(intent);
			}
		});
		mHead_Gifts.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到送礼物界面并传递ID、姓名、头像
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, SendGiftActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("name", mName);
				intent.putExtra("avatar", mAvatar);
				startActivity(intent);
			}
		});
		mHead_About.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到关于界面并传递ID和资料
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, AboutActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("result", mInfoResult);
				startActivity(intent);
			}
		});
		mHead_Photo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到照片界面并传递ID和姓名
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, PhotoActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("name", mName);
				startActivity(intent);
			}
		});
		mHead_Diary.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到日记界面并传递ID和姓名
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, DiaryActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("name", mName);
				startActivity(intent);
			}
		});
		mHead_Friends.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到好友界面并传递ID和姓名
				Intent intent = new Intent();
				intent.setClass(FriendInfoActivity.this, FriendsActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("name", mName);
				startActivity(intent);
			}
		});
		mHead_Friends_List_Count.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断来访者的数量
				if (mInfoResult.getVisitor_count() > 0) {
					// 跳转到最近来访界面并传递ID
					Intent intent = new Intent();
					intent.setClass(FriendInfoActivity.this,
							VisitorsActivity.class);
					intent.putExtra("uid", mUid);
					startActivity(intent);
				} else {
					// 显示提示信息
					Toast.makeText(FriendInfoActivity.this, "暂时未有来访者信息",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void init() {
		// 接收用户的ID、姓名、头像
		mEmail = getIntent().getStringExtra("email");
		mUid = getIntent().getStringExtra("uid");
		mName = getIntent().getStringExtra("name");
		mAvatar = getIntent().getStringExtra("avatar");
		// 获取资料
		getInfo();
		// 获取状态
		getHomeData();
		// 根据用户ID显示界面内容,ID为空时代表为当前用户资料则隐藏聊天和送礼物功能
		if (StringUtil.isNull(mUid)) {
			mTitle.setText("我的首页");
			mHead_Chat.setVisibility(View.GONE);
			mHead_Gifts.setVisibility(View.GONE);
			mAdapter = new FriendInfoAdapter(FriendInfoActivity.this,
					mKXApplication.mMyStatusResults);
		} else {
			CallService.addVisitor(mUid, StorageUtil.getString(this, "userid"), StorageUtil.getString(this, "nickname"));
			mTitle.setText("好友详情");
			mHead_Chat.setVisibility(View.VISIBLE);
			mHead_Gifts.setVisibility(View.VISIBLE);
			if (mKXApplication.mFriendStatusResults.containsKey(mUid)) {
				mAdapter = new FriendInfoAdapter(FriendInfoActivity.this,
						mKXApplication.mFriendStatusResults.get(mUid));
			} else {
				mAdapter = new FriendInfoAdapter(FriendInfoActivity.this, null);
			}
		}
		// 获取来访者
		getVisitors();
		// 添加头布局和适配器
		mDisplay.addHeaderView(mHead);
		mDisplay.setAdapter(mAdapter);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		/*if(StringUtil.isNull(mUid)){
			mInfoResult = mKXApplication.mMyInfoResult;
			setInfo();
		}*/
		/*mInfoResult = mKXApplication.mMyInfoResult;
		setInfo();*/
	}

	/**
	 * 获取用户资料
	 */
	private void getInfo() {
		// ID为空代表为当前用户数据
		if (StringUtil.isNull(mUid)) {
			if (mKXApplication.mMyInfoResult == null) {
				mKXApplication.mMyInfoResult = new FriendInfoResult();
				String json = CallService.getUserInfo("");
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject object = jsonObject.getJSONObject("data");
					mKXApplication.mMyInfoResult.setName(object
							.getString("name"));
					mKXApplication.mMyInfoResult.setAvatar(object
							.getString("avatar"));
					mKXApplication.mMyInfoResult.setGender(object
							.getInt("gender"));
					mKXApplication.mMyInfoResult.setConstellation(object
							.getString("constellation"));
					mKXApplication.mMyInfoResult.setSignature(object
							.getString("signature"));
					mKXApplication.mMyInfoResult.setPhoto_count(object
							.getInt("photo_count"));
					mKXApplication.mMyInfoResult.setDiary_count(object
							.getInt("diary_count"));
					mKXApplication.mMyInfoResult.setFriend_count(object
							.getInt("friend_count"));
					mKXApplication.mMyInfoResult.setVisitor_count(object
							.getInt("visitor_count"));
					mKXApplication.mMyInfoResult.setWallpager(object
							.getInt("wallpager"));
					mKXApplication.mMyInfoResult.setDate(object
							.getString("date"));
					mInfoResult = mKXApplication.mMyInfoResult;
					setInfo();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				mInfoResult = mKXApplication.mMyInfoResult;
				setInfo();
			}
		} else {
			if (mKXApplication.mFriendInfoResults.containsKey(mUid)) {
				mInfoResult = mKXApplication.mFriendInfoResults.get(mUid);
				setInfo();
			} else {
				mInfoResult = new FriendInfoResult();
				String json = CallService.getUserInfo(mUid);
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject object = jsonObject.getJSONObject("data");
					mInfoResult.setName(object.getString("name"));
					mInfoResult.setAvatar(object.getString("avatar"));
					mInfoResult.setGender(object.getInt("gender"));
					mInfoResult.setConstellation(object
							.getString("constellation"));
					mInfoResult.setSignature(object.getString("signature"));
					mInfoResult.setPhoto_count(object.getInt("photo_count"));
					mInfoResult.setDiary_count(object.getInt("diary_count"));
					mInfoResult.setFriend_count(object.getInt("friend_count"));
					mInfoResult
							.setVisitor_count(object.getInt("visitor_count"));
					mInfoResult.setWallpager(object.getInt("wallpager"));
					mInfoResult.setDate(object.getString("date"));
					mKXApplication.mFriendInfoResults.put(mUid, mInfoResult);
					setInfo();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 添加资料数据显示到界面上
	 */
	private void setInfo() {
		mHead_Name.setText(mInfoResult.getName());
//		mHead_Avatar.setImageBitmap(mKXApplication.getAvatar(mInfoResult
//				.getAvatar()));
		ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ mInfoResult
				.getAvatar(), mHead_Avatar, ImageUtil.getOption());
		
		mHead_Gender.setImageBitmap(Utils.getGender(getResources(),
				mInfoResult.getGender()));
		mHead_Constellation.setText(mInfoResult.getConstellation());
		mHead_Sig.setText(new TextUtil(mKXApplication).replace(mInfoResult
				.getSignature()));
		mHead_About.setText("关于");
		mHead_Photo.setText("照片 " + mInfoResult.getPhoto_count());
		mHead_Diary.setText("日记 " + mInfoResult.getDiary_count());
		mHead_Friends.setText("好友 " + mInfoResult.getFriend_count());
		mHead_Friends_List_Count.setText(mInfoResult.getVisitor_count() + "");
		mHead_Wallpager.setImageBitmap(mKXApplication
				.getTitleWallpager(mInfoResult.getWallpager()));
	}
	
	

	/**
	 * 获取用户来访者数据
	 */
	private void getVisitors() {
		// ID为空代表为当前用户数据
		if (StringUtil.isNull(mUid)) {
			if (mKXApplication.mMyVisitorsResults.isEmpty()) {
				String json;
				try {
					json = CallService.getVisitors(StorageUtil.getString(this, "userid"));
					JSONObject object = new JSONObject(json);
					JSONArray array = object.getJSONArray(
							"data");
					VisitorsResult result = null;
					for (int i = 0; i < array.length(); i++) {
						result = new VisitorsResult();
						result.setUid(array.getJSONObject(i).getString("visitor_uid"));
						result.setName(array.getJSONObject(i).getString("visitor_name"));
						result.setAvatar(array.getJSONObject(i)
								.getString("avatar"));
						result.setTime(array.getJSONObject(i).getString("time"));
						mKXApplication.mMyVisitorsResults.add(result);
					}
					setVisitors(mKXApplication.mMyVisitorsResults);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				setVisitors(mKXApplication.mMyVisitorsResults);
			}
		} else {
			if (mKXApplication.mFriendVisitorsResults.containsKey(mUid)) {
				setVisitors(mKXApplication.mFriendVisitorsResults.get(mUid));
			} else {
				String json;
				try {
					json = CallService.getVisitors(mUid);
					JSONObject object = new JSONObject(json);
					JSONArray array = object.getJSONArray(
							"data");
					VisitorsResult result = null;
					List<VisitorsResult> list = new ArrayList<VisitorsResult>();
					for (int i = 0; i < array.length(); i++) {
						result = new VisitorsResult();
						result.setUid(array.getJSONObject(i).getString("visitor_uid"));
						result.setName(array.getJSONObject(i).getString("visitor_name"));
						result.setAvatar(array.getJSONObject(i)
								.getString("avatar"));
						result.setTime(array.getJSONObject(i).getString("time"));
						list.add(result);
					}
					mKXApplication.mFriendVisitorsResults.put(mUid, list);
					setVisitors(list);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 添加用户的来访者数据显示到界面上
	 * 
	 * @param list
	 *            来访者数据
	 */
	private void setVisitors(List<VisitorsResult> list) {
		for (int i = 0; i < list.size(); i++) {
			VisitorsResult result = list.get(i);
			// 显示最近头像
			ImageView imageView = new ImageView(this);
			int widthAndHeight = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 30, getResources()
							.getDisplayMetrics());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					widthAndHeight, widthAndHeight);
			imageView.setLayoutParams(params);
			imageView.setPadding(4, 4, 4, 4);
//			imageView.setImageBitmap(mKXApplication.getAvatar(result
//					.getAvatar()));
			ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ result
					.getAvatar(), imageView, ImageUtil.getOption());
			imageView.setTag(result);
			mHead_Friends_List.addView(imageView);
			mHead_Friends_List.invalidate();
			imageView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// 获取当前单击的来访者资料,并跳转到查看好友资料界面
					VisitorsResult result = (VisitorsResult) v.getTag();
					Intent intent = new Intent();
					intent.setClass(FriendInfoActivity.this,
							FriendInfoActivity.class);
					intent.putExtra("uid", result.getUid());
					intent.putExtra("name", result.getName());
					intent.putExtra("avatar", result.getAvatar());
					startActivity(intent);
				}
			});
		}
	}
	
	private void getHomeData() {
		// ID为空代表为当前用户数据
		if (StringUtil.isNull(mUid)) {
			if(mKXApplication.mMyStatusResults.isEmpty()){
				String json = CallService.getMyMessages();
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONArray array = jsonObject.getJSONArray("data");
					HomeResult result = null;
					for (int i = 0; i < array.length(); i++) {
						result = new HomeResult();
						result.setUid(array.getJSONObject(i).getInt("uid"));
						result.setEmail(array.getJSONObject(i).getString("email"));
						result.setName(array.getJSONObject(i).getString("name"));
						result.setAvatar(array.getJSONObject(i).getString("avatar"));
						result.setType(array.getJSONObject(i).getString("type"));
						result.setTime(array.getJSONObject(i).getString("time"));
						result.setTitle(array.getJSONObject(i).getString("title"));
						result.setMessageid(array.getJSONObject(i).getInt("messageid"));
						result.setAlbumid(array.getJSONObject(i).getInt("albumid"));
						if (array.getJSONObject(i).has("from")) {
							result.setFrom(array.getJSONObject(i).getString("from"));
						}
						if (array.getJSONObject(i).has("comment_count")) {
							result.setComment_count(array.getJSONObject(i).getInt(
									"comment_count"));
						}
						if (array.getJSONObject(i).has("like_count")) {
							result.setLike_count(array.getJSONObject(i).getInt(
									"like_count"));
						}
						if (array.getJSONObject(i).has("photo")) {
							JSONArray photoArray = array.getJSONObject(i).getJSONArray("photo");
							List<String> photos = new ArrayList<String>();;
							for(int j=0; j< photoArray.length();j++){
								photos.add(photoArray.getString(j));
							}
							result.setPhoto(photos);
						}
						mKXApplication.mMyStatusResults.add(result);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}else{
			if (!mKXApplication.mFriendStatusResults.containsKey(mUid)) {
				String json;
				try {
					json = CallService.getFriendMessages(mUid);
						JSONObject jsonObject = new JSONObject(json);
						JSONArray array = jsonObject.getJSONArray("data");
						List<HomeResult> results = new ArrayList<HomeResult>();
						HomeResult result = null;
						for (int i = 0; i < array.length(); i++) {
							result = new HomeResult();
							result.setUid(array.getJSONObject(i).getInt("uid"));
							result.setEmail(array.getJSONObject(i).getString("email"));
							result.setName(array.getJSONObject(i).getString("name"));
							result.setAvatar(array.getJSONObject(i).getString("avatar"));
							result.setType(array.getJSONObject(i).getString("type"));
							result.setTime(array.getJSONObject(i).getString("time"));
							result.setTitle(array.getJSONObject(i).getString("title"));
							result.setMessageid(array.getJSONObject(i).getInt("messageid"));
							result.setAlbumid(array.getJSONObject(i).getInt("albumid"));
							if (array.getJSONObject(i).has("from")) {
								result.setFrom(array.getJSONObject(i).getString("from"));
							}
							if (array.getJSONObject(i).has("comment_count")) {
								result.setComment_count(array.getJSONObject(i).getInt(
										"comment_count"));
							}
							if (array.getJSONObject(i).has("like_count")) {
								result.setLike_count(array.getJSONObject(i).getInt(
										"like_count"));
							}
							if (array.getJSONObject(i).has("photo")) {
								JSONArray photoArray = array.getJSONObject(i).getJSONArray("photo");
								List<String> photos = new ArrayList<String>();;
								for(int j=0; j< photoArray.length();j++){
									photos.add(photoArray.getString(j));
								}
								result.setPhoto(photos);
							}
							results.add(result);
						}
						mKXApplication.mFriendStatusResults.put(mUid, results);
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 获取用户的状态数据
	 */
	private class FriendInfoAdapter extends BaseAdapter {
		private Context mContext;
		List<HomeResult> mResults = new ArrayList<HomeResult>();

		public FriendInfoAdapter(Context context, List<HomeResult> results) {
			mContext = context;
			if (results != null) {
				mResults = results;
			}
		}

		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.size();
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.user_info_item, null);
				holder = new ViewHolder();
				holder.viewed = (View) convertView
						.findViewById(R.id.user_item_viewed);

				holder.viewed_avatar = (ImageView) holder.viewed
						.findViewById(R.id.user_info_item_avatar);
				holder.viewed_name = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_name);
				holder.viewed_time = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_time);
				holder.viewed_title = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_content);
				holder.viewed_from = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_from);
				holder.viewed_comment_count = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_comment_count);
				holder.viewed_like_count = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_like_count);
				holder.viewed_forward_count = (TextView) holder.viewed
						.findViewById(R.id.user_info_item_forward_count);
				holder.photo = (View) convertView
						.findViewById(R.id.user_item_photo);
				holder.photo_avatar = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_avatar);
				holder.photo_name = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_name);
				holder.photo_time = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_time);
				holder.photo_title = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_titie);
				holder.photo_photo1 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo1);
				holder.photo_photo2 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo2);
				holder.photo_photo3 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo3);
				holder.photo_photo4 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo4);
				holder.photo_photo5 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo5);
				holder.photo_photo6 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo6);
				holder.photo_photo7 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo7);
				holder.photo_photo8 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo8);
				holder.photo_photo9 = (ImageView) holder.photo
						.findViewById(R.id.user_photo_item_photo9);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mScreenWidth / 4 , mScreenWidth / 4);
				params.setMargins(2, 2, 2, 2);
				holder.photo_photo1.setLayoutParams(params);
				holder.photo_photo2.setLayoutParams(params);
				holder.photo_photo3.setLayoutParams(params);
				holder.photo_photo4.setLayoutParams(params);
				holder.photo_photo5.setLayoutParams(params);
				holder.photo_photo6.setLayoutParams(params);
				holder.photo_photo7.setLayoutParams(params);
				holder.photo_photo8.setLayoutParams(params);
				holder.photo_photo9.setLayoutParams(params);
				holder.photo_from = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_from);
				holder.photo_comment_count = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_comment_count);
				holder.photo_like_count = (TextView) holder.photo
						.findViewById(R.id.user_photo_item_like_count);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final HomeResult result = mResults.get(position);
			if ("viewed".equals(result.getType())) {
				holder.viewed.setVisibility(View.VISIBLE);
				holder.photo.setVisibility(View.GONE);
				holder.viewed.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(mContext,
								HomeDiaryDetailActivity.class);
						if(!StorageUtil.getString(mContext, "username").equals(result.getEmail())){
							intent.putExtra("uid", result.getUid());
						}
						intent.putExtra("name", result.getName());
						intent.putExtra("result", (Serializable)result);
						mContext.startActivity(intent);
					}
				});
//				holder.viewed_avatar.setImageBitmap(mKXApplication
//						.getAvatar(result.getAvatar()));
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.viewed_avatar, ImageUtil.getOption());
				
				holder.viewed_name.setText(result.getName());
				holder.viewed_time.setText(result.getTime());
				holder.viewed_title.setText(result.getTitle());
				holder.viewed_from.setText(result.getFrom());
				holder.viewed_comment_count.setText(result.getComment_count()
						+ "");
				holder.viewed_like_count.setText(result.getLike_count() + "");
				holder.viewed_forward_count.setText(result.getComment_count() + "");
			} else {
				holder.viewed.setVisibility(View.GONE);
				holder.photo.setVisibility(View.VISIBLE);
				holder.photo_photo2.setVisibility(View.GONE);
				holder.photo_photo3.setVisibility(View.GONE);
				holder.photo_photo4.setVisibility(View.GONE);
				holder.photo_photo5.setVisibility(View.GONE);
				holder.photo_photo6.setVisibility(View.GONE);
				holder.photo_photo7.setVisibility(View.GONE);
				holder.photo_photo8.setVisibility(View.GONE);
				holder.photo_photo9.setVisibility(View.GONE);
//				holder.photo_avatar.setImageBitmap(mKXApplication
//						.getAvatar(result.getAvatar()));
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.photo_avatar, ImageUtil.getOption());
				holder.photo_name.setText(result.getName());
				holder.photo_time.setText(result.getTime());
				holder.photo_title.setText(result.getTitle());
				for(int i = 0; i < result.getPhoto().size(); i++){
					switch(i){
						case 0:
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo1, ImageUtil.getImageOption());
							break;
						case 1:
							holder.photo_photo2.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo2, ImageUtil.getImageOption());
							break;
						case 2:
							holder.photo_photo3.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo3, ImageUtil.getImageOption());
							break;
						case 3:
							holder.photo_photo4.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo4, ImageUtil.getImageOption());
							break;
						case 4:
							holder.photo_photo5.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo5, ImageUtil.getImageOption());
							break;
						case 5:
							holder.photo_photo6.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo6, ImageUtil.getImageOption());
							break;
						case 6:
							holder.photo_photo7.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo7, ImageUtil.getImageOption());
							break;
						case 7:
							holder.photo_photo8.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo8, ImageUtil.getImageOption());
							break;
						case 8:
							holder.photo_photo9.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getPhoto().get(i), holder.photo_photo9, ImageUtil.getImageOption());
							break;
					}
						
				}
				
				holder.photo_from.setText(result.getFrom());
				holder.photo_comment_count.setText(result.getComment_count()
						+ "");
				holder.photo_like_count.setText(result.getLike_count() + "");
				holder.photo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(mContext,
								HomePhotoPictureDetailActivity.class);
						if(!StorageUtil.getString(mContext, "username").equals(result.getEmail())){
							intent.putExtra("uid", result.getUid());
						}
						intent.putExtra("avatar", result.getAvatar());
						intent.putExtra("name", result.getName());
						intent.putExtra("result", (Serializable)result);
						mContext.startActivity(intent);
					}
				});
			}
			return convertView;
		}

		class ViewHolder {
			View viewed;
			ImageView viewed_avatar;
			TextView viewed_name;
			TextView viewed_time;
			TextView viewed_title;
			TextView viewed_all;
			TextView viewed_from;
			TextView viewed_comment_count;
			TextView viewed_like_count;
			TextView viewed_forward_count;

			View photo;
			ImageView photo_avatar;
			TextView photo_name;
			TextView photo_time;
			TextView photo_title;
			ImageView photo_photo1;
			ImageView photo_photo2;
			ImageView photo_photo3;
			ImageView photo_photo4;
			ImageView photo_photo5;
			ImageView photo_photo6;
			ImageView photo_photo7;
			ImageView photo_photo8;
			ImageView photo_photo9;
			TextView photo_from;
			TextView photo_comment_count;
			TextView photo_like_count;
			TextView photo_forward_count;
		}
	}

	/*private class FriendInfoAdapter extends BaseAdapter {
		private Context mContext;
		private List<Diary> mResults = new ArrayList<Diary>();

		public FriendInfoAdapter(Context context, List<Diary> results) {
			mContext = context;
			if (results != null) {
				mResults = results;
			}
		}

		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.friendinfo_activity_item, null);
				holder = new ViewHolder();
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.friendinfo_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.friendinfo_item_name);
				holder.time = (TextView) convertView
						.findViewById(R.id.friendinfo_item_time);
				holder.content = (TextView) convertView
						.findViewById(R.id.friendinfo_item_content);
				holder.from = (TextView) convertView
						.findViewById(R.id.friendinfo_item_from);
				holder.comment_count = (TextView) convertView
						.findViewById(R.id.friendinfo_item_comment_count);
				holder.forward_count = (TextView) convertView
						.findViewById(R.id.friendinfo_item_forward_count);
				holder.like_count = (TextView) convertView
						.findViewById(R.id.friendinfo_item_like_count);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 添加状态信息到界面
			Diary result = mResults.get(position);
//			holder.avatar.setImageBitmap(mAvatar);
			ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ result.getAvatar(), holder.avatar, ImageUtil.getOption());
			holder.name.setText(result.getUsername());
			holder.time.setText(result.getTime());
			holder.content.setText(new TextUtil(mKXApplication)
					.replace(result.getTitle()));
			return convertView;
		}

		class ViewHolder {
			ImageView avatar;
			TextView name;
			TextView time;
			TextView content;
			TextView from;
			TextView comment_count;
			TextView forward_count;
			TextView like_count;
		}
	}*/
}
