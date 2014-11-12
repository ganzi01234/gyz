package com.kaixin.android.activity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.result.FriendInfoResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;
import com.kaixin.android.view.MyDateTimePickerDialog;
import com.kaixin.android.view.MyDateTimePickerDialog.OnDateTimeSetListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 资料关于类
 * 
 * @author gyz
 * 
 */
public class AboutActivity extends KXActivity{

	private Button mBack;
	private TextView mTitle;
	private Button mSubmit;
	private ImageButton mAvatar;
	private Button mAvatarChange;
	private EditText mName;
	private TextView mSignature;
	private TextView mGender;
	private TextView mDate;
	private EditText mTelephone;
	private EditText mAddress;
	private ImageView mDateIcon;
	private ImageView mAddressIcon;
	private ImageView mTelephoneIcon;

	private String mUid;// 当前查看的用户Id
	private FriendInfoResult mResult;// 当前查看的用户的资料数据
	public static final String DATEPICKER_TAG = "datepicker";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.about_back);
		mTitle = (TextView) findViewById(R.id.about_title);
		mSubmit = (Button) findViewById(R.id.about_submit);
		mAvatar = (ImageButton) findViewById(R.id.about_avatar);
		mAvatarChange = (Button) findViewById(R.id.about_avatar_change);
		mName = (EditText) findViewById(R.id.about_name);
		mSignature = (TextView) findViewById(R.id.about_signature);
		mGender = (TextView) findViewById(R.id.about_gender);
		mDate = (TextView) findViewById(R.id.about_date);
		mTelephone = (EditText) findViewById(R.id.edt_telephone);
		mAddress = (EditText) findViewById(R.id.edt_address);
//		mConstellation = (TextView) findViewById(R.id.about_constellation);
		mDateIcon = (ImageView) findViewById(R.id.about_date_icon);
		mAddressIcon = (ImageView) findViewById(R.id.about_address_icon);
		mTelephoneIcon = (ImageView) findViewById(R.id.about_telephone_icon);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();// 关闭当前界面
			}
		});
		mAvatarChange.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PhotoDialog();
			}
		});
		
		mGender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CallService.modifyUserInfo(mName.getText().toString(), 
						mDate.getText().toString(), 
						mTelephone.getText().toString(),
						String.valueOf(Utils.getGenderNum(mGender.getText().toString())), 
						mAddress.getText().toString());
				Constants.isRefreshUserInfo = true;
//				KXApplication.getInstance().mMyInfoResult.setName(mName.getText().toString());
				//关闭当前界面,并返回更新信息
				Intent intent = new Intent();
				intent.putExtra("nickname", mName.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		mDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
				if(!StringUtil.isNull(mDate.getText().toString())){
					DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = fmt.parse(mDate.getText().toString());
						mycalendar.setTime(date);//为Calendar对象设置时间为生日时间
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					Date mydate = new Date(); // 获取当前日期Date对象
					mycalendar.setTime(mydate);//为Calendar对象设置时间为当前日期
				}
				
				new MyDateTimePickerDialog(AboutActivity.this, mycalendar, new OnDateTimeSetListener() {

					@Override
					public void onDateTimeSet(int year, int monthOfYear,
							int dayOfMonth) {
						
						mDate.setText(year + "-" + monthOfYear + "-"
								+ dayOfMonth);
					}

				}).show();
			}
		});
	}
	
	private void init() {
		
		mUid = getIntent().getStringExtra("uid");// 接收传递过来的用户ID
		mResult = (FriendInfoResult)getIntent().getParcelableExtra("result");// 接收传递过来的用资料
		// 当Id不存在时为当前登录用户,否则则是其他用户,根据用户的不同,显示不同界面效果
		if (mUid == null) {
			mBack.setText("我的首页");
			mTitle.setText("我的资料");
			mSubmit.setVisibility(View.VISIBLE);
			mSubmit.setText("提交");
			mAvatarChange.setVisibility(View.VISIBLE);
			mDateIcon.setVisibility(View.VISIBLE);
			mAddressIcon.setVisibility(View.VISIBLE);
			mTelephoneIcon.setVisibility(View.VISIBLE);
		} else {
			mBack.setText(mResult.getName());
			mTitle.setText(mResult.getName() + "的资料");
			mSubmit.setVisibility(View.GONE);
			mAvatarChange.setVisibility(View.INVISIBLE);
			mDateIcon.setVisibility(View.GONE);
			mAddressIcon.setVisibility(View.GONE);
			mTelephoneIcon.setVisibility(View.GONE);
		}
		// 填充界面数据
//		mAvatar.setImageBitmap(mKXApplication.getAvatar(mResult.getAvatar()));
		ImageLoader.getInstance().displayImage(Constants.getImageUrl()+ mResult.getAvatar(), mAvatar);
		mName.setText(mResult.getName());
		String signature = mResult.getSignature();
		if(!StringUtil.isNull(signature)){
			mSignature.setText(new TextUtil(mKXApplication)
				.replace(signature));
		}else{
			mSignature.setText("本人很低调，还没签名！");
		}
		mGender.setText(Utils.getGender(mResult.getGender()));
		mDate.setText(mResult.getDate());
//		mConstellation.setText(mResult.getConstellation());
	}
	
	private void PhotoDialog() {
		AlertDialog.Builder builder = new Builder(this);
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
							KXApplication.getInstance().mUploadPhotoPath = KaiXinAddress.SDCARED_CAMERA + 
										UUID.randomUUID().toString();
							File file = new File(
									KXApplication.getInstance().mUploadPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							startActivityForResult(
											intent,
											ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							startActivity(new Intent(AboutActivity.this,
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
	
	public void onBackPressed() {
		// 关闭当前界面,并返回更新信息
		setResult(RESULT_CANCELED);
		finish();
	}

}
