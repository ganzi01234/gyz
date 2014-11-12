package com.kaixin.android.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.activity.BaiduMapActivity.MyGeneralListener;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.LocationResult;
import com.kaixin.android.result.MessageResult;
import com.kaixin.android.result.PublicPageResult;
import com.kaixin.android.result.VoiceResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.view.MyDateTimePickerDialog;
import com.kaixin.android.view.MyDateTimePickerDialog.OnDateTimeSetListener;

/**
 * 菜单好友类
 * 
 * @author gyz
 * 
 */
public class SlaveInfoActivity extends KXActivity{
	private Context mContext;
	
	private Button mBack;
	private ListView mDisplay;
	private Button mVoice;
	private Button mMessage;
	private Button mLocation;
	private TextUtil mTextUtil;
	private VoiceAdapter mVoiceAdapter;
	private MessageAdapter mMessageAdapter;

	// 奴隶的录音数据
	/*private List<VoiceResult> mSlaveVoiceResults = new ArrayList<VoiceResult>();
	// 奴隶的短信数据
	private List<MessageResult> mSlaveMessageResults = new ArrayList<MessageResult>();
	// 奴隶的定位数据
	private List<PublicPageResult> mMyPublicPageResults = new ArrayList<PublicPageResult>();*/

	// 页签
	private int mChoosePostition = 0;

	private String mUid;

	private String mEmail;
	private AlertDialog alertDialog;

	private MapView mMapDisplay;
	private TextView mChooseDate;
	MyItemizedOverlay mAddrOverlay = null;
	private int mCurrentPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		if (mBMapManager == null) {
			initEngineManager(this.getApplicationContext());
		}
		setContentView(R.layout.slaveinfo_activity);
		findViewById();
		setListener();
		init();
	}


	private void findViewById() {
		mBack = (Button) findViewById(R.id.slaveinfo_back);
		mVoice = (Button) findViewById(R.id.voice_page);
		mMessage = (Button) findViewById(R.id.message_page);
		mLocation = (Button) findViewById(R.id.location_page);
		mDisplay = (ListView) findViewById(R.id.slaveinfo_display);
		mMapDisplay = (MapView) findViewById(R.id.bmapView);
		mChooseDate = (TextView) findViewById(R.id.choose_date);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mVoice.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的不是录音内容则显示录音内容数据
				if (mChoosePostition != 0) {
					mChoosePostition = 0;
					mDisplay.setVisibility(View.VISIBLE);
					mMapDisplay.setVisibility(View.GONE);
					mChooseDate.setVisibility(View.GONE);
					mVoice.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mMessage.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mLocation.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mVoiceAdapter = new VoiceAdapter();
					mDisplay.setAdapter(mVoiceAdapter);
				}
			}
		});
		mMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的是好友内容则显示公共主页内容数据
				if (mChoosePostition != 1) {
					mChoosePostition = 1;
					mDisplay.setVisibility(View.VISIBLE);
					mMapDisplay.setVisibility(View.GONE);
					mChooseDate.setVisibility(View.GONE);
					mVoice.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mMessage.setBackgroundResource(R.drawable.bottomtabbutton_red);
					mLocation.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mMessageAdapter = new MessageAdapter();
					mDisplay.setAdapter(mMessageAdapter);
				}
			}
		});
		mLocation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的是好友内容则显示公共主页内容数据
				if (mChoosePostition != 2) {
					mChoosePostition = 2;
					mDisplay.setVisibility(View.GONE);
					mMapDisplay.setVisibility(View.VISIBLE);
					mChooseDate.setVisibility(View.VISIBLE);
					mVoice.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mMessage.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mLocation.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					if(!mKXApplication.mSlaveLocationResults.isEmpty()){
						for(int i=0; i < mKXApplication.mSlaveLocationResults.size(); i++){
							LocationResult location = mKXApplication.mSlaveLocationResults.get(i);
							showMap(i, Double.parseDouble(location.getLatitude()),
									Double.parseDouble(location.getLongitude()),
									location.getLocation());
						}
					}else{
						MessageUtil.showMsg(mContext, "未定位到用户位置！");
					}
					
				}
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(mChoosePostition == 0){
					String json = CallService.authVoice(mKXApplication.mSlaveVoiceResults.get(position).getVid());
					JSONObject object;
					try {
						object = new JSONObject(json);
						boolean success = object.getBoolean("success");
						if(!success){
							MessageUtil.showImgMsg(mContext, object.getString("data"));
							return;
						}else{
							MessageUtil.showImgMsg(mContext, object.getString("data"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent = new Intent();
					intent.setClass(mContext, PlayVoiceActivity.class);
					intent.putExtra("url", mKXApplication.mSlaveVoiceResults.get(position).getUrl());
//					CallService.deleteVoice(mKXApplication.mSlaveVoiceResults.get(position).getVid());
					mKXApplication.mSlaveVoiceResults.remove(position);
					mVoiceAdapter.notifyDataSetChanged();
					mContext.startActivity(intent);
				}else{
					String json = CallService.authMessage(mKXApplication.mSlaveMessageResults.get(position).getMid());
					JSONObject object;
					try {
						object = new JSONObject(json);
						boolean success = object.getBoolean("success");
						if(!success){
							MessageUtil.showImgMsg(mContext, object.getString("data"));
							return;
						}else{
							MessageUtil.showImgMsg(mContext, object.getString("data"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mCurrentPosition = position;
					alertDialog = new AlertDialog.Builder(mContext)
						.setMessage(mKXApplication.mSlaveMessageResults.get(position).getContent())
						.show();
					mKXApplication.mSlaveMessageResults.remove(position);
					mMessageAdapter.notifyDataSetChanged();
					delayCloseController = new DelayCloseController();  // 新建一个任务      
					delayCloseController.setCloseFlags(CLOSE_ALERTDIALOG);            //设置信息标志位   
					delayCloseController.timer.schedule(delayCloseController, 5000);   //启动定时器 
				}
			}
		});
		mChooseDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
				if(!StringUtil.isNull(mChooseDate.getText().toString())){
					DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = fmt.parse(mChooseDate.getText().toString());
						mycalendar.setTime(date);//为Calendar对象设置时间为生日时间
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					Date mydate = new Date(); // 获取当前日期Date对象
					mycalendar.setTime(mydate);//为Calendar对象设置时间为当前日期
				}
				
				new MyDateTimePickerDialog(SlaveInfoActivity.this, mycalendar, new OnDateTimeSetListener() {

					@Override
					public void onDateTimeSet(int year, int monthOfYear,
							int dayOfMonth) {
						if(monthOfYear < 10){
							mChooseDate.setText(year + "-0" + monthOfYear + "-"
									+ dayOfMonth);
						}else{
							mChooseDate.setText(year + "-" + monthOfYear + "-"
									+ dayOfMonth);
						}
						
						mMapDisplay.getOverlays().clear();
						mMapDisplay.refresh();
						getLocation(mChooseDate.getText().toString());
						if(!mKXApplication.mSlaveLocationResults.isEmpty()){
							for(int i=0; i < mKXApplication.mSlaveLocationResults.size(); i++){
								LocationResult location = mKXApplication.mSlaveLocationResults.get(i);
								showMap(i, Double.parseDouble(location.getLatitude()),
										Double.parseDouble(location.getLongitude()),
										location.getLocation());
							}
						}else{
							MessageUtil.showMsg(mContext, "未定位到用户位置！");
						}
					}

				}).show();
			}
		});
	}
	
	private final int CLOSE_ALERTDIALOG = 0;  //定义关闭对话框的动作信号标志
	private final int CLOSE_SAMPLE_VIEW = 1;  //定义关闭SampleView的动作信号标志
    private DelayCloseController delayCloseController = null;
	private class DelayCloseController extends TimerTask {
		private Timer timer = new Timer();
                private int actionFlags = 0;//标志位参数
                public void setCloseFlags(int flag)
 		{
 			actionFlags = flag;
 		}
		@Override
		public void run() {
			Message messageFinish = new Message();
			messageFinish.what = actionFlags ;
			mainHandler.sendMessage(messageFinish);
		}
	}
	
	private Handler mainHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CLOSE_SAMPLE_VIEW:
				if(alertDialog != null && alertDialog.isShowing())
				{
					alertDialog.dismiss();
				}
				break;
			case CLOSE_ALERTDIALOG:
				if(alertDialog != null && alertDialog.isShowing())
				{
					alertDialog.dismiss();  //关闭对话框
					if (delayCloseController.timer != null){
						delayCloseController.timer.cancel();
				    }
					
				}
				break;
			default:
				break;
			}
		}
    };

	private MapController mMapController;
	private BMapManager mBMapManager;
    

	private void init() {
		initMap();
		mEmail = getIntent().getStringExtra("email");
		// 实例化文本工具
		mTextUtil = new TextUtil(mKXApplication);
		// 获取我的好友
		getMySlaveInfo();
		// 添加适配器
		mVoiceAdapter = new VoiceAdapter();
		mDisplay.setAdapter(mVoiceAdapter);
	}
	
	private void initMap() {
		mMapDisplay.getController().setZoom(17);
		mMapDisplay.getController().enableClick(true);
		mMapDisplay.setBuiltInZoomControls(true);
		mMapController = mMapDisplay.getController();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date mydate = new Date(); // 获取当前日期Date对象
		mChooseDate.setText(sdf.format(mydate));
	}
	
	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(Constants.BAIDU_MAP_KEY, new MyGeneralListener())) {
			Toast.makeText(this.getApplicationContext(), "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}
	
	class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(SlaveInfoActivity.this, "您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(SlaveInfoActivity.this, "输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Log.e("map", "permissio denied. check your app key");
			}
		}
	}
	
	private void showMap(int i, double latitude, double longtitude, String address) {
		GeoPoint point1 = new GeoPoint((int) (latitude * 1e6), (int) (longtitude * 1e6));
		point1 = CoordinateConvert.fromGcjToBaidu(point1);
		mMapController.setCenter(point1);
		Drawable marker = chooseOverlay(i);
//		Drawable marker = this.getResources().getDrawable(R.drawable.icon_marka);
		// 为maker定义位置和边界
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
		mAddrOverlay = new MyItemizedOverlay(marker, mMapDisplay);
		GeoPoint point = new GeoPoint((int) (latitude * 1e6), (int) (longtitude * 1e6));
		point = CoordinateConvert.fromGcjToBaidu(point);
		OverlayItem addrItem = new OverlayItem(point, "", address);
		mAddrOverlay.addItem(addrItem);
		mMapDisplay.getOverlays().add(mAddrOverlay);
		mMapDisplay.refresh();
	}


	private Drawable chooseOverlay(int i) {
		Drawable marker = null;
		switch(i){
			case 1:
				marker = this.getResources().getDrawable(R.drawable.icon_marka);
				break;
			case 2:
				marker = this.getResources().getDrawable(R.drawable.icon_markb);
				break;
			case 3:
				marker = this.getResources().getDrawable(R.drawable.icon_markc);
				break;
			case 4:
				marker = this.getResources().getDrawable(R.drawable.icon_markd);
				break;
			case 5:
				marker = this.getResources().getDrawable(R.drawable.icon_marke);
				break;
			case 6:
				marker = this.getResources().getDrawable(R.drawable.icon_markf);
				break;
			case 7:
				marker = this.getResources().getDrawable(R.drawable.icon_markg);
				break;
			case 8:
				marker = this.getResources().getDrawable(R.drawable.icon_markh);
				break;
			case 9:
				marker = this.getResources().getDrawable(R.drawable.icon_marki);
				break;
			case 10:
				marker = this.getResources().getDrawable(R.drawable.icon_markj);
				break;
			default:
				marker = this.getResources().getDrawable(R.drawable.icon_markj);
				break;
		}
		return marker;
	}
	
	/*
	 * 要处理overlay点击事件时需要继承ItemizedOverlay
	 * 不处理点击事件时可直接生成ItemizedOverlay.
	 */
	class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	    //用MapView构造ItemizedOverlay
	    public MyItemizedOverlay(Drawable mark,MapView mapView){
	        super(mark,mapView);
	    }
	    protected boolean onTap(int index) {
	        //在此处理item点击事件
//	        System.out.println("item onTap: "+index);
	    	Toast.makeText(SlaveInfoActivity.this, "地址："+mKXApplication.mSlaveLocationResults.get(index).getLocation()
	    			+"\n时间：" + mKXApplication.mSlaveLocationResults.get(index).getTime(), Toast.LENGTH_SHORT).show();
	        return true;
	    }
	    public boolean onTap(GeoPoint pt, MapView mapView){
	    	//在此处理MapView的点击事件，当返回 true时
	    	super.onTap(pt,mapView);
	    	return false;
	    }
	}        
		 
	/**
	 * 获取我的好友
	 */
	private void getMySlaveInfo() {
		
		if (mKXApplication.mSlaveVoiceResults.isEmpty()) {
			String json = CallService.getMySalveVoice(mEmail);
			try {
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray("data");
				VoiceResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new VoiceResult();
					result.setVid(array.getJSONObject(i).getString("vid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setUrl(array.getJSONObject(i).getString("url"));
					result.setTime(array.getJSONObject(i).getString("time"));
					result.setInfo(array.getJSONObject(i).getString("info"));
					mKXApplication.mSlaveVoiceResults.add(result);
				}
			}catch (Exception e) {

			}
		}
		
		if (mKXApplication.mSlaveMessageResults.isEmpty()) {
			String json = CallService.getMySalveMessage(mEmail);
			try {
				JSONObject object = new JSONObject(json);
				JSONArray array = object.getJSONArray("data");
				MessageResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new MessageResult();
					result.setMid(array.getJSONObject(i).getString("vid"));
					result.setTitle(array.getJSONObject(i).getString("title"));
					result.setContent(array.getJSONObject(i).getString("content"));
					result.setTime(array.getJSONObject(i).getString("time"));
					result.setInfo(array.getJSONObject(i).getString("info"));
					mKXApplication.mSlaveMessageResults.add(result);
				}
			}catch (Exception e) {

			}
		}
		Date mydate = new Date(); // 获取当前日期Date对象
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		getLocation(fmt.format(mydate));
	}


	private void getLocation(String date) {
		String json = CallService.getMySalveLocation(mEmail, date);
		try {
			JSONObject object = new JSONObject(json);
			JSONArray array = object.getJSONArray("data");
			LocationResult result = null;
			for (int i = 0; i < array.length(); i++) {
				result = new LocationResult();
				result.setTime(array.getJSONObject(i).getString("time"));
				result.setLocation(array.getJSONObject(i).getString("location"));
				result.setLatitude(array.getJSONObject(i).getString("latitude"));
				result.setLongitude(array.getJSONObject(i).getString("longitude"));
				mKXApplication.mSlaveLocationResults.add(result);
			}
		}catch (Exception e) {

		}
	}

	private class VoiceAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mSlaveVoiceResults.size();
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
						R.layout.slaveinfo_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.slaveinfo_item_name);
				holder.info = (TextView) convertView
						.findViewById(R.id.slaveinfo_item_info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			VoiceResult result = mKXApplication.mSlaveVoiceResults.get(position);
			holder.name.setText(result.getTime());
			holder.info.setText(result.getInfo());
			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView time;
			TextView info;
		}

	}
	
	private class MessageAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mSlaveMessageResults.size();
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
						R.layout.slaveinfo_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.slaveinfo_item_name);
				holder.info = (TextView) convertView
						.findViewById(R.id.slaveinfo_item_info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			MessageResult result = mKXApplication.mSlaveMessageResults.get(position);
			holder.name.setText("点开查看");
			holder.info.setText(result.getInfo());
			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView time;
			TextView info;
		}

	}

}
