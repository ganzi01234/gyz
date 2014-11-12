package com.kaixin.android.activity;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.menu.BuyAndSellSlaves;
import com.kaixin.android.menu.Desktop;
import com.kaixin.android.menu.Desktop.onChangeViewListener;
import com.kaixin.android.menu.Friends;
import com.kaixin.android.menu.Gifts;
import com.kaixin.android.menu.Home;
import com.kaixin.android.menu.Lbs;
import com.kaixin.android.menu.Message;
import com.kaixin.android.menu.Photo;
import com.kaixin.android.menu.Recommend;
import com.kaixin.android.menu.User;
import com.kaixin.android.menu.Viewed;
import com.kaixin.android.service.UpdateApp;
import com.kaixin.android.service.UploadAvatarService;
import com.kaixin.android.ui.base.FlipperLayout;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.ui.base.FlipperLayout.onUgcDismissListener;
import com.kaixin.android.ui.base.FlipperLayout.onUgcShowListener;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.PhotoUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.ViewUtil;

public class MainActivity extends KXActivity implements OnOpenListener {
	/**
	 * 当前显示内容的容器(继承于ViewGroup)
	 */
	private FlipperLayout mRoot;
	/**
	 * 菜单界面
	 */
	private Desktop mDesktop;
	/**
	 * 用户首页界面
	 */
	private User mUserInfo;
	/**
	 * 内容首页界面
	 */
	private Home mHome;
	/**
	 * 消息首页界面
	 */
	private Message mMessage;
	/**
	 * 好友首页界面
	 */
	private Friends mFriends;
	/**
	 * 好友买卖界面
	 */
	private BuyAndSellSlaves mBuyAndSellSlaves;
	/**
	 * 照片首页界面
	 */
	private Photo mPhoto;
	/**
	 * 转帖首页界面
	 */
	private Viewed mViewed;
	/**
	 * 礼物首页界面
	 */
	private Gifts mGifts;
	/**
	 * 游戏首页界面
	 */
	private Recommend mRecommend;
	/**
	 * 附近首页界面
	 */
	private Lbs mLbs;
	/**
	 * 当前显示的View的编号
	 */
	private int mViewPosition;
	/**
	 * 退出时间
	 */
	private long mExitTime;
	/**
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;
	/**
	 * 
	 */
	public static Activity mInstance;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private boolean isFlag;

	private static final String TAG = "LBS";
	private static final int SENSOR_SHAKE = 10;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UpdateApp app = new UpdateApp(this);
		app.checkUpdate();
		
		CallService.setUsername(StorageUtil.getString(this, "username"));
		CallService.setPasswordMD5(Encrypter.md5(StorageUtil.getString(this, "password")));
		/**
		 * 创建容器,并设置全屏大小
		 */
		mRoot = new FlipperLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRoot.setLayoutParams(params);
		/**
		 * 创建菜单界面和内容首页界面,并添加到容器中,用于初始显示
		 */
		mDesktop = new Desktop(this, this, mKXApplication);
		mHome = new Home(this, this, mKXApplication);
		mRoot.addView(mDesktop.getView(), params);
		mRoot.addView(mHome.getView(), params);
		setContentView(mRoot);
		setListener();
		mInstance=this;
		
		
	}
	
	/**
	 * 重力感应监听
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
//			Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);
			// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			int medumValue = 16;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
				vibrator.vibrate(200);
				android.os.Message msg = new android.os.Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	/**
	 * 动作执行
	 */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SENSOR_SHAKE:
					mLbs.fresh();
					break;
				}
		}

	};

	/**
	 * UI事件监听
	 */
	private void setListener() {
		mHome.setOnOpenListener(this);
		/**
		 * 监听菜单界面切换显示内容(onChangeViewListener接口在Desktop中定义)
		 */
		mDesktop.setOnChangeViewListener(new onChangeViewListener() {

			public void onChangeView(int arg0) {
				mViewPosition = arg0;
				if (sensorManager != null) {// 取消监听器
					sensorManager.unregisterListener(sensorEventListener);
				}
				switch (arg0) {
				case ViewUtil.USER:
					if (mUserInfo == null) {
						mUserInfo = new User(MainActivity.this,
								MainActivity.this, mKXApplication);
						mUserInfo.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mUserInfo.getView());
					break;
				case ViewUtil.HOME:
					mRoot.close(mHome.getView());
					break;
				case ViewUtil.MESSAGE:
					if (mMessage == null) {
						mMessage = new Message(MainActivity.this, mKXApplication);
						mMessage.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mMessage.getView());
					break;
				case ViewUtil.BUY_SELL:
					if (mBuyAndSellSlaves == null) {
						mBuyAndSellSlaves = new BuyAndSellSlaves(MainActivity.this,
								mKXApplication);
						mBuyAndSellSlaves.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mBuyAndSellSlaves.getView());
					break;
				case ViewUtil.FRIENDS:
					if (mFriends == null) {
						mFriends = new Friends(MainActivity.this,
								mKXApplication);
						mFriends.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mFriends.getView());
					break;
				case ViewUtil.PHOTO:
					if (mPhoto == null) {
						mPhoto = new Photo(MainActivity.this, mKXApplication,
								mScreenWidth);
						mPhoto.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mPhoto.getView());
					break;
				case ViewUtil.VIEWED:
					if (mViewed == null) {
						mViewed = new Viewed(MainActivity.this, mKXApplication);
						mViewed.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mViewed.getView());
					break;
				case ViewUtil.GIFTS:
					/*if (mGifts == null) {
						mGifts = new Gifts(MainActivity.this, mKXApplication);
						mGifts.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mGifts.getView());*/
					MessageUtil.showMsg(MainActivity.this, "功能开发中..");
					break;
				case ViewUtil.RECOMMEND:
					if (mRecommend == null) {
						mRecommend = new Recommend(MainActivity.this,
								mKXApplication);
						mRecommend.setOnOpenListener(MainActivity.this);
					}
					mRoot.close(mRecommend.getView());
					break;
				case ViewUtil.LBS:
					if (mLbs == null) {
						mLbs = new Lbs(MainActivity.this, mKXApplication);
						mLbs.setOnOpenListener(MainActivity.this);
						sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
						vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						if (sensorManager != null) {// 注册监听器
							sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
							// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
						}
					}
					mRoot.close(mLbs.getView());
					break;
				default:
					break;
				}
			}
		});
		/**
		 * 隐藏path菜单
		 */
		mRoot.setOnUgcDismissListener(new onUgcDismissListener() {

			public void dismiss() {
				switch (mViewPosition) {
				case ViewUtil.USER:
					mUserInfo.dismissUgc();
					break;

				case ViewUtil.HOME:
					mHome.dismissUgc();
					break;
				}
			}
		});
		/**
		 * 显示path菜单
		 */
		mRoot.setOnUgcShowListener(new onUgcShowListener() {

			public void show() {
				switch (mViewPosition) {
				case ViewUtil.USER:
					mUserInfo.showUgc();
					break;

				case ViewUtil.HOME:
					mHome.showUgc();
					break;
				}
			}
		});
		
		findViewById(R.id.warnning_layout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent wifiSettingsIntent = new Intent(Settings.ACTION_SETTINGS); 
				startActivity(wifiSettingsIntent); 
			}
		});
	}
	
	

	@Override
	protected void onPause() {
		super.onPause();
		if (sensorManager != null) {// 取消监听器
			sensorManager.unregisterListener(sensorEventListener);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			findViewById(R.id.warnning_layout).setVisibility(View.GONE);
		}
		if(mFriends != null && Constants.isAddFriend){
			mFriends.fresh();
		}
		if(mHome != null && Constants.isAddFriend){
			mHome.fresh();
		}
		if(mUserInfo != null && Constants.isRefreshUserInfo){
			mUserInfo.fresh();
			Constants.isRefreshUserInfo = false;
		}
		Constants.isAddFriend = false;
		if(mHome != null){
			mHome.showUnreadMsg();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		/**
		 * 切换墙纸时回调此方法,通知菜单界面和用户界面修改墙纸
		 */
		case ActivityForResultUtil.REQUESTCODE_CHANGEWALLPAGER:
			if (resultCode == RESULT_OK) {
				mDesktop.setWallpager();
				mUserInfo.setWallpager();
			}
			break;
		/**
		 * 修改签名时回调此方法,通知菜单界面和用户界面修改签名
		 */
		case ActivityForResultUtil.REQUESTCODE_EDITSIGNATURE:
			if (resultCode == RESULT_OK) {
				String arg0 = data.getStringExtra("signature");
				CallService.modifySign(arg0);
				mDesktop.setSignature(arg0);
				mUserInfo.setSignature(arg0);
			}
			break;
		/**
		 * 修改昵称时回调此方法,通知菜单界面和用户界面修改昵称
		 */
		case ActivityForResultUtil.REQUESTCODE_EDITNICKNAME:
			if (resultCode == RESULT_OK) {
				String arg0 = data.getStringExtra("nickname");
				mDesktop.setNickName(arg0);
				mUserInfo.setNickName(arg0);
			}
			break;
		/**
		 * 通过照相修改头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA:
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				File file = new File(mKXApplication.mUploadPhotoPath);
				startPhotoZoom(Uri.fromFile(file));
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
			}
			break;
		/**
		 * 通过本地修改头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION:
			Uri uri = null;
			if (data == null) {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				uri = data.getData();
				startPhotoZoom(uri);
			} else {
				Toast.makeText(this, "照片获取失败", Toast.LENGTH_SHORT).show();
			}
			break;
		/**
		 * 裁剪修改的头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP:
			if (data == null) {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				return;
			} else {
				saveCropPhoto(data);
			}
			break;
		/**
		 * 通过照相上传图片
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA:
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ImageFilterActivity.class);
				String path = PhotoUtil.saveToLocal(PhotoUtil.createBitmap(
						mKXApplication.mUploadPhotoPath, mScreenWidth,
						mScreenHeight));
				intent.putExtra("path", path);
				startActivity(intent);
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	/**
	 * 系统裁剪照片
	 * 
	 * @param uri
	 */
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent,
				ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP);
	}

	/**
	 * 保存裁剪的照片
	 * 
	 * @param data
	 */
	private void saveCropPhoto(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			bitmap = PhotoUtil.toRoundCorner(bitmap, 15);
			
			if (bitmap != null) {
				uploadPhoto(bitmap);
			}
		} else {
			Toast.makeText(this, "获取裁剪照片错误", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 更新头像
	 */
	private void uploadPhoto(Bitmap bitmap) {
		String path = PhotoUtil.saveToLocal(bitmap);
		Intent intent = new Intent(this, UploadAvatarService.class);
		intent.putExtra("photoPath", path);
		intent.putExtra("date", new Date(new java.util.Date().getTime()).toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);
		
		mUserInfo.setAvatar(bitmap);
		mDesktop.setAvatar(bitmap);
	}

	/**
	 * 返回键监听
	 */
	public void onBackPressed() {
		/**
		 * 如果界面的path菜单没有关闭时,先将path菜单关闭,否则则判断两次返回时间间隔,小于两秒则退出程序
		 */
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_OPEN) {
			if (mDesktop.getUgcIsShowing()) {
				mDesktop.closeUgc();
			} else {
				exit();
			}
		} else {
			switch (mViewPosition) {
			case ViewUtil.USER:
				if (mUserInfo.getUgcIsShowing()) {
					mUserInfo.closeUgc();
				} else {
					exit();
				}
				break;
			case ViewUtil.HOME:
				if (mHome.getUgcIsShowing()) {
					mHome.closeUgc();
				} else {
					exit();
				}
				break;
			default:
				exit();
				break;
			}

		}

	}

	/**
	 * 判断两次返回时间间隔,小于两秒则退出程序
	 */
	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回键,可直接退出程序", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
//			EMChatManager.getInstance().logout();
			mKXApplication.closeLocationTask(); // 开启定时的定位线程
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			
		}
	}

	public void open() {
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}
	}
}
