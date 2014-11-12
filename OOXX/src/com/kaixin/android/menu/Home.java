package com.kaixin.android.menu;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.activity.ChatActivity;
import com.kaixin.android.activity.CheckInActivity;
import com.kaixin.android.activity.FriendInfoActivity;
import com.kaixin.android.activity.HomeDiaryDetailActivity;
import com.kaixin.android.activity.HomePhotoPictureDetailActivity;
import com.kaixin.android.activity.PhoneAlbumActivity;
import com.kaixin.android.activity.VoiceActivity;
import com.kaixin.android.activity.WriteRecordActivity;
import com.kaixin.android.anim.UgcAnimations;
import com.kaixin.android.common.Constants;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.result.ConversationResult;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sharegroup.jiguang.http.HttpCallBack;
import com.sharegroup.jiguang.http.HttpSyncClient;

/**
 * 菜单首页类
 * 
 * @author gyz
 * 
 */
public class Home {
	private Context mContext;
	private Activity mActivity;
	private KXApplication mKXApplication;
	private View mHome;

	private Button mMenu;
	private RelativeLayout mTopLayout;
	private TextView mTopText;
	private Button mRefresh;
	private ZrcListView mDisplay;

	private View mUgcView;
	private RelativeLayout mUgcLayout;
	private ImageView mUgc;
	private ImageView mUgcBg;
	private ImageView mUgcVoice;
	private ImageView mUgcPhoto;
	private ImageView mUgcRecord;
	private ImageView mUgcLbs;
	private OnOpenListener mOnOpenListener;

	private String[] mPopupWindowItems = { "好友动态", "最近联系" };
	private PopupWindow mPopupWindow;
	private View mPopView;
	private ListView mPopDisplay;
	private PopupWindowAdapter popupAdapter;
	
	private Handler mHandler;
	SimpleHeader header;
	SimpleFooter footer;
	
	private int pageCount = 50;
	private int page = 1;
	private ProgressDialog dialog;

	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;
	private HomeAdapter homeAdapter;
	private ChatHistoryAdapter chatHistoryAdapter;
	private int mWidth;
//	private PullToRefreshView mPullRefresh;
	public static TextView mMsgNumber;
	public static boolean isRead = false;

	public Home(Context context, Activity activity, KXApplication application) {
		mContext = context;
		mWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		mActivity = activity;
		mKXApplication = application;
		mHome = LayoutInflater.from(context).inflate(R.layout.home, null);
		mPopView = LayoutInflater.from(context).inflate(
				R.layout.home_popupwindow, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mHome.findViewById(R.id.home_menu);
		mTopLayout = (RelativeLayout) mHome.findViewById(R.id.home_top_layout);
		mTopText = (TextView) mHome.findViewById(R.id.home_top_text);
		mRefresh = (Button) mHome.findViewById(R.id.home_refresh);
		mDisplay = (ZrcListView) mHome.findViewById(R.id.home_display);
		mUgcView = (View) mHome.findViewById(R.id.home_ugc);
		mUgcLayout = (RelativeLayout) mUgcView.findViewById(R.id.ugc_layout);
		mUgc = (ImageView) mUgcView.findViewById(R.id.ugc);
		mUgcBg = (ImageView) mUgcView.findViewById(R.id.ugc_bg);
		mUgcVoice = (ImageView) mUgcView.findViewById(R.id.ugc_voice);
		mUgcPhoto = (ImageView) mUgcView.findViewById(R.id.ugc_photo);
		mUgcRecord = (ImageView) mUgcView.findViewById(R.id.ugc_record);
		mUgcLbs = (ImageView) mUgcView.findViewById(R.id.ugc_lbs);
		mPopDisplay = (ListView) mPopView
				.findViewById(R.id.home_popupwindow_display);
		mMsgNumber = (TextView) mHome
				.findViewById(R.id.unread_msg_number);
		if(!StringUtil.isNull(StorageUtil.getString(mContext, "hasUnreadMsg"))){
			mMsgNumber.setVisibility(View.VISIBLE);
		}else{
			mMsgNumber.setVisibility(View.GONE);
		}
		
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mHome.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mHome.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
		}
//		mPullRefresh = (PullToRefreshView) mHome
//				.findViewById(R.id.pull_refresh_view);

	}
	
	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mTopLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示菜单
				initPopupWindow();
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				getHome();
				getNewHome();
			}
		});
		
		mDisplay.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			
			@Override
			public void onItemClick(ZrcListView parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String type = mKXApplication.mMyHomeResults.get(position).getType();
				if("photo".equals(type)){
					Intent intent = new Intent();
					intent.setClass(mContext,
							HomePhotoPictureDetailActivity.class);
					intent.putExtra("avatar", mKXApplication.mMyHomeResults.get(position).getAvatar());
					if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mMyHomeResults.get(position).getEmail())){
						intent.putExtra("uid", mKXApplication.mMyHomeResults.get(position).getUid());
					}
					intent.putExtra("name", mKXApplication.mMyHomeResults.get(position).getName());
					intent.putExtra("result", (Serializable)mKXApplication.mMyHomeResults.get(position));
					intent.putExtra("position", position);
					mContext.startActivity(intent);
				}else if("viewed".equals(type)){
					Intent intent = new Intent();
					intent.setClass(mContext,
							HomeDiaryDetailActivity.class);
					if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mMyHomeResults.get(position).getEmail())){
						intent.putExtra("uid", String.valueOf(mKXApplication.mMyHomeResults.get(position).getUid()));
					}
					intent.putExtra("name", mKXApplication.mMyHomeResults.get(position).getName());
					intent.putExtra("result", (Serializable)mKXApplication.mMyHomeResults.get(position));
					intent.putExtra("position", position);
					mContext.startActivity(intent);
				}
			}
		});
		
		mPopDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 这里只更改了标题栏,先不做数据的切换,因为数据有限
				mPopupWindow.dismiss();
				mTopText.setText(mPopupWindowItems[arg2]);
				if(1 == arg2){
					mDisplay.setHeadable(null);
					mKXApplication.mMyChatHistory = mKXApplication.mSaveConversationDao.queryAllConversation();
					mDisplay.setAdapter(new ChatHistoryAdapter());
					mMsgNumber.setVisibility(View.GONE);
					StorageUtil.saveString(mContext, "hasUnreadMsg", "");
					popupAdapter.notifyDataSetChanged();
					mDisplay.setOnItemClickListener(new ZrcListView.OnItemClickListener() {

						@Override
						public void onItemClick(ZrcListView parent, View view, int position, long id) {
							Intent intent = new Intent();
							intent.setClass(mContext, ChatActivity.class);
							intent.putExtra("email", mKXApplication.mMyChatHistory.get(position).getEmail());
							intent.putExtra("name", mKXApplication.mMyChatHistory.get(position).getName());
							intent.putExtra("avatar", mKXApplication.mMyChatHistory.get(position).getAvatar());
							mContext.startActivity(intent);
							isRead = true;
						}
					});
					mDisplay.setOnItemLongClickListener(new ZrcListView.OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(ZrcListView parent, View view, final int position, long id) {
							Builder builder = new AlertDialog.Builder(mContext);
							builder.setTitle("删除消息").setMessage("确定删除本会话？");
							builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									mKXApplication.mSaveConversationDao.deleteConversation(mKXApplication.mMyChatHistory.get(position));
									mKXApplication.mMyChatHistory.remove(position);
									chatHistoryAdapter.notifyDataSetChanged();
									dialog.dismiss();
								}
							});
							builder.setNegativeButton("取消", null);
							builder.show();
							
							return false;
						}
					});
				}else{
					mDisplay.setHeadable(header);
					mDisplay.setAdapter(new HomeAdapter());
					mDisplay.setOnItemClickListener(new ZrcListView.OnItemClickListener() {

						@Override
						public void onItemClick(ZrcListView parent, View view, int position, long id) {
							String type = mKXApplication.mMyHomeResults.get(position).getType();
							if("photo".equals(type)){
								Intent intent = new Intent();
								intent.setClass(mContext,
										HomePhotoPictureDetailActivity.class);
								if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mMyHomeResults.get(position).getEmail())){
									intent.putExtra("uid", mKXApplication.mMyHomeResults.get(position).getUid());
								}
								intent.putExtra("avatar", mKXApplication.mMyHomeResults.get(position).getAvatar());
								intent.putExtra("name", mKXApplication.mMyHomeResults.get(position).getName());
								intent.putExtra("result", (Serializable)mKXApplication.mMyHomeResults.get(position));
								intent.putExtra("position", position);
								mContext.startActivity(intent);
							}else if("viewed".equals(type)){
								Intent intent = new Intent();
								intent.setClass(mContext,
										HomeDiaryDetailActivity.class);
								if(!StorageUtil.getString(mContext, "username").equals(mKXApplication.mMyHomeResults.get(position).getEmail())){
									intent.putExtra("uid", String.valueOf(mKXApplication.mMyHomeResults.get(position).getUid()));
								}
								intent.putExtra("name", mKXApplication.mMyHomeResults.get(position).getName());
								intent.putExtra("result", (Serializable)mKXApplication.mMyHomeResults.get(position));
								intent.putExtra("position", position);
								mContext.startActivity(intent);
							}
						}
					});
				}
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
								VoiceActivity.class));
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
		
		/*mPullRefresh.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 获取首页数据
						getNewHome();
						homeAdapter.notifyDataSetChanged();
						mPullRefresh.onHeaderRefreshComplete("更新于:"+new Date().toLocaleString());
					}
				}, 1000);
			}
		});
		
		mPullRefresh.setOnFooterRefreshListener(new OnFooterRefreshListener() {
			
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mPullRefresh.onFooterRefreshComplete();
					}
				}, 1000);
			}
		});*/
	}

	private void init() {
		//定义refreshview
//		mPullRefresh.setFooterRefreshEnable(true);
//		mPullRefresh.setHeaderRefreshEnable(true);
		// 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
//        float density = mContext.getResources().getDisplayMetrics().density;
//        mDisplay.setFirstTopOffset((int) (density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        header = new SimpleHeader(mContext);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        mDisplay.setHeadable(header);

        // 设置加载更多的样式（可选）
        footer = new SimpleFooter(mContext);
        footer.setCircleColor(0xff33bbee);
        mDisplay.setFootable(footer);

        // 设置列表项出现动画（可选）
        mDisplay.setItemAnimForTopIn(R.anim.topitem_in);
        mDisplay.setItemAnimForBottomIn(R.anim.bottomitem_in);

        // 下拉刷新事件回调（可选）
        mDisplay.setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        mDisplay.setOnLoadMoreStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
		
		mHandler = new Handler();
		// 获取首页数据
		getHome();
		// 添加适配器
		homeAdapter = new HomeAdapter();
		chatHistoryAdapter = new ChatHistoryAdapter();
		mDisplay.setAdapter(homeAdapter);
		mDisplay.setDivider(null);
		if(getPageCount() > 1){
			mDisplay.startLoadMore(); // 开启LoadingMore功能
		}
		mDisplay.refresh(); // 主动下拉刷新
	}
	
	public void showUnreadMsg(){
		if(isRead){
			mKXApplication.mMyChatHistory = mKXApplication.mSaveConversationDao.queryAllConversation();
			mDisplay.setAdapter(new ChatHistoryAdapter());
			isRead = false;
		}
		if(!StringUtil.isNull(StorageUtil.getString(mContext, "hasUnreadMsg"))){
			mMsgNumber.setVisibility(View.VISIBLE);
		}else{
			mMsgNumber.setVisibility(View.GONE);
		}
	}
	
	private void refresh(){
		mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewHome();
                homeAdapter.notifyDataSetChanged();
                mDisplay.setRefreshSuccess("加载成功"); // 通知加载成功
            }
        }, 2 * 1000);
    }

    private void loadMore(){
    	mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                if(page <= getPageCount()){
                	homeAdapter.notifyDataSetChanged();
                    mDisplay.setLoadMoreSuccess();
                    if(page == getPageCount()){
                    	mDisplay.stopLoadMore();
                    }
                }else{
                	mDisplay.setLoadMoreSuccess();
                	mDisplay.stopLoadMore();
                }
            }

        }, 2 * 1000);
    }
    
    private int getPageCount() {
		if(mKXApplication.mMyHomeResults.size() % pageCount == 0){
			return mKXApplication.mMyHomeResults.size() / pageCount;
		}else{
			return mKXApplication.mMyHomeResults.size() / pageCount + 1;
		}
	}
	
	public void fresh(){
		// 获取我的好友
		getNewHome();
		homeAdapter.notifyDataSetChanged();
	}

	/**
	 * 获取首页数据
	 */
	@SuppressWarnings("unchecked")
	private void getHome() {
		if(!CommonUtils.isNetWorkConnected(mContext)){
			mKXApplication.mMyHomeResults =  mKXApplication.mSaveHomeResultDao.queryAllHomeResult();
		}else{
			mKXApplication.mMyHomeResults =  mKXApplication.mSaveHomeResultDao.queryAllHomeResult();
			if(mKXApplication.mMyHomeResults.isEmpty()){
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("username", StorageUtil.getString(mContext, "username"));
				params.put("password", Encrypter.md5(StorageUtil.getString(mContext, "password")));
				HttpSyncClient.getInstance().post(mContext, Constants.getUrl() + "/MessagesServlet", params, new HttpCallBack() {

					@Override
					public void onStartRequest() {
						dialog = new ProgressDialog(mContext);
						dialog.setMessage(mContext.getString(R.string.listdialogmessage));
						if(!dialog.isShowing())
							dialog.show();
					}

					@Override
					public void onSuccess(Object paramObject) {
						dismiss();
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(paramObject.toString());
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
								
								mKXApplication.mMyHomeResults.add(result);
							}
							homeAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Exception paramException) {
						dismiss();
					}
				}, String.class);
			}else{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("username", StorageUtil.getString(mContext, "username"));
				params.put("password", Encrypter.md5(StorageUtil.getString(mContext, "password")));
				params.put("time", mKXApplication.mMyHomeResults.get(0).getTime());
				HttpSyncClient.getInstance().post(mContext, Constants.getUrl() + "/UpdateMessagesServlet", params, new HttpCallBack() {

					@Override
					public void onStartRequest() {
						dialog = new ProgressDialog(mContext);
						dialog.setMessage(mContext.getString(R.string.listdialogmessage));
						if(!dialog.isShowing())
							dialog.show();
					}

					@Override
					public void onSuccess(Object paramObject) {
						dismiss();
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(paramObject.toString());
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
								mKXApplication.mMyHomeResults.set(i, result);
							}
							homeAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Exception paramException) {
						dismiss();
					}
				}, String.class);
			}
			
		}
		
	}
	
	private void dismiss() {
		if(dialog.isShowing())
			dialog.dismiss();

	}
	
	/**
	 * 获取最新首页数据
	 */
	@SuppressWarnings("unchecked")
	private void getNewHome() {
		if(!mKXApplication.mMyHomeResults.isEmpty()){
			String json = CallService.getNewMessages(mKXApplication.mMyHomeResults.get(0).getTime());
			try {
				JSONObject jsonObject = new JSONObject(json);
				JSONArray array = jsonObject.getJSONArray("data");
				HomeResult result = null;
				if(array.length() != 0){
					for (int i = 0; i < array.length(); i++) {
						result = new HomeResult();
						result.setUid(array.getJSONObject(i).getInt("uid"));
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
						mKXApplication.mSaveHomeResultDao.saveHomeResult(result);
						mKXApplication.mMyHomeResults.add(0, result);
					}
				}else{
					MessageUtil.showMsg(mContext, "没有更新的内容！");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化菜单
	 */
	private void initPopupWindow() {
		popupAdapter = new PopupWindowAdapter();
		mPopDisplay.setAdapter(popupAdapter);
		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(mPopView, mTopLayout.getWidth(),
					LayoutParams.WRAP_CONTENT, true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		}
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			mPopupWindow.showAsDropDown(mTopLayout, 0, -10);
		}
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

	private class PopupWindowAdapter extends BaseAdapter {

		public int getCount() {
			return mPopupWindowItems.length;
		}

		public Object getItem(int position) {
			return mPopupWindowItems[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.home_popupwindow_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.home_popupwindow_item_name);
				holder.unread_msg_number = (TextView) convertView
						.findViewById(R.id.unread_msg_number);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mPopupWindowItems[position]);
			if(position == 1){
				if(!StringUtil.isNull(StorageUtil.getString(mContext, "hasUnreadMsg"))){
					holder.unread_msg_number.setVisibility(View.VISIBLE);
				}else{
					holder.unread_msg_number.setVisibility(View.GONE);
				}
			}else{
				holder.unread_msg_number.setVisibility(View.GONE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView unread_msg_number;
		}
	}

	private class HomeAdapter extends BaseAdapter {

		public int getCount() {
//			return mKXApplication.mMyHomeResults.size();
			if(page * pageCount < mKXApplication.mMyHomeResults.size()){
				return page * pageCount;
			}else{
				return mKXApplication.mMyHomeResults.size();
			}
			
		}

		public Object getItem(int position) {
			return mKXApplication.mMyHomeResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.home_item, null);
				holder = new ViewHolder();
				holder.viewed = (View) convertView
						.findViewById(R.id.home_item_viewed);

				holder.viewed_avatar = (ImageView) holder.viewed
						.findViewById(R.id.home_viewed_item_avatar);
				holder.viewed_name = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_name);
				holder.viewed_time = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_time);
				holder.viewed_title = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_title);
				holder.viewed_all = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_all);
				holder.photo = (View) convertView
						.findViewById(R.id.home_item_photo);
				holder.photo_avatar = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_avatar);
				holder.photo_name = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_name);
				holder.photo_time = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_time);
				holder.photo_title = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_titie);
				holder.photo_photo1 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo1);
				holder.photo_photo2 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo2);
				holder.photo_photo3 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo3);
				holder.photo_photo4 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo4);
				holder.photo_photo5 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo5);
				holder.photo_photo6 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo6);
				holder.photo_photo7 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo7);
				holder.photo_photo8 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo8);
				holder.photo_photo9 = (ImageView) holder.photo
						.findViewById(R.id.home_photo_item_photo9);
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
						.findViewById(R.id.home_photo_item_from);
				holder.photo_comment_count = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_comment_count);
				holder.photo_like_count = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_like_count);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final HomeResult result = mKXApplication.mMyHomeResults.get(position);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					mKXApplication.mSaveHomeResultDao.saveHomeResult(result);
				}
			}).start();
			if ("viewed".equals(result.getType())) {
				holder.viewed.setVisibility(View.VISIBLE);
				holder.photo.setVisibility(View.GONE);
//				holder.viewed_avatar.setImageBitmap(mKXApplication
//						.getAvatar(result.getAvatar()));
				ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.viewed_avatar, ImageUtil.getOption());
				
				holder.viewed_name.setText(result.getName());
				holder.viewed_time.setText(result.getTime());
				holder.viewed_title.setText(result.getTitle());
				holder.viewed_all.setText("查看" + result.getName() + "的回复");
				holder.viewed_avatar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(mContext, FriendInfoActivity.class);
						intent.putExtra("uid", String.valueOf(result.getUid()));
						intent.putExtra("name", result.getName());
						intent.putExtra("avatar", result.getAvatar());
						mContext.startActivity(intent);
					}
				});
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
				holder.photo_avatar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(mContext, FriendInfoActivity.class);
						if(!StorageUtil.getString(mContext, "username").equals(result.getEmail())){
							intent.putExtra("uid", result.getUid());
						}
						intent.putExtra("email", result.getEmail());
						intent.putExtra("name", result.getName());
						intent.putExtra("avatar", result.getAvatar());
						mContext.startActivity(intent);
					}
				});
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
				
//				holder.photo_photo1.setImageBitmap(mKXApplication.getHome(result
//						.getPhoto()));
				holder.photo_from.setText(result.getFrom());
				holder.photo_comment_count.setText(result.getComment_count()
						+ "");
				holder.photo_like_count.setText(result.getLike_count() + "");
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
		}
	}
	
	private class ChatHistoryAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mMyChatHistory.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMyChatHistory.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.row_chat_history, null);
				holder = new ViewHolder();
				holder.list_item_layout = (RelativeLayout) convertView
						.findViewById(R.id.list_item_layout);
				holder.avatar_container = (RelativeLayout) convertView
						.findViewById(R.id.avatar_container);
				holder.name = (TextView) convertView
						.findViewById(R.id.name);
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.avatar);
				holder.time = (TextView) convertView
						.findViewById(R.id.time);
				holder.msg_state = (ImageView) convertView
						.findViewById(R.id.msg_state);
				holder.message = (TextView) convertView
						.findViewById(R.id.message);
				holder.unread_msg_number = (TextView) convertView
						.findViewById(R.id.unread_msg_number);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			if(position%2==0)
			{
				holder.list_item_layout.setBackgroundResource(R.drawable.mm_listitem);
			}else{
				holder.list_item_layout.setBackgroundResource(R.drawable.mm_listitem_grey);
			}
			
			ConversationResult result = mKXApplication.mMyChatHistory.get(position);
			ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.avatar, ImageUtil.getOption());
			EMConversation conversation = EMChatManager.getInstance().getConversation(result.getEmail());
			if (conversation.getUnreadMsgCount() > 0) {
				// 显示与此用户的消息未读数
				holder.unread_msg_number.setText(String.valueOf(conversation.getUnreadMsgCount()));
				holder.unread_msg_number.setVisibility(View.VISIBLE);
			} else {
				holder.unread_msg_number.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(result.getName());
			holder.time.setText(result.getTime());
			if (conversation.getLastMessage().getType() == EMMessage.Type.TXT) {
				TextMessageBody txtBody = (TextMessageBody) conversation.getLastMessage().getBody();
				holder.message.setText(txtBody.getMessage().toString());
			}
			if (conversation.getLastMessage().getType() == EMMessage.Type.IMAGE) {
				holder.message.setText("图片");
			}
			if (conversation.getLastMessage().getType() == EMMessage.Type.LOCATION) {
				holder.message.setText("位置信息");
			}
			if (conversation.getLastMessage().getType() == EMMessage.Type.VOICE) {
				holder.message.setText("语音");
			}
			
			return convertView;
		}

		class ViewHolder {
			RelativeLayout list_item_layout;
			RelativeLayout avatar_container;
			TextView name;
			ImageView avatar;
			TextView time;
			ImageView msg_state;
			TextView message;
			TextView unread_msg_number;
		}
	}

	/**
	 * 照片对话框
	 */
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

	public View getView() {
		return mHome;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
