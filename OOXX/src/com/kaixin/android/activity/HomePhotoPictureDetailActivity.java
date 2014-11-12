package com.kaixin.android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.kaixin.android.anim.PhotoAnimations;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.AlbumResult;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.PhotoUtil;
import com.kaixin.android.view.HackyViewPager;
import com.kaixin.android.view.PhotoViewAttacher;
import com.kaixin.android.view.PhotoViewAttacher.OnPhotoTapListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 普通照片显示类
 * 
 * @author gyz
 * 
 */
@SuppressLint("ValidFragment")
public class HomePhotoPictureDetailActivity extends FragmentActivity {
	private LinearLayout mTopParent;
	private static LinearLayout mTop;
	private Button mBack;
	private TextView mTitle;
	private ImageView mArrow;
	private Button mToPeople;
	private Button mToGallery;
	private TextView mDescription;
	private LinearLayout mMore;
	private TextView mMoreTitle;
	private GridView mMoreDisplay;
	private static RelativeLayout mBottom;
	private ImageButton mSaveas;
	private Button mComment;
	private ImageButton mLike;
	private HackyViewPager mPager;

	private int mUid;// 照片所属用户的ID
	private String mName;// 照片所属用户的姓名
	private String mAvatar;// 照片所属用户的头像
	private int mTotalCount;// 照片总数量
	private int mCurrentPosition = 0;// 照片当前的编号
	private HomeResult mHomePhotoResult; // 照片数据
	private boolean mMoreIsShowing;// 是否显示更多相册
	private boolean mIsLike;// 是否赞过
	private List<AlbumResult> mMorePhotoResults = new ArrayList<AlbumResult>();// 更多相册的显示数据
	private int mAlbumId;
	private static final String STATE_POSITION = "STATE_POSITION";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photopicturedetail_activity);
		findViewById();
		setListener();
		init();
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		mPager.setCurrentItem(mCurrentPosition);
	}

	private void findViewById() {
		mTopParent = (LinearLayout) findViewById(R.id.photopicturedetail_top_parent);
		mTop = (LinearLayout) findViewById(R.id.photopicturedetail_top);
		mBack = (Button) findViewById(R.id.photopicturedetail_back);
		mTitle = (TextView) findViewById(R.id.photopicturedetail_title);
		mArrow = (ImageView) findViewById(R.id.photopicturedetail_arrow);
		mToPeople = (Button) findViewById(R.id.photopicturedetail_topeople);
		mToGallery = (Button) findViewById(R.id.photopicturedetail_togallery);
		mToGallery.setVisibility(View.GONE);
		mArrow.setVisibility(View.GONE);
		mToPeople.setVisibility(View.GONE);
		mDescription = (TextView) findViewById(R.id.photopicturedetail_description);
		mMore = (LinearLayout) findViewById(R.id.photopicturedetail_more);
		mMoreTitle = (TextView) findViewById(R.id.photopicturedetail_more_title);
		mMoreDisplay = (GridView) findViewById(R.id.photopicturedetail_more_display);
		mBottom = (RelativeLayout) findViewById(R.id.photopicturedetail_bottom);
		mSaveas = (ImageButton) findViewById(R.id.photopicturedetail_saveas);
		mComment = (Button) findViewById(R.id.photopicturedetail_comment);
		mLike = (ImageButton) findViewById(R.id.photopicturedetail_like);
		mPager = (HackyViewPager) findViewById(R.id.photopicturedetail_gallery);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断是否显示更多相册,如果显示则隐藏,否则关闭当前界面
				if (mMoreIsShowing) {
					mMoreIsShowing = false;
					PhotoAnimations.startCloseAnimation(mTopParent, mMore,
							mDescription, mArrow);
				} else {
					finish();
				}
			}
		});
		mTitle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断更多相册是否显示,显示则隐藏,隐藏则显示
				mMoreIsShowing = !mMoreIsShowing;
			}
		});
		mToPeople.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断更多相册是否显示,显示则隐藏,否则跳转到该照片所属用户的资料界面
				if (mMoreIsShowing) {
					mMoreIsShowing = false;
					PhotoAnimations.startCloseAnimation(mTopParent, mMore,
							mDescription, mArrow);
				} else {
					// 跳转到好友资料界面,并传递数据
					Intent intent = new Intent();
					intent.setClass(HomePhotoPictureDetailActivity.this,
							FriendInfoActivity.class);
					intent.putExtra("uid", mUid);
					intent.putExtra("name", mName);
					intent.putExtra("avatar", mAvatar);
					startActivity(intent);
				}

			}
		});
		mToGallery.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断更多相册是否显示,显示则隐藏,否则关闭当前界面
				if (mMoreIsShowing) {
					mMoreIsShowing = false;
//					PhotoAnimations.startCloseAnimation(mTopParent, mMore,
//							mDescription, mArrow);
				} else {
					// 关闭当前界面
					finish();
				}
			}
		});
		mSaveas.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获取当前照片的编号
//				int image = mPhotoResult.get(mCurrentPosition)
//						.getImage();
				// 获取当前照片
				Bitmap bitmap = CallService.getPhoto(mAlbumId, mHomePhotoResult.getPhoto().get(mCurrentPosition), false, String.valueOf(mUid));
				// 保存当前照片
				boolean result = PhotoUtil.saveToSDCard(bitmap);
				if (result) {
					Toast.makeText(HomePhotoPictureDetailActivity.this,
							"已保存到/sdcard/KaiXin/download/下", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(HomePhotoPictureDetailActivity.this,
							"保存失败,请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mComment.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到评论内容显示界面,并传递照片数据
				Intent intent = new Intent();
				intent.setClass(HomePhotoPictureDetailActivity.this,
						HomePhotoCommentDetailActivity.class);
				intent.putExtra("photoId", mHomePhotoResult.getMessageid());
				intent.putExtra("result",
						(Serializable)mHomePhotoResult);
//				intent.putExtra("result", mMorePhotoResults.get(mCurrentPosition));
				startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_COMMENT_COUNT);
			}
		});
		mLike.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断是否赞过,如果赞过提示信息,否则修改界面显示内容
				if (!mIsLike) {
					mIsLike = true;
					mLike.setImageResource(R.drawable.photo_like_disabled);
					CallService.setPhotoLike(mHomePhotoResult.getMessageid());
					Toast.makeText(
							HomePhotoPictureDetailActivity.this,
							"我和"+ mHomePhotoResult.getLike_count()
											 + "个人觉得挺赞的",
							Toast.LENGTH_SHORT).show();
					mHomePhotoResult.setLike_count(mHomePhotoResult.getLike_count() + 1);
					Constants.isAddFriend = true;
					handler.sendEmptyMessageDelayed(0, 500);
				} else {
					Toast.makeText(HomePhotoPictureDetailActivity.this, "您已经赞过了",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				// 滑动时修改显示内容
				mCurrentPosition = position;
				changeContent();
			}

		});
		mMoreDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转到照片列表显示界面,并传递信息
				Intent intent = new Intent();
				intent.setClass(HomePhotoPictureDetailActivity.this,
						PhotoListActivity.class);
				intent.putExtra("uid", String.valueOf(mUid));
				intent.putExtra("name", mName);
				intent.putExtra("avatar", mAvatar);
				intent.putExtra("result", (Serializable)mMorePhotoResults);
				startActivity(intent);
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void init() {
		// 获取照片所属用户的ID、姓名、头像、照片数据、当前照片的编号
		mUid = getIntent().getIntExtra("uid", -1);
		mName = getIntent().getStringExtra("name");
		mAvatar = getIntent().getStringExtra("avatar");
		mHomePhotoResult = (HomeResult)getIntent().getSerializableExtra("result");
		// 获得照片总数量
		mTotalCount = mHomePhotoResult.getPhoto().size();
		// 修改显示的内容
		changeContent();
		// 添加适配器
		mPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager(), mHomePhotoResult.getPhoto()));
	}

	/**
	 * 修改显示内容
	 */
	private void changeContent() {
		mTitle.setText(mCurrentPosition + 1 + "/" + mTotalCount);
		mDescription.setText(mHomePhotoResult.getTitle());
		mComment.setText(mHomePhotoResult.getComment_count()
				+ "");
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mIsLike = false;
			mLike.setImageResource(R.drawable.photoview_like_button);
			if (mCurrentPosition + 1 >= mTotalCount) {
				/*mMoreIsShowing = true;
				PhotoAnimations.startOpenAnimation(mTopParent, mMore,
						mDescription, mArrow);*/
			} else {
				mPager.setCurrentItem(mCurrentPosition + 1);
			}
		}
	};
	
	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public List<String> fileList;

		public ImagePagerAdapter(FragmentManager fm,  List<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return ImageDetailFragment.newInstance(url);
		}

	}
	

	public void onBackPressed() {
		// 判断是更多相册是否显示,显示则隐藏,否则关闭当前界面
		if (mMoreIsShowing) {
			mMoreIsShowing = false;
			PhotoAnimations.startCloseAnimation(mTopParent, mMore,
					mDescription, mArrow);
		} else {
			// 关闭当前界面
			finish();
		}
	}

	static class ImageDetailFragment extends Fragment {
		private String mImageUrl;
		private ImageView mImageView;
		private ProgressBar progressBar;
		private PhotoViewAttacher mAttacher;

		public static ImageDetailFragment newInstance(String imageUrl) {
			final ImageDetailFragment f = new ImageDetailFragment();

			final Bundle args = new Bundle();
			args.putString("url", imageUrl);
			f.setArguments(args);

			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
			mImageView = (ImageView) v.findViewById(R.id.image);
			mAttacher = new PhotoViewAttacher(mImageView);
			
			mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
				
				@Override
				public void onPhotoTap(View arg0, float arg1, float arg2) {
//					getActivity().finish();
					if (mTop.isShown() && mBottom.isShown()) {
						PhotoAnimations.startGoneAnimation(mTop, mBottom);
					} else {
						PhotoAnimations.startVisibleAnimation(mTop, mBottom);
					}
				}
			});
			
			progressBar = (ProgressBar) v.findViewById(R.id.loading);
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			ImageLoader.getInstance().displayImage(Constants.getImageUrl() + mImageUrl, mImageView, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					progressBar.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					System.out.println(imageUri);
					String message = null;
					switch (failReason.getType()) {
					case IO_ERROR:
						message = "下载错误";
						break;
					case DECODING_ERROR:
						message = "图片无法显示";
						break;
					case NETWORK_DENIED:
						message = "网络有问题，无法下载";
						break;
					case OUT_OF_MEMORY:
						message = "图片太大无法显示";
						break;
					case UNKNOWN:
						message = "未知的错误";
						break;
					}
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					progressBar.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					progressBar.setVisibility(View.GONE);
					mAttacher.update();
				}
			});
			
			
		}

	}
	
	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 在评论回复上评论返回数据
		case ActivityForResultUtil.REQUESTCODE_COMMENT_COUNT:
			if (resultCode == RESULT_OK) {
				// 添加回复的数据
				Bundle bundle = data.getBundleExtra("result");
				mComment.setText(bundle.getInt("count") + "");
			}
			break;
		}
	}
}
