package com.kaixin.android.activity;

import java.io.Serializable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.TextUtil;

/**
 * 日记内容显示类
 * 
 * @author gyz
 * 
 */
public class HomeDiaryDetailActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private TextView mDiaryTitle;
	private TextView mDiaryTime;
	private TextView mDiaryContent;
	private Button mComment;
	private Button mRepost;

	private String mUid;// 日记所属用户的ID
	private String mName;// 日记所属用户的姓名
	private HomeResult mHomeDiaryResult; // 日记的具体内容
	private ProgressDialog mDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarydetail_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.diarydetail_back);
		mTitle = (TextView) findViewById(R.id.diarydetail_top_title);
		mDiaryTitle = (TextView) findViewById(R.id.diarydetail_title);
		mDiaryTime = (TextView) findViewById(R.id.diarydetail_time);
		mDiaryContent = (TextView) findViewById(R.id.diarydetail_content);
		mComment = (Button) findViewById(R.id.diarydetail_comment);
		mRepost = (Button) findViewById(R.id.diarydetail_repost);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mRepost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示发送转帖进度条,并在500ms后隐藏并显示提示信息
				String content = mHomeDiaryResult.getTitle();
				if(content.length() > 5){
					CallService.writeDiary(content.substring(0, 4), content, 1);
				}else{
					CallService.writeDiary(content, content, 1);
				}
				mDialog.show();
				handler.sendEmptyMessageDelayed(0, 500);
			}
		});
		
		mComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 跳转到评论内容显示界面,并传递照片数据
				Intent intent = new Intent();
				intent.setClass(HomeDiaryDetailActivity.this,
						HomeDiaryCommentDetailActivity.class);
				intent.putExtra("diaryId", mHomeDiaryResult.getMessageid());
				intent.putExtra("result", (Serializable)mHomeDiaryResult);
//				intent.putExtra("result", mMorePhotoResults.get(mCurrentPosition));
				startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_COMMENT_COUNT);
			}
		});
	}

	private void init() {
		// 初始化进度条
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("正在发送转帖请求...");
		// 获取当前日记所属用户的ID和姓名以及日记内容
		mUid = getIntent().getStringExtra("uid");
		mName = getIntent().getStringExtra("name");
		mHomeDiaryResult = (HomeResult)getIntent().getSerializableExtra("result");

		// 根据用户的ID显示不同的内容,如果是当前用户则不显示转帖
		if (mUid == null) {
			mTitle.setText("我的说说");
			mRepost.setVisibility(View.GONE);
		} else {
			mTitle.setText(mName + "的说说");
			mRepost.setVisibility(View.VISIBLE);
		}
		// 添加日记的具体内容
		mDiaryTitle.setText(mHomeDiaryResult.getTitle());
		mDiaryTime.setText(mHomeDiaryResult.getTime());
		mDiaryContent.setText(new TextUtil(mKXApplication).replace(mHomeDiaryResult
				.getTitle()));
		mComment.setText(mHomeDiaryResult.getComment_count() + "");
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 如果进度条存在则隐藏并显示提示信息
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
				Toast.makeText(HomeDiaryDetailActivity.this,
						"转帖成功!你的好友会通过好友状态看到此转帖", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
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
