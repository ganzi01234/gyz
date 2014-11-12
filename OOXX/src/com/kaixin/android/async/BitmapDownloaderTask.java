package com.kaixin.android.async;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.kaixin.android.common.Constants;
import com.kaixin.android.utils.CallService;

/**
 * 图片加载工具类
 */
public class BitmapDownloaderTask extends AsyncTask<Map<String, String>, Void, Bitmap> {
    // 使用WeakReference解决内存问题，可以有效避免OOM的发生
    private final WeakReference<ImageView> imageViewReference;

    public BitmapDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }
    // 实际的下载线程，内部其实是concurrent线程，所以不会阻塞
    @Override
    protected Bitmap doInBackground(Map<String, String>... params) {
    	InputStream is = (InputStream) CallService.getObject(Constants.getUrl() + "/PhotoServlet", new String[]
    			{ "albumId", "filename", "original", "uid" }, new String[]
    			{ params[0].get("albumId"), params[0].get("filename"), params[0].get("original") , params[0].get("uid")}, true);
    	Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) { // 下载完后执行的
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap); // 下载完设置imageview为刚才下载的bitmap对象
            }
        }
    }
}
