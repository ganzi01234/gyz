package com.kaixin.android.location;

import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.kaixin.android.KXApplication;

public class BMapApiApp extends KXApplication {

	private static BMapApiApp mDemoApp;

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��
	public static BMapApiApp getInstance() {
		if (null == mDemoApp) {
			mDemoApp = new BMapApiApp();
		}
		return mDemoApp;

	}

	// �ٶ�MapAPI�Ĺ�����
	public BMapManager mBMapMan = null;

	// ��ȨKey
	// TODO: ���������Key,
	// �����ַ��http://dev.baidu.com/wiki/static/imap/key/
	// String mStrKey = "���ڴ����������ȨKey";
	public String mStrKey = "7F321729E05F4C62E9A04D985869076E53087406";
	boolean m_bKeyRight = true; // ��ȨKey��ȷ����֤ͨ��

	// �����¼�������������ͨ�������������Ȩ��֤�����
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is " + iError);
/*			Toast.makeText(BMapApiApp.mDemoApp.getApplicationContext(), "��������������", Toast.LENGTH_LONG).show();
			
			return;*/
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is " + iError);
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// ��ȨKey����
				Toast.makeText(BMapApiApp.mDemoApp.getApplicationContext(), "baidu key error", Toast.LENGTH_LONG).show();
				BMapApiApp.mDemoApp.m_bKeyRight = false;
			}
		}

	}

	@Override
	public void onCreate() {
		mDemoApp = BMapApiApp.getInstance();
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		super.onCreate();
	}

	@Override
	// ��������app���˳�֮ǰ����mapadpi��destroy()��������ظ���ʼ��������ʱ�����
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}

}
