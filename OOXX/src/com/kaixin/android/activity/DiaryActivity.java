package com.kaixin.android.activity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.Diary;
import com.kaixin.android.result.DiaryResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.TextUtil;

/**
 * 资料日记类
 * 
 * @author gyz
 * 
 */
public class DiaryActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private Button mRefresh;
	private Button mWriteMessage;
	private ListView mDisplay;
	private TextView mNoDisplay;

	private DiaryAdapter mAdapter;

	
	private String mUid;// 当前日记所属的用户ID
	private String mName;// 当前日记所属的用户姓名

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary_activity);
		findViewById();
		setListener();
		try {
			init();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.diary_back);
		mTitle = (TextView) findViewById(R.id.diary_title);
		mRefresh = (Button) findViewById(R.id.diary_refresh);
		mWriteMessage = (Button) findViewById(R.id.diary_write_message);
		mDisplay = (ListView) findViewById(R.id.diary_display);
		mNoDisplay = (TextView) findViewById(R.id.diary_nodisplay);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
			}
		});
		mWriteMessage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到写日记界面
				startActivityForResult(new Intent(DiaryActivity.this,
						WriteDiaryActivity.class),
						ActivityForResultUtil.REQUESTCODE_WRITEDIARY);
			}
		});
	}

	private void init() throws JSONException {
		// 获取当前日记所属用户的ID和姓名
		mUid = getIntent().getStringExtra("uid");
		mName = getIntent().getStringExtra("name");
		// ID如果为空则代表为当前用户,否则为其他用户
		if (mUid == null) {
			mBack.setText("我的首页");
			mTitle.setText("我的日记");
			// 当前用户时可显示进行写日记
			mWriteMessage.setVisibility(View.VISIBLE);
			// 获取日记数据
			getDiary();
			// 初始化适配器
			mAdapter = new DiaryAdapter(mKXApplication.mMyDiaryResults);
			// 添加适配器
			mDisplay.setAdapter(mAdapter);
		} else {
			mBack.setText(mName);
			mTitle.setText(mName + "的日记");
			// 非当前用户隐藏写日记
			mWriteMessage.setVisibility(View.GONE);
			// 获取日记数据
			getDiary();
			// 初始化适配器
			mAdapter = new DiaryAdapter(
					mKXApplication.mFriendDiaryResults.get(mUid));
			// 添加适配器
			mDisplay.setAdapter(mAdapter);
		}
	}
	
	/**
	 * 获取用户的状态
	 */
	private List<Diary> getDiaries(String uid) {
		String json;
		List<Diary> results = null;
		try {
			json = CallService.getDiaries(uid);
			JSONObject object = new JSONObject(json);
			JSONArray array = object.getJSONArray(
					"data");
			Diary result = null;
			results = new ArrayList<Diary>();
			for (int i = 0; i < array.length(); i++) {
				result = new Diary();
				result.setId(array.getJSONObject(i).getInt("id"));
				result.setTitle(array.getJSONObject(i).getString("title"));
				result.setUsername(array.getJSONObject(i).getString("username"));
				result.setFilename(array.getJSONObject(i).getString("filename"));
				result.setTime(array.getJSONObject(i).getString("time"));
				result.setContent(array.getJSONObject(i).getString("content"));
				result.setComment_count(array.getJSONObject(i).getInt("comment_count"));
				result.setLike_count(array.getJSONObject(i).getInt("like_count"));
				results.add(result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			mDisplay.setVisibility(View.GONE);
			mNoDisplay.setVisibility(View.VISIBLE);
		}
		return results;
	}

	/**
	 * 根据用户的ID获取用户的日记数据
	 */
	private void getDiary() throws JSONException {
		if (mUid == null) {
			if (mKXApplication.mMyDiaryResults.isEmpty()) {
				mKXApplication.mMyDiaryResults = getDiaries("");
			}
		} else {
			if (!mKXApplication.mFriendDiaryResults.containsKey(mUid)) {
				mKXApplication.mFriendDiaryResults.put(mUid, getDiaries(mUid));
			}
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//判断是否写日记时已经发布日记,如果发布则更新界面
		if (resultCode == RESULT_OK) {
			mAdapter.notifyDataSetChanged();
		}
	}

	private class DiaryAdapter extends BaseAdapter {
		private List<Diary> mResults;

		public DiaryAdapter(List<Diary> results) {
			if (results == null) {
				mResults = new ArrayList<Diary>();
			} else {
				mResults = results;
			}
		}

		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(DiaryActivity.this).inflate(
						R.layout.diary_activity_item, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView
						.findViewById(R.id.diary_item_title);
				holder.content = (TextView) convertView
						.findViewById(R.id.diary_item_content);
				holder.time = (TextView) convertView
						.findViewById(R.id.diary_item_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Diary result = mResults.get(position);
			holder.title.setText(result.getTitle());
			holder.content.setText(new TextUtil(mKXApplication).replace(result
					.getContent()));
			holder.time.setText(result.getTime());
			convertView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					//跳转到日记详情界面,并传递用户的ID、姓名和日记的具体内容
					Intent intent = new Intent();
					intent.setClass(DiaryActivity.this,
							DiaryDetailActivity.class);
					intent.putExtra("uid", mUid);
					intent.putExtra("name", mName);
					intent.putExtra("result", (Serializable)mResults.get(position));
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView content;
			TextView time;
		}

	}
}
