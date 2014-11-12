package com.kaixin.android.menu;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.activity.AboutActivity;
import com.kaixin.android.activity.ChangeWallpagerActivity;
import com.kaixin.android.activity.CheckInActivity;
import com.kaixin.android.activity.ContactsActivity;
import com.kaixin.android.activity.DiaryActivity;
import com.kaixin.android.activity.EditSignatureActivity;
import com.kaixin.android.activity.FriendInfoActivity;
import com.kaixin.android.activity.FriendsActivity;
import com.kaixin.android.activity.HomeDiaryDetailActivity;
import com.kaixin.android.activity.HomePhotoPictureDetailActivity;
import com.kaixin.android.activity.PhoneAlbumActivity;
import com.kaixin.android.activity.PhotoActivity;
import com.kaixin.android.activity.VisitorsActivity;
import com.kaixin.android.activity.PlayVoiceActivity;
import com.kaixin.android.activity.WriteRecordActivity;
import com.kaixin.android.anim.UgcAnimations;
import com.kaixin.android.common.Constants;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.result.Diary;
import com.kaixin.android.result.FriendInfoResult;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.result.VisitorsResult;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 用户首页
 * 
 * @author gyz
 * 
 */
public class User {
	private Context mContext;
	private Activity mActivity;
	private KXApplication mKXApplication;
	/**
	 * 当前界面的View
	 */
	private View mUser;
	/**
	 * 以下为控件,自己查看布局文件
	 */
	// 头布局控件
	private View mUserHead;
	private ImageView mHead_Wallpager;
	private ImageView mHead_Avatar;
	private TextView mHead_Name;
	private ImageView mHead_Gender;
	private TextView mHead_Constellation;
	private LinearLayout mHead_Sig_Layout;
	private TextView mHead_Sig;
	private TextView mHead_About;
	private TextView mHead_Photo;
	private TextView mHead_Diary;
	private TextView mHead_Friends;
	private Button mRefresh;
	private LinearLayout mHead_Friends_List;
	private Button mHead_Friends_List_Count;
	// Path菜单控件
	private View mUgcView;
	private RelativeLayout mUgcLayout;
	private ImageView mUgc;
	private ImageView mUgcBg;
	private ImageView mUgcVoice;
	private ImageView mUgcPhoto;
	private ImageView mUgcRecord;
	private ImageView mUgcLbs;

	private Button mMenu;
	// 显示内容的ListView以及适配器
	private ListView mDisplay;
	private UserInfoAdapter mAdapter;
	private OnOpenListener mOnOpenListener;
	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;
	private int mWidth;

	public User(Context context, Activity activity, KXApplication application) {
		mContext = context;
		mWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		mActivity = activity;
		mKXApplication = application;
		// 绑定布局到当前View
		mUser = LayoutInflater.from(context).inflate(R.layout.user_info, null);
		mUserHead = LayoutInflater.from(context).inflate(
				R.layout.user_info_head, null);

		findViewById();
		setListener();
		init();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		
		mHead_Wallpager = (ImageView) mUserHead
				.findViewById(R.id.user_info_head_wallpager);
		mHead_Avatar = (ImageView) mUserHead
				.findViewById(R.id.user_info_head_avatar);
		mHead_Name = (TextView) mUserHead
				.findViewById(R.id.user_info_head_name);
		mHead_Gender = (ImageView) mUserHead
				.findViewById(R.id.user_info_head_gender);
		mHead_Constellation = (TextView) mUserHead
				.findViewById(R.id.user_info_head_constellation);
		mHead_Sig_Layout = (LinearLayout) mUserHead
				.findViewById(R.id.user_info_head_sig_layout);
		mHead_Sig = (TextView) mUserHead.findViewById(R.id.user_info_head_sig);
		mHead_About = (TextView) mUserHead
				.findViewById(R.id.user_info_head_about);
		mHead_Photo = (TextView) mUserHead
				.findViewById(R.id.user_info_head_photo);
		mHead_Diary = (TextView) mUserHead
				.findViewById(R.id.user_info_head_diary);
		mHead_Friends = (TextView) mUserHead
				.findViewById(R.id.user_info_head_friends);
		mHead_Friends_List = (LinearLayout) mUserHead
				.findViewById(R.id.user_info_head_friends_list);
		mHead_Friends_List_Count = (Button) mUserHead
				.findViewById(R.id.user_info_head_friends_list_count);
		
		mRefresh = (Button) mUser
				.findViewById(R.id.refresh);

		mUgcView = (View) mUser.findViewById(R.id.user_info_ugc);
		mUgcLayout = (RelativeLayout) mUgcView.findViewById(R.id.ugc_layout);
		mUgc = (ImageView) mUgcView.findViewById(R.id.ugc);
		mUgcBg = (ImageView) mUgcView.findViewById(R.id.ugc_bg);
		mUgcVoice = (ImageView) mUgcView.findViewById(R.id.ugc_voice);
		mUgcPhoto = (ImageView) mUgcView.findViewById(R.id.ugc_photo);
		mUgcRecord = (ImageView) mUgcView.findViewById(R.id.ugc_record);
		mUgcLbs = (ImageView) mUgcView.findViewById(R.id.ugc_lbs);

		mMenu = (Button) mUser.findViewById(R.id.user_info_menu);
		mDisplay = (ListView) mUser.findViewById(R.id.user_info_display);
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mUser.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mUser.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
		}
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		
		mRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mKXApplication.mMyVisitorsResults.clear();
				mKXApplication.mUserHomeResults.clear();
				getInfo();
				getVisitors();
				getHomeData();
			}
		});
		// 墙纸监听
		mHead_Wallpager.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到修改墙纸界面
				mActivity.startActivityForResult(new Intent(mContext,
						ChangeWallpagerActivity.class),
						ActivityForResultUtil.REQUESTCODE_CHANGEWALLPAGER);
			}
		});
		// 头像监听
		mHead_Avatar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new AlertDialog.Builder(mContext)
						.setTitle("请选择")
						.setItems(
								new String[] { "修改头像", "编辑我的资料", "设置为通讯录头像" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											new AlertDialog.Builder(mContext)
													.setTitle("修改头像")
													.setItems(
															new String[] {
																	"拍照上传",
																	"上传手机中的照片" },
															new DialogInterface.OnClickListener() {

																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	Intent intent = null;
																	switch (which) {
																	case 0:
																		intent = new Intent(
																				MediaStore.ACTION_IMAGE_CAPTURE);
																		File dir = new File(
																				KaiXinAddress.SDCARED_CAMERA);
																		if (!dir.exists()) {
																			dir.mkdirs();
																		}
																		mKXApplication.mUploadPhotoPath = KaiXinAddress.SDCARED_CAMERA
																				+ UUID.randomUUID()
																						.toString();
																		File file = new File(
																				mKXApplication.mUploadPhotoPath);
																		if (!file
																				.exists()) {
																			try {
																				file.createNewFile();
																			} catch (IOException e) {

																			}
																		}
																		intent.putExtra(
																				MediaStore.EXTRA_OUTPUT,
																				Uri.fromFile(file));
																		mActivity
																				.startActivityForResult(
																						intent,
																						ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA);
																		break;

																	case 1:
																		intent = new Intent(
																				Intent.ACTION_PICK,
																				null);
																		intent.setDataAndType(
																				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
																				"image/*");
																		mActivity
																				.startActivityForResult(
																						intent,
																						ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);
																		break;
																	}
																}
															})
													.setNegativeButton(
															"取消",
															new DialogInterface.OnClickListener() {

																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	dialog.cancel();
																}
															}).create().show();
											break;

										case 1:
											// 跳转到关于界面
											Intent intent = new Intent();
											intent.setClass(mContext,
													AboutActivity.class);
											intent.putExtra(
													"result",
													mKXApplication.mMyInfoResult);
											mContext.startActivity(intent);
											break;
										case 2:
											mContext.startActivity(new Intent(
													mContext,
													ContactsActivity.class));
											break;
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								}).create().show();
			}
		});
		// 签名监听
		mHead_Sig_Layout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到修改签名界面
				Intent intent = new Intent(mContext,
						EditSignatureActivity.class);
				intent.putExtra("signature", mKXApplication.mMyInfoResult.getSignature());
				mActivity.startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_EDITSIGNATURE);
			}
		});
		// 最近来访数量监听
		mHead_Friends_List_Count.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到最近来访列表界面
				mContext.startActivity(new Intent(mContext,
						VisitorsActivity.class));
			}
		});
		// 关于监听
		mHead_About.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到关于界面
				Intent intent = new Intent();
				intent.setClass(mContext, AboutActivity.class);
				intent.putExtra("result", (Parcelable)mKXApplication.mMyInfoResult);
				mActivity.startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_EDITNICKNAME);
			}
		});
		// 照片监听
		mHead_Photo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到照片界面
				mContext.startActivity(new Intent(mContext, PhotoActivity.class));
			}
		});
		// 日记监听
		mHead_Diary.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext, DiaryActivity.class));
			}
		});
		// 好友监听
		mHead_Friends.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, FriendsActivity.class);
				intent.putExtra("name", mKXApplication.mMyInfoResult.getName());
				mContext.startActivity(intent);
			}
		});
		// Path监听
		mUgcView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// 判断是否已经显示,显示则关闭并隐藏
				if (mUgcIsShowing) {
					mUgcIsShowing = false;
					UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc,
							500);
					return true;
				}
				return false;
			}
		});
		// Path监听
		mUgc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断是否显示,已经显示则隐藏,否则则显示
				mUgcIsShowing = !mUgcIsShowing;
				if (mUgcIsShowing) {
					UgcAnimations.startOpenAnimation(mUgcLayout, mUgcBg, mUgc,
							500);
				} else {
					UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc,
							500);
				}
			}
		});
		// Path 语音按钮监听
		mUgcVoice.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation anim = UgcAnimations.clickAnimation(500);
				anim.setAnimationListener(new AnimationListener() {

					public void onAnimationStart(Animation animation) {

					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationEnd(Animation animation) {
						mContext.startActivity(new Intent(mContext,
								PlayVoiceActivity.class));
						closeUgc();
					}
				});
				mUgcVoice.startAnimation(anim);
			}
		});
		// Path 拍照按钮监听
		mUgcPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation anim = UgcAnimations.clickAnimation(500);
				anim.setAnimationListener(new AnimationListener() {

					public void onAnimationStart(Animation animation) {

					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationEnd(Animation animation) {
						PhotoDialog();
						closeUgc();
					}
				});
				mUgcPhoto.startAnimation(anim);
			}
		});
		// Path 记录按钮监听
		mUgcRecord.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation anim = UgcAnimations.clickAnimation(500);
				anim.setAnimationListener(new AnimationListener() {

					public void onAnimationStart(Animation animation) {

					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationEnd(Animation animation) {
						mContext.startActivity(new Intent(mContext,
								WriteRecordActivity.class));
						closeUgc();
					}
				});
				mUgcRecord.startAnimation(anim);
			}
		});
		// Path 签到按钮监听
		mUgcLbs.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation anim = UgcAnimations.clickAnimation(500);
				anim.setAnimationListener(new AnimationListener() {

					public void onAnimationStart(Animation animation) {

					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationEnd(Animation animation) {
						mContext.startActivity(new Intent(mContext,
								CheckInActivity.class));
						closeUgc();
					}
				});
				mUgcLbs.startAnimation(anim);
			}
		});
	}
	
	

	/**
	 * 界面初始化
	 */
	private void init() {
		getInfo();
		getVisitors();
		getHomeData();
		mDisplay.addHeaderView(mUserHead);
		mAdapter = new UserInfoAdapter(mContext);
		mDisplay.setAdapter(mAdapter);
	}

	/**
	 * 获取用户资料
	 */
	private void getInfo() {
		if(!CommonUtils.isNetWorkConnected(mContext)){
			mKXApplication.mMyInfoResult = StorageUtil.getFriendInfoResult(mContext);
		}else{
			mKXApplication.mMyInfoResult = new FriendInfoResult();
			String json = CallService.getUserInfo("");
			try {
				JSONObject jsonObject = new JSONObject(json);
				JSONObject object = jsonObject.getJSONObject("data");
				mKXApplication.mMyInfoResult.setName(object.getString("name"));
				mKXApplication.mMyInfoResult.setAvatar(object.getString("avatar"));
				mKXApplication.mMyInfoResult.setGender(object.getInt("gender"));
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
				mKXApplication.mMyInfoResult.setDate(object.getString("date"));
	
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		// 界面的数据赋值
		mHead_Name.setText(mKXApplication.mMyInfoResult.getName());
//			mHead_Avatar.setImageBitmap(mKXApplication
//					.getAvatar(mKXApplication.mMyInfoResult.getAvatar()));
		ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ mKXApplication.mMyInfoResult.getAvatar(), mHead_Avatar, ImageUtil.getOption());
		mHead_Gender.setImageBitmap(Utils.getGender(
				mContext.getResources(),
				mKXApplication.mMyInfoResult.getGender()));
		mHead_Constellation.setText(mKXApplication.mMyInfoResult
				.getConstellation());
		
		String signature =mKXApplication.mMyInfoResult.getSignature();
		if(!StringUtil.isNull(signature)){
			mHead_Sig.setText(new TextUtil(mKXApplication)
				.replace(signature));
		}else{
			mHead_Sig.setText("本人很低调，还没签名！");
		}
		
		mHead_About.setText("关于");
		mHead_Photo.setText("照片 "
				+ mKXApplication.mMyInfoResult.getPhoto_count());
		mHead_Diary.setText("日记 "
				+ mKXApplication.mMyInfoResult.getDiary_count());
		mHead_Friends.setText("好友 "
				+ mKXApplication.mMyInfoResult.getFriend_count());
		mHead_Friends_List_Count.setText(mKXApplication.mMyInfoResult
				.getVisitor_count() + "");

		/**
		 * 原有的墙纸取消,采用随机的墙纸,这样保证每次进入都不一样,其他用户采用自己设定的墙纸显示,如注释掉的代码
		 * 
		 * mHead_Wallpager.setImageDrawable(mKXApplication
		 * .getTitleWallpager(mKXApplication.mMyInfoResult
		 * .getWallpager()));
		 */

		mHead_Wallpager.setImageBitmap(mKXApplication
				.getTitleWallpager(mKXApplication.mWallpagerPosition));
	}

	/**
	 * 获取用户最近来访
	 */
	private void getVisitors() {
		if(mKXApplication.mMyVisitorsResults.isEmpty()){
			String json;
			try {
				json = CallService.getVisitors(StorageUtil.getString(mContext, "userid"));
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray(
						"data");
				VisitorsResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new VisitorsResult();
					result.setUid(array.getJSONObject(i).getString("visitor_uid"));
					result.setName(array.getJSONObject(i).getString("visitor_name"));
					result.setAvatar(array.getJSONObject(i).getString("avatar"));
					result.setTime(array.getJSONObject(i).getString("time"));
					mKXApplication.mMyVisitorsResults.add(result);

					// 显示最近头像
					ImageView imageView = new ImageView(mContext);
					int widthAndHeight = (int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_DIP, 30, mContext
									.getResources().getDisplayMetrics());
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							widthAndHeight, widthAndHeight);
					imageView.setLayoutParams(params);
					imageView.setPadding(4, 4, 4, 4);
//					imageView.setImageBitmap(mKXApplication.getAvatar(result
//							.getAvatar()));
					ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ result
							.getAvatar(), imageView, ImageUtil.getOption());
					
					imageView.setTag(result);
					mHead_Friends_List.addView(imageView);
					mHead_Friends_List.invalidate();
					imageView.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							VisitorsResult result = (VisitorsResult) v.getTag();
							Intent intent = new Intent();
							intent.setClass(mContext, FriendInfoActivity.class);
							intent.putExtra("uid", result.getUid());
							intent.putExtra("name", result.getName());
							intent.putExtra("avatar", result.getAvatar());
							mContext.startActivity(intent);
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取用户的状态
	 */
	private void getHomeData() {
		if(mKXApplication.mUserHomeResults.isEmpty()){
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
					mKXApplication.mUserHomeResults.add(result);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改墙纸
	 */
	public void setWallpager() {
		mHead_Wallpager.setImageBitmap(mKXApplication
				.getTitleWallpager(mKXApplication.mWallpagerPosition));
	}

	/**
	 * 修改签名
	 * 
	 * @param arg0
	 *            修改的签名文本
	 */
	public void setSignature(String arg0) {
		mHead_Sig.setText(new TextUtil(mKXApplication).replace(arg0));
	}
	
	/**
	 * 修改昵称
	 * 
	 * @param arg0
	 *            修改的昵称文本
	 */
	public void setNickName(String arg0) {
		mHead_Name.setText(new TextUtil(mKXApplication).replace(arg0));
	}

	/**
	 * 修改头像
	 * 
	 * @param bitmap
	 *            修改的头像
	 */
	public void setAvatar(Bitmap bitmap) {
		mHead_Avatar.setImageBitmap(bitmap);
	}

	/**
	 * 获取Path菜单显示状态
	 * 
	 * @return 显示状态
	 */
	public boolean getUgcIsShowing() {
		return mUgcIsShowing;
	}

	/**
	 * 关闭Path菜单
	 */
	public void closeUgc() {
		mUgcIsShowing = false;
		UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc, 500);
	}

	/**
	 * 显示Path菜单
	 */
	public void showUgc() {
		if (mUgcView != null) {
			mUgcView.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 关闭Path菜单
	 */
	public void dismissUgc() {
		if (mUgcView != null) {
			mUgcView.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取用户首页界面
	 * 
	 * @return 用户首页界面的View
	 */
	public View getView() {
		return mUser;
	}

	/**
	 * 我的首页适配器
	 * 
	 * @author gyz
	 * 
	 */
	private class UserInfoAdapter extends BaseAdapter {
		private Context mContext;

		public UserInfoAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mKXApplication.mUserHomeResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mUserHomeResults.size();
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
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mWidth / 4 , mWidth / 4);
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
			final HomeResult result = mKXApplication.mUserHomeResults.get(position);
			if ("viewed".equals(result.getType())) {
				holder.viewed.setVisibility(View.VISIBLE);
				holder.photo.setVisibility(View.GONE);
				holder.viewed.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(mContext,
								HomeDiaryDetailActivity.class);
						if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mUserHomeResults.get(position).getEmail())){
							intent.putExtra("uid", mKXApplication.mUserHomeResults.get(position).getUid());
						}
						intent.putExtra("name", mKXApplication.mUserHomeResults.get(position).getName());
						intent.putExtra("result", (Serializable)mKXApplication.mUserHomeResults.get(position));
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
						if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mUserHomeResults.get(position).getEmail())){
							intent.putExtra("uid", mKXApplication.mUserHomeResults.get(position).getUid());
						}
						intent.putExtra("avatar", mKXApplication.mUserHomeResults.get(position).getAvatar());
						intent.putExtra("name", mKXApplication.mUserHomeResults.get(position).getName());
						intent.putExtra("result", (Serializable)mKXApplication.mUserHomeResults.get(position));
						mContext.startActivity(intent);
					}
				});
				
			}
			/*Diary result = mResults.get(position);
//			Bitmap avatar = PhotoUtil.toRoundCorner(BitmapFactory
//					.decodeResource(mContext.getResources(), R.drawable.head),
//					15);
//			holder.avatar.setImageBitmap(avatar);
			ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.avatar, ImageUtil.getOption());
			holder.name.setText(result.getUsername());
			holder.time.setText(result.getTime());
			holder.content.setText(new TextUtil(mKXApplication).replace(result.getTitle()));
			holder.like_count.setText(result.getLike_count()+ "");
			holder.comment_count.setText(result.getComment_count()+ "");
			holder.forward_count.setText(result.getComment_count()+ "");*/
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

	public void setOnOpenListener(OnOpenListener onOpenListener) {
//		mHead_Name.setText(mKXApplication.mMyInfoResult.getName());
		mOnOpenListener = onOpenListener;
	}

	private void PhotoDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("上传照片至OOXX");
		builder.setItems(new String[] { "拍照上传", "上传手机中的照片" },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File dir = new File(KaiXinAddress.SDCARED_CAMERA);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							mKXApplication.mUploadPhotoPath = KaiXinAddress.SDCARED_CAMERA
									+ UUID.randomUUID().toString();
							File file = new File(
									mKXApplication.mUploadPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							mActivity
									.startActivityForResult(
											intent,
											ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							mContext.startActivity(new Intent(mContext,
									PhoneAlbumActivity.class));
							break;
						}
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	
	public void fresh(){
		// 获取我的好友
		getInfo();
	}
}
