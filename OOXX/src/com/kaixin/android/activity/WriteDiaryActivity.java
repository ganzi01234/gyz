package com.kaixin.android.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.Diary;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.TextUtil;
import com.kaixin.android.utils.Utils;

/**
 * 写日记类
 * 
 * @author gyz
 * 
 */
public class WriteDiaryActivity extends KXActivity {
	private LinearLayout mParent;
	private Button mCancel;
	private Button mSubmit;
	private EditText mTitle;
	private EditText mContent;
	private Button mFace;
	private Button mPhoto;
	private Button mLocation;
	private TextView mCompetence;
	private String[] mCompetenceItems = new String[] { "任何人可见",
			"仅关注人可见", "仅自己可见" };// 权限名称
	private int mCompetencePosition = 1;// 当前选择的权限在列表中的位置

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writediary_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mParent = (LinearLayout) findViewById(R.id.writediary_parent);
		mCancel = (Button) findViewById(R.id.writediary_cannel);
		mSubmit = (Button) findViewById(R.id.writediary_submit);
		mTitle = (EditText) findViewById(R.id.writediary_title);
		mContent = (EditText) findViewById(R.id.writediary_content);
		mFace = (Button) findViewById(R.id.writediary_face);
		mPhoto = (Button) findViewById(R.id.writediary_photo);
		mLocation = (Button) findViewById(R.id.writediary_location);
		mCompetence = (TextView) findViewById(R.id.writerecord_competence);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示退出对话框
				showExitDialog();
			}
		});
		mSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获取当前标题和内容数据
				String title = mTitle.getText().toString().trim();
				String content = mContent.getText().toString().trim();
				// 标题和内容为空时提示消息,否则则关闭当前界面并返回数据
				if (TextUtils.isEmpty(title)) {
					showErrorDialog("日记标题不能为空");
				} else if (TextUtils.isEmpty(content)) {
					showErrorDialog("日记内容不能为空");
				} else {
					Diary result = new Diary();
					result.setTitle(title);
					result.setContent(content);
					CallService.writeDiary(title, content, mCompetencePosition);
					result.setTime(Utils.formatDate(WriteDiaryActivity.this,
							System.currentTimeMillis()));
					mKXApplication.mMyDiaryResults.add(0, result);
					mKXApplication.mDraft_DiaryTitle = null;
					mKXApplication.mDraft_DiaryContent = null;
					setResult(RESULT_OK);
					MessageUtil.showImgMsg(mKXApplication, "发布成功，+10");
					finish();
				}
			}
		});
		mTitle.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				// 当标题获取到焦点时,表情不可用
				if (hasFocus) {
					mFace.setBackgroundResource(R.drawable.write_function_face_disable);
					mFace.setEnabled(false);
				} else {
					mFace.setBackgroundResource(R.drawable.write_function_face_button);
					mFace.setEnabled(true);
				}
			}
		});
		mFace.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示表情对话框
				showFace(mParent);
			}
		});
		mPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});
		mLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});
		mFaceGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取当前光标所在位置
				int currentPosition = mContent.getSelectionStart();
				// 添加含有表情的文本
				mContent.setText(new TextUtil(mKXApplication).replace(mContent
						.getText().insert(currentPosition,
								mKXApplication.mFacesText.get(position))));
				// 关闭表情对话框
				dismissFace();
			}
		});
		mFaceClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭表情对话框
				dismissFace();
			}
		});
		mCompetence.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 权限对话框
				CompetenceDialog();
			}
		});
	}

	private void init() {
		// 查看当前草稿箱是否有数据,有的话则显示
		if (!(mKXApplication.mDraft_DiaryTitle == null)) {
			mTitle.setText(mKXApplication.mDraft_DiaryTitle);
		}
		if (!(mKXApplication.mDraft_DiaryContent == null)) {
			mContent.setText(mKXApplication.mDraft_DiaryContent);
		}
		mCompetence.setText(mCompetenceItems[mCompetencePosition]);
	}

	/**
	 * 提示对话框
	 * 
	 * @param message
	 */
	private void showErrorDialog(String message) {
		new AlertDialog.Builder(WriteDiaryActivity.this).setTitle("OOXX")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
	}

	/**
	 * 退出对话框
	 */
	private void showExitDialog() {
		final String title = mTitle.getText().toString().trim();
		final String content = mContent.getText().toString().trim();
		if ((!TextUtils.isEmpty(title)) || (!TextUtils.isEmpty(content))) {
			new AlertDialog.Builder(WriteDiaryActivity.this)
					.setTitle("退出正在编辑的日记")
					.setItems(new String[] { "保存为日记草稿", "不保存" },
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0:
										mKXApplication.mDraft_DiaryTitle = title;
										mKXApplication.mDraft_DiaryContent = content;
										break;

									case 1:
										mKXApplication.mDraft_DiaryTitle = null;
										mKXApplication.mDraft_DiaryContent = null;
										break;
									}
									setResult(RESULT_CANCELED);
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create().show();
		} else {
			mKXApplication.mDraft_DiaryTitle = null;
			mKXApplication.mDraft_DiaryContent = null;
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	/**
	 * 权限对话框
	 */
	private void CompetenceDialog() {
		AlertDialog.Builder builder = new Builder(WriteDiaryActivity.this);
		builder.setTitle("请选择记录权限");
		builder.setAdapter(new CompetenceAdapter(),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mCompetencePosition = which;
						mCompetence.setText(mCompetenceItems[which]);
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	private class CompetenceAdapter extends BaseAdapter {

		public int getCount() {
			return mCompetenceItems.length;
		}

		public Object getItem(int position) {
			return mCompetenceItems[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(WriteDiaryActivity.this)
						.inflate(R.layout.writerecord_activity_competence_item,
								null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.writerecord_activity_competence_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.writerecord_activity_competence_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (mCompetencePosition == position) {
				holder.icon.setVisibility(View.VISIBLE);
			} else {
				holder.icon.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(mCompetenceItems[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
		}
	}

	public void onBackPressed() {
		// 显示退出对话框
		showExitDialog();
	}
}
