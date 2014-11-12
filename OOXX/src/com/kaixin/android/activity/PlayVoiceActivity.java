package com.kaixin.android.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kaixin.android.R;
import com.kaixin.android.common.Constants;

public class PlayVoiceActivity extends Activity {
	
	private String path;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveinfo_voice);
        
        path = getIntent().getStringExtra("url");
        
        Uri uri = Uri.parse(Constants.getVoiceUrl() + path);  
        //Uri uri = Uri.parse("file:///sdcard/music/dufuhenmang.3gp");  
        VideoView videoView = (VideoView)this.findViewById(R.id.videoView);  
        videoView.setMediaController(new MediaController(this));  
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();  
    }
}