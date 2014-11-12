/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaixin.android.async;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.util.ImageUtils;
import com.kaixin.android.R;
import com.kaixin.android.activity.ShowBigImage;
import com.kaixin.android.filter.ImageCache;

public class LoadImageTask extends AsyncTask<Object, Void, Bitmap> {
	private ImageView iv = null;
    String localFullSizePath = null;
	String thumbnailPath = null;	
	String remotePath = null;
	EMMessage message = null;
    ChatType chatType;
	Activity activity;
	
	@Override
	protected Bitmap doInBackground(Object... args) {
		thumbnailPath = (String)args[0];
		localFullSizePath = (String)args[1];
        remotePath = (String)args[2];
		chatType = (ChatType) args[3];		
		iv = (ImageView)args[4];		
//		if(args[2] != null) {
		    activity = (Activity) args[5];
//		}
		message = (EMMessage) args[6];
		File file = new File(thumbnailPath);
		if(file.exists())
			return ImageUtils.decodeScaleImage(thumbnailPath, 120, 120);
		else
			return ImageUtils.decodeScaleImage(localFullSizePath, 120, 120);
		
	}
	
	protected void onPostExecute(Bitmap image) {
		if (image != null) {
            iv.setImageBitmap(image);
            ImageCache.getInstance().put(thumbnailPath, image);
			iv.setClickable(true);
			iv.setTag(thumbnailPath);
		    iv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {                   
					if(thumbnailPath != null){
					    
					    Intent intent = new Intent(activity, ShowBigImage.class);  
	                    File file = new File(localFullSizePath);
	                    if(file.exists()) {
	                        Uri uri = Uri.fromFile(file);
	                        intent.putExtra("uri", uri);                    
	                    } else {
	                        //The local full size pic does not exist yet. ShowBigImage needs to download it from the server first
	                        intent.putExtra("remotepath", remotePath);                          
	                    }  
	                    if (message.getChatType() != ChatType.Chat) {
	                        // delete the image from server after download
	                    }
	                    if(message != null && message.direct == EMMessage.Direct.RECEIVE && !message.isAcked){
							message.isAcked = true;
							try {
								//看了大图后发个已读回执给对方
								EMChatManager.getInstance().ackMessageRead(message.getFrom(), message.getMsgId());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
	                    activity.startActivity(intent); 					    
					}				    
				}
			});
		} else {
		    iv.setImageResource(R.drawable.default_image);
		}
	}
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	}
}
