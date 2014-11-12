package com.kaixin.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.CommentResult;
import com.kaixin.android.result.HomeResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.ImageUtil;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.TextUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 评论内容显示类
 * 
 * @author gyz
 * 
 */
public class HomeDiaryCommentDetailActivity extends KXActivity {
	private Button mBack;
	private Button mLike;
	private ListView mDisplay;
	private Button mComment;

	private View mHead;
	private View mFoot;
	private TextView mHeadContent;
	private View mHeadLine;

	private DiaryCommentDetailAdapter mAdapter;
	private List<CommentResult> mCommentResult = new ArrayList<CommentResult>();
	private HomeResult mHomeDiaryDetailResult;

	private boolean mIsLike;// 是否已经赞过
	private int mReplyPosition;// 回复的评论编号
	private int diaryId;
	private int position;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photocommentdetail_activity);
		mHead = LayoutInflater.from(this).inflate(
				R.layout.photocommentdetail_activity_head, null);
		mFoot = LayoutInflater.from(this).inflate(
				R.layout.photocommentdetail_activity_foot, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.photocommentdetail_back);
		mLike = (Button) findViewById(R.id.photocommentdetail_like);
		mDisplay = (ListView) findViewById(R.id.photocommentdetail_display);
		mComment = (Button) findViewById(R.id.photocommentdetail_comment);
		mHeadContent = (TextView) mHead
				.findViewById(R.id.photocommentdetail_head_content);
		mHeadLine = (View) mHead
				.findViewById(R.id.photocommentdetail_head_line);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putInt("count",
						mHomeDiaryDetailResult.getComment_count());
				Intent intent = new Intent();
				intent.putExtra("result", bundle);
				setResult(RESULT_OK, intent);
				// 关闭当前界面
				finish();
			}
		});
		mLike.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!mIsLike) {
					// 修改界面内容
					mIsLike = true;
					mLike.setTextColor(Color.parseColor("#999999"));
					CallService.setDiaryLike(mHomeDiaryDetailResult.getMessageid());
					if (mHomeDiaryDetailResult.getLike_count() == 0) {
						mHeadContent.setText("我觉得这个挺赞的");
					} else {
						mHeadContent.setText("我和"
								+ mHomeDiaryDetailResult.getLike_count() + "个人觉得这个挺赞的");
					}
					mHomeDiaryDetailResult.setLike_count(mHomeDiaryDetailResult.getLike_count() + 1);
					Constants.isAddFriend = true;
				}
			}
		});
		mComment.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到评论界面
				startActivityForResult(new Intent(
						HomeDiaryCommentDetailActivity.this,
						PhotoCommentActivity.class),
						ActivityForResultUtil.REQUESTCODE_PHOTOCOMMENT);
			}
		});
	}

	private void init() {
		diaryId = getIntent().getIntExtra("diaryId", -1);
		position = getIntent().getIntExtra("position", -1);
		mHomeDiaryDetailResult =  (HomeResult)getIntent().getSerializableExtra("result");
		// 获取照片内容数据
		
		String json = CallService.getComments(1, 0, diaryId);
		getComments(json);
		
		// 添加头布局
		mDisplay.addHeaderView(mHead);
		// 根据内容显示界面
		if (mHomeDiaryDetailResult.getLike_count() == 0) {
			mHeadContent.setText("还没有人赞过");
		} else {
			mHeadContent.setText(mHomeDiaryDetailResult.getLike_count() + "个人觉得这个挺赞的");
		}
		if (mHomeDiaryDetailResult.getComment_count() == 0) {
			mHeadLine.setVisibility(View.GONE);
			mDisplay.addFooterView(mFoot);
		}
		// 实例化适配器
		mAdapter = new DiaryCommentDetailAdapter();
		// 添加适配器
		mDisplay.setAdapter(mAdapter);
	}
	
	/**
	 * 解析Json数据
	 * 
	 * @param json
	 * @param isFriend
	 */
	private void getComments(String json) {
		try {
			JSONObject object = new JSONObject(json);
				JSONArray commentArray = object.getJSONArray(
						"data");
				
				for (int j = 0; j < commentArray.length(); j++) {
					CommentResult comment = new CommentResult();
					comment.setId(commentArray.getJSONObject(j).getInt("id"));
					comment.setPhoto_id(commentArray.getJSONObject(j).getInt("photo_id"));
					comment.setAlbum_id(commentArray.getJSONObject(j).getInt("album_id"));
					comment.setDiary_id(commentArray.getJSONObject(j).getInt("diary_id"));
					comment.setContent(commentArray.getJSONObject(j).getString("content"));
					comment.setUsername(commentArray.getJSONObject(j).getString("username"));
					comment.setNickname(commentArray.getJSONObject(j).getString("nickname"));
					comment.setTime(commentArray.getJSONObject(j).getString("time"));
					comment.setAvatar(commentArray.getJSONObject(j).getString("avatar"));
					mCommentResult.add(comment);
				}
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 在评论回复上评论返回数据
		case ActivityForResultUtil.REQUESTCODE_PHOTOREPLY:
			if (resultCode == RESULT_OK) {
				// 添加回复的数据
				Bundle bundle = data.getBundleExtra("result");
				CommentResult result = new CommentResult();
				result.setNickname(StorageUtil.getString(HomeDiaryCommentDetailActivity.this, "nickname"));
				result.setContent(bundle.getString("content"));
				result.setDiary_id(mHomeDiaryDetailResult.getMessageid());
				result.setTime("刚刚");
				result.setAlbum_id(0);
				result.setPhoto_id(0);
				result.setIs_reply(1);
				result.setAvatar(StorageUtil.getString(HomeDiaryCommentDetailActivity.this, "mAvatar"));
				result.setReply_user(bundle.getString("reply_user"));
				mCommentResult.add(0, result);
				Constants.isAddFriend = true;
				CallService.writeComments(result);
				MessageUtil.showImgMsg(this, "发表成功,金币+5");
				/*List<Map<String, String>> list = null;
				// 如果已经存在评论的回复,则直接添加,否则创建
				if (result.containsKey("replys")) {
					list = (List<Map<String, String>>) result.get("replys");
					list.add(map);
				} else {
					list = new ArrayList<Map<String, String>>();
					list.add(map);
					result.put("replys", list);
				}*/
				// 更新界面
				mAdapter.notifyDataSetChanged();
				mHomeDiaryDetailResult.setComment_count(mHomeDiaryDetailResult.getComment_count() + 1);
				
			}
			break;
		// 照片的评论
		case ActivityForResultUtil.REQUESTCODE_PHOTOCOMMENT:
			if (resultCode == RESULT_OK) {
				mHeadLine.setVisibility(View.VISIBLE);
				mDisplay.removeFooterView(mFoot);
				// 添加评论,并更新界面
				Bundle bundle = data.getBundleExtra("result");
				CommentResult result = new CommentResult();
				result.setNickname(StorageUtil.getString(HomeDiaryCommentDetailActivity.this, "nickname"));
				result.setContent(bundle.getString("content"));
				result.setDiary_id(mHomeDiaryDetailResult.getMessageid());
				result.setTime("刚刚");
				result.setAlbum_id(0);
				result.setPhoto_id(0);
				result.setIs_reply(0);
				result.setReply_user("");
				result.setAvatar(StorageUtil.getString(HomeDiaryCommentDetailActivity.this, "mAvatar"));
				mCommentResult.add(0, result);
				CallService.writeComments(result);
				MessageUtil.showImgMsg(this, "发表成功,金币+5");
				mAdapter.notifyDataSetChanged();
				Constants.isAddFriend = true;
//				mDisplay.setSelection(mCommentResult.size());
				mHomeDiaryDetailResult.setComment_count(mHomeDiaryDetailResult.getComment_count() + 1);
				
			}
			break;
		}
	}

	private class DiaryCommentDetailAdapter extends BaseAdapter {
		public int getCount() {
			return mCommentResult.size();
		}

		public Object getItem(int position) {
			return mCommentResult.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unchecked")
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(
						HomeDiaryCommentDetailActivity.this).inflate(
						R.layout.photocommentdetail_activity_item, null);
				holder = new ViewHolder();
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.photocommentdetail_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.photocommentdetail_item_name);
				holder.time = (TextView) convertView
						.findViewById(R.id.photocommentdetail_item_time);
				holder.content = (TextView) convertView
						.findViewById(R.id.photocommentdetail_item_content);
				holder.replyLayout = (LinearLayout) convertView
						.findViewById(R.id.photocommentdetail_item_replay_layout);
				holder.reply = (Button) convertView
						.findViewById(R.id.photocommentdetail_item_reply);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final CommentResult result = mCommentResult.get(position);
			// 如果存在评论的回复显示回复Layout,并根据回复的数量显示内容,否则则隐藏回复Layout
			/*if (result.containsKey("replys")) {
				holder.replyLayout.setVisibility(View.VISIBLE);
				List<Map<String, String>> list = (List<Map<String, String>>) result
						.get("replys");
				holder.replyLayout.removeAllViews();
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> map = list.get(i);
					PhotoReplyLayout layout = new PhotoReplyLayout(
							DiaryCommentDetailActivity.this, mKXApplication);
					holder.replyLayout.addView(layout.getLayout());
					holder.replyLayout.invalidate();
					layout.setAvatar(Integer.parseInt(map.get("avatar")));
					layout.setName(map.get("name"));
					layout.setTime(map.get("time"));
					layout.setContent(new TextUtil(mKXApplication).replace(map
							.get("content")));
				}
			} else {
				holder.replyLayout.setVisibility(View.GONE);
			}*/
			// 显示评论的内容
//			holder.avatar.setImageBitmap(mKXApplication.getAvatar(result.getAlbum_id()));
			ImageLoader.getInstance().displayImage(Constants.getImageUrl() + result.getAvatar(), holder.avatar, ImageUtil.getOption());
			holder.name.setText(result.getNickname().toString());
			holder.time.setText(result.getTime().toString());
//			holder.content.setText(new TextUtil(mKXApplication).replace(result.getContent().toString()));
			if (result.getIs_reply() == 1) {
				holder.content.setText(Html.fromHtml(new TextUtil(mKXApplication).getBlueString(result
						.getContent().toString()).toString()));
			}else{
				holder.content.setText(Html.fromHtml(new TextUtil(mKXApplication).getBlueString(result
						.getContent().toString()).toString()));
			}
			holder.reply.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// 跳转到回复界面
					mReplyPosition = position;
					Intent intent = new Intent(
							HomeDiaryCommentDetailActivity.this,
							PhotoReplyActivity.class);
					intent.putExtra("reply_user", result.getNickname().toString());
					intent.putExtra("content", result.getContent().toString());
					startActivityForResult(intent,
							ActivityForResultUtil.REQUESTCODE_PHOTOREPLY);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView avatar;
			TextView name;
			TextView time;
			TextView content;
			LinearLayout replyLayout;
			Button reply;
		}
	}
}
