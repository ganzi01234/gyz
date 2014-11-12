package com.kaixin.android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.StringUtil;

/**
 * 用户反馈类
 * 
 * @author gyz
 * 
 */
public class FeedBackActivity extends KXActivity {
	private Button mCancel;
	private Button mSubmit;
	private EditText mEdt;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_activity);
		findViewById();
		setListener();
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.feedback_cannel);
		mSubmit = (Button) findViewById(R.id.feedback_submit);
		mEdt = (EditText) findViewById(R.id.feedback_edt);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(StringUtil.isNull(mEdt.getText().toString())){
					Toast.makeText(FeedBackActivity.this, "请输入反馈内容！",
							Toast.LENGTH_SHORT).show();
				}else if(mEdt.getText().toString().length() > 250){
					Toast.makeText(FeedBackActivity.this, "亲,请输入250字以内反馈！",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(FeedBackActivity.this, "提交成功",
							Toast.LENGTH_SHORT).show();
					CallService.writeFeedback(mEdt.getText().toString());
					// 关闭当前界面
					finish();
				}
			}
		});
	}
}
