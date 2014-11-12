package com.kaixin.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;

/**
 * 导入好友至手机通讯录类
 * 
 * @author gyz
 * 
 */
public class ExportActivity extends KXActivity {
	private Button mBack;
	private Button mExport;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_activity);
		findViewById();
		setListener();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.export_back);
		mExport = (Button) findViewById(R.id.btn_export);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mExport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExportActivity.this, ContactsActivity.class); 
				startActivity(intent);
			}
		});
	}
}
