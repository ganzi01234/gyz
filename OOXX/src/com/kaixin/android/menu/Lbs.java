package com.kaixin.android.menu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.activity.BaiduMapActivity;
import com.kaixin.android.activity.FriendInfoActivity;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.NearbyPeopleResult;
import com.kaixin.android.result.NearbyPhotoDetailResult;
import com.kaixin.android.result.NearbyPhotoResult;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.ui.base.MyListView;
import com.kaixin.android.ui.base.NearbyPhotoLayout;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 菜单附近类
 * 
 * @author gyz
 * 
 */
public class Lbs {
	private Context mContext;
	private KXApplication mKXApplication;
	private View mLbs;

	private Button mMenu;
	private Button mGoLbs;
	private ScrollView mDisplayLayout;
	private MyListView mDisplay;
	private TextView mNoDisplay;
//	private Button mCheckIn;
	private Button mNearbyPeople;
	private Button mNearbyPhoto;
	private OnOpenListener mOnOpenListener;
	private int mChoosePostition = 0;
	private NearbyPeopleAdapter mNearbyPeopleAdapter;
	private NearbyPhotoAdapter mNearbyPhotoAdapter;
	
	public Lbs(Context context, KXApplication application) {
		mContext = context;
		mKXApplication = application;
		mLbs = LayoutInflater.from(context).inflate(R.layout.lbs, null);
		findViewById();
		setListener();
		init();

	}

	private void findViewById() {
		mMenu = (Button) mLbs.findViewById(R.id.lbs_menu);
		mGoLbs = (Button) mLbs.findViewById(R.id.lbs_golbs);
		mDisplayLayout = (ScrollView) mLbs
				.findViewById(R.id.lbs_display_layout);
		mDisplay = (MyListView) mLbs.findViewById(R.id.lbs_display);
		mNoDisplay = (TextView) mLbs.findViewById(R.id.lbs_nodisplay);
//		mCheckIn = (Button) mLbs.findViewById(R.id.lbs_checkin);
		mNearbyPeople = (Button) mLbs.findViewById(R.id.lbs_nearby_people);
		mNearbyPhoto = (Button) mLbs.findViewById(R.id.lbs_nearby_photo);
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mLbs.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mLbs.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
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
		mGoLbs.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, BaiduMapActivity.class);
				mContext.startActivity(intent);
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				/*NearbyPeopleResult people = mKXApplication.mMyNearByPeopleResults.get(position);
				Intent intent = new Intent();
				intent.setClass(mContext, FriendInfoActivity.class);
				intent.putExtra("email", people.getEmail());
				intent.putExtra("uid", people.getUid());
				intent.putExtra("name", people.getName());
				intent.putExtra("avatar", people.getAvatar());
				mContext.startActivity(intent);*/
				Intent intent = new Intent(mContext, BaiduMapActivity.class);
				intent.putExtra("latitude", Double.parseDouble(mKXApplication.mMyNearByPeopleResults.get(position).getLatitude()));
				intent.putExtra("longitude", Double.parseDouble(mKXApplication.mMyNearByPeopleResults.get(position).getLongitude()));
				intent.putExtra("address", mKXApplication.mMyNearByPeopleResults.get(position).getLocation());
				mContext.startActivity(intent);
			}
			
		});
		/*mCheckIn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 0) {
					mChoosePostition = 0;
					mDisplayLayout.setVisibility(View.GONE);
					mNoDisplay.setVisibility(View.VISIBLE);
					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
				}
			}
		});*/
		mNearbyPeople.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 1) {
					mChoosePostition = 1;
					mDisplayLayout.setVisibility(View.VISIBLE);
					mNoDisplay.setVisibility(View.GONE);
					mDisplay.setDivider(new ColorDrawable(Color
							.parseColor("#aaaaaa")));
					mDisplay.setDividerHeight(1);
					mDisplay.setBackgroundColor(Color.parseColor("#00000000"));
//					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_red);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mDisplay.setAdapter(mNearbyPeopleAdapter);
					mDisplay.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							Intent intent = new Intent(mContext, BaiduMapActivity.class);
							intent.putExtra("latitude", Double.parseDouble(mKXApplication.mMyNearByPeopleResults.get(position).getLatitude()));
							intent.putExtra("longitude", Double.parseDouble(mKXApplication.mMyNearByPeopleResults.get(position).getLongitude()));
							intent.putExtra("address", mKXApplication.mMyNearByPeopleResults.get(position).getLocation());
							mContext.startActivity(intent);
						}
					});
				}
			}
		});
		mNearbyPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 2) {
					mChoosePostition = 2;
					mDisplayLayout.setVisibility(View.VISIBLE);
					mNoDisplay.setVisibility(View.GONE);
					mDisplay.setDivider(null);
					mDisplay.setDividerHeight(0);
					mDisplay.setBackgroundResource(R.drawable.listbox);
//					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					mDisplay.setAdapter(mNearbyPhotoAdapter);
					mDisplay.setOnItemClickListener(null);
				}
			}
		});
	}

	private void init() {
		// 获取附近的人的数据
		getNearbyPeople();
		// 获取附近的照片数据
		getNearbyPhoto();
		// 初始化适配器
		mNearbyPeopleAdapter = new NearbyPeopleAdapter();
		mNearbyPhotoAdapter = new NearbyPhotoAdapter();
		mDisplay.setAdapter(mNearbyPeopleAdapter);
		mDisplayLayout.setVisibility(View.VISIBLE);
		mNoDisplay.setVisibility(View.GONE);
	}

	/**
	 * 获取附近的人的数据
	 */
	private void getNearbyPeople() {
		if (mKXApplication.mMyNearByPeopleResults.isEmpty()) {
			String json = CallService.getNearByPeople(
					StorageUtil.getString(mContext, "longitude"),
					StorageUtil.getString(mContext, "latitude"));
			try {
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray("data");
				NearbyPeopleResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new NearbyPeopleResult();
					result.setUid(array.getJSONObject(i).getString("uid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setAvatar(array.getJSONObject(i).getString("avatar"));
					result.setEmail(array.getJSONObject(i).getString("email"));
					result.setDistance(array.getJSONObject(i).getString(
							"distance"));
					result.setLocation(array.getJSONObject(i).getString(
							"location"));
					result.setLongitude(array.getJSONObject(i).getString(
							"longitude"));
					result.setLatitude(array.getJSONObject(i).getString(
							"latitude"));
					mKXApplication.mMyNearByPeopleResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取附近的照片的数据
	 */
	private void getNearbyPhoto() {
		if (mKXApplication.mMyNearbyPhotoResults.isEmpty()) {
			String json = CallService.getNearByPhoto(
					StorageUtil.getString(mContext, "longitude"),
					StorageUtil.getString(mContext, "latitude"));
			try {
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray("data");
				NearbyPhotoResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new NearbyPhotoResult();
					result.setPid(array.getJSONObject(i).getString("pid"));
					result.setImage(array.getJSONObject(i).getString("image"));
					result.setTitle(array.getJSONObject(i).getString("title"));
					result.setCount(array.getJSONObject(i).getInt("count"));
					result.setLocation(array.getJSONObject(i).getString(
							"location"));
					result.setDistance(array.getJSONObject(i).getString(
							"distance"));
					JSONArray imagesArray = array.getJSONObject(i)
							.getJSONArray("images");
					List<NearbyPhotoDetailResult> images = new ArrayList<NearbyPhotoDetailResult>();
					for (int j = 0; j < imagesArray.length(); j++) {
						NearbyPhotoDetailResult nearbyPhotoDetailResult = new NearbyPhotoDetailResult();
						nearbyPhotoDetailResult.setImage(imagesArray
								.getJSONObject(j).getString("image"));
						nearbyPhotoDetailResult.setOwners_uid(imagesArray
								.getJSONObject(j).getString("owners_uid"));
						nearbyPhotoDetailResult.setOwners_name(imagesArray
								.getJSONObject(j).getString("owners_name"));
						nearbyPhotoDetailResult.setOwners_avatar(imagesArray
								.getJSONObject(j).getString("owners_avatar"));
						nearbyPhotoDetailResult.setDescription(imagesArray
								.getJSONObject(j).getString("description"));
						nearbyPhotoDetailResult.setComment_count(imagesArray
								.getJSONObject(j).getInt("comment_count"));
						nearbyPhotoDetailResult.setLike_count(imagesArray
								.getJSONObject(j).getInt("like_count"));
						/*JSONArray commentsArray = imagesArray.getJSONObject(j)
								.getJSONArray("comments");
						List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
						for (int k = 0; k < commentsArray.length(); k++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("uid", commentsArray.getJSONObject(k)
									.getString("uid"));
							map.put("avatar", commentsArray.getJSONObject(k)
									.getString("avatar"));
							map.put("name", commentsArray.getJSONObject(k)
									.getString("name"));
							map.put("time", commentsArray.getJSONObject(k)
									.getString("time"));
							map.put("content", commentsArray.getJSONObject(k)
									.getString("content"));
							if (commentsArray.getJSONObject(k).has("replys")) {
								JSONArray replysArray = commentsArray
										.getJSONObject(k)
										.getJSONArray("replys");
								List<Map<String, String>> replysResults = new ArrayList<Map<String, String>>();
								for (int l = 0; l < replysArray.length(); l++) {
									Map<String, String> replyMap = new HashMap<String, String>();
									replyMap.put("uid", replysArray
											.getJSONObject(l).getString("uid"));
									replyMap.put("avatar",
											replysArray.getJSONObject(l)
													.getString("avatar"));
									replyMap.put("name", replysArray
											.getJSONObject(l).getString("name"));
									replyMap.put("time", replysArray
											.getJSONObject(l).getString("time"));
									replyMap.put("content",
											replysArray.getJSONObject(l)
													.getString("content"));
									replysResults.add(replyMap);
								}
								map.put("replys", replysResults);
							}
							comments.add(map);
						}
						nearbyPhotoDetailResult.setComments(comments);*/
						images.add(nearbyPhotoDetailResult);
					}
					result.setImages(images);
					mKXApplication.mMyNearbyPhotoResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class NearbyPeopleAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mMyNearByPeopleResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMyNearByPeopleResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.lbs_nearby_people_item, null);
				holder = new ViewHolder();
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.lbs_nearby_people_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_name);
				holder.location = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_location);
				holder.time = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_time);
				holder.picture = (ImageView) convertView
						.findViewById(R.id.lbs_nearby_people_item_picture);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			NearbyPeopleResult result = mKXApplication.mMyNearByPeopleResults
					.get(position);
			ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result
					.getAvatar(), holder.avatar, ImageUtil.getOption());
//			holder.avatar.setImageBitmap(mKXApplication.getDefaultAvatar(result
//					.getAvatar()));
			holder.name.setText(result.getName());
			holder.location.setText(result.getLocation());
			holder.time.setText("距离我:" + result.getDistance().substring(0, result.getDistance().indexOf(".")) + "米");
			if (result.isPicture()) {
				holder.picture.setVisibility(View.VISIBLE);
			} else {
				holder.picture.setVisibility(View.GONE);
			}
			if (position == 0) {
				convertView.setBackgroundResource(R.drawable.lbs_photo_head_bg);
			} else if (position == getCount() - 1) {
				convertView.setBackgroundResource(R.drawable.lbs_photo_more_bg);
			} else {
				convertView
						.setBackgroundResource(R.drawable.lbs_photo_middle_bg);
			}
			return convertView;
		}

		class ViewHolder {
			ImageView avatar;
			TextView name;
			TextView location;
			TextView time;
			ImageView picture;
		}

	}

	private class NearbyPhotoAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mMyNearbyPhotoResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMyNearbyPhotoResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.lbs_nearby_photo_item, null);
				holder = new ViewHolder();
				holder.location = (TextView) convertView
						.findViewById(R.id.lbs_nearby_photo_item_location);
				holder.distance = (TextView) convertView
						.findViewById(R.id.lbs_nearby_photo_item_distance);
				holder.display = (LinearLayout) convertView
						.findViewById(R.id.lbs_nearby_photo_item_display);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			NearbyPhotoResult result = mKXApplication.mMyNearbyPhotoResults
					.get(position);
			holder.location.setText(result.getLocation());
			holder.distance.setText(result.getDistance().substring(0, result.getDistance().indexOf(".")) + "米");
			if (result.getCount() > 0) {
				holder.display.setVisibility(View.VISIBLE);
				holder.display.removeAllViews();
				for (int i = 0; i < result.getCount(); i++) {
					NearbyPhotoDetailResult nearbyPhotoDetailResult = result
							.getImages().get(i);
					NearbyPhotoLayout layout = new NearbyPhotoLayout(mContext,
							mKXApplication);
					holder.display.addView(layout.getLayout());
					holder.display.invalidate();
					layout.setPhoto(nearbyPhotoDetailResult.getImage());
					layout.getLayout().setTag(nearbyPhotoDetailResult);
					layout.getLayout().setOnClickListener(
							new OnClickListener() {

								public void onClick(View v) {
									NearbyPhotoDetailResult result = (NearbyPhotoDetailResult) v
											.getTag();
									System.out.println(result.getDescription());
								}
							});
				}
			} else {
				holder.display.setVisibility(View.GONE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView location;
			TextView distance;
			LinearLayout display;
		}
	}

	public View getView() {
		return mLbs;
	}
	
	public void fresh(){
		mKXApplication.mMyNearbyPhotoResults.clear();
		mKXApplication.mMyNearByPeopleResults.clear();
		// 获取附近的人的数据
		getNearbyPeople();
		mNearbyPeopleAdapter.notifyDataSetChanged();
		// 获取附近的照片数据
		getNearbyPhoto();
		mNearbyPhotoAdapter.notifyDataSetChanged();
	}
	
	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
}
