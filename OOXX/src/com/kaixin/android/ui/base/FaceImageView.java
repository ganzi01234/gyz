package com.kaixin.android.ui.base;

import java.util.LinkedList;

import com.kaixin.android.utils.MosaicProcessor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class FaceImageView extends ImageView {

	private static Bitmap mSrc;
	private LinkedList<FaceImage> mFaceImages = new LinkedList<FaceImage>();
	private int mFaceImagePosition = -1;
	private FaceImage mFaceImage;
	public static final int min_mosaic_block_size = 10;

	public FaceImageView(Context context) {
		super(context);
	}

	public FaceImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FaceImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void addFace(Bitmap face) {
		if (mSrc.getWidth() > 100 && mSrc.getHeight() > 100) {
			face = Bitmap.createScaledBitmap(face, 100, 100, true);
		} else if (mSrc.getWidth() > 100 && mSrc.getHeight() < 100) {
			face = Bitmap.createScaledBitmap(face, mSrc.getHeight(),
					mSrc.getHeight(), true);
		} else if (mSrc.getHeight() > 100 && mSrc.getWidth() < 100) {
			face = Bitmap.createScaledBitmap(face, mSrc.getWidth(),
					mSrc.getWidth(), true);
		} else {
			face = Bitmap.createScaledBitmap(face, mSrc.getWidth(),
					mSrc.getHeight(), true);
		}

		float left = mSrc.getWidth() / 2 - face.getWidth() / 2;
		float top = mSrc.getHeight() / 2 - face.getHeight() / 2;
		float right = left + face.getWidth();
		float bottom = top + face.getHeight();
		RectF rectF = new RectF(left, top, right, bottom);
		FaceImage faceImage = new FaceImage(face, rectF);
		mFaceImages.addFirst(faceImage);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		for (int i = mFaceImages.size(); i > 0; i--) {
			mFaceImages.get(i - 1).draw(canvas);
		}
		canvas.restore();
	}

	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mSrc = bm;
	}

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mFaceImagePosition = getPosition(x, y);
			break;

		case MotionEvent.ACTION_MOVE:
			if (mFaceImagePosition != -1 && mFaceImage == null) {
				mFaceImage = mFaceImages.get(mFaceImagePosition);
				mFaceImages.remove(mFaceImagePosition);
				mFaceImages.addFirst(mFaceImage);
			}
			
			if (mFaceImage != null) {
				mFaceImage.move(x, y);
				invalidate();
			}
			
			break;

		case MotionEvent.ACTION_UP:
			mFaceImagePosition = -1;
			mFaceImage = null;
			break;
		}
		return true;
	}
	
	/**
	 * 
	 * @param bitmap
	 * @param targetRect
	 * @param blockSize
	 *            {@link #min_mosaic_block_size}
	 * @return
	 * @throws Exception
	 */
	public static Bitmap makeMosaic(Bitmap bitmap, RectF targetRect,
			int blockSize) throws OutOfMemoryError {
		if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0
				|| bitmap.isRecycled()) {
			throw new RuntimeException("bad bitmap to add mosaic");
		}
		if (blockSize < min_mosaic_block_size) {
			blockSize = min_mosaic_block_size;
		}
		if (targetRect == null) {
			targetRect = new RectF();
		}
		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		if (targetRect.isEmpty()) {
			targetRect.set(0, 0, bw, bh);
		}
		//
		int rectW = (int) targetRect.width();
		int rectH = (int) targetRect.height();
		int[] bitmapPxs = new int[bw * bh];
		// fetch bitmap pxs
		bitmap.getPixels(bitmapPxs, 0, bw, 0, 0, bw, bh);
		//
		int rowCount = (int) Math.ceil((float) rectH / blockSize);
		int columnCount = (int) Math.ceil((float) rectW / blockSize);
		int maxX = bw;
		int maxY = bh;
		for (int r = 0; r < rowCount; r++) { // row loop
			for (int c = 0; c < columnCount; c++) {// column loop
				int startX = (int) (targetRect.left + c * blockSize + 1);
				int startY = (int) (targetRect.top + r * blockSize + 1);
				dimBlock(bitmapPxs, startX, startY, blockSize, maxX, maxY);
			}
		}
		return Bitmap.createBitmap(bitmapPxs, bw, bh, Config.ARGB_8888);
	}

	/**
	 * �ӿ���ȡ���Ŵ󣬴Ӷ�ﵽ����˵�ģ��Ч��
	 * 
	 * @param pxs
	 * @param startX
	 * @param startY
	 * @param blockSize
	 * @param maxX
	 * @param maxY
	 */
	private static void dimBlock(int[] pxs, int startX, int startY,
			int blockSize, int maxX, int maxY) {
		int stopX = startX + blockSize - 1;
		int stopY = startY + blockSize - 1;
		if (stopX > maxX) {
			stopX = maxX;
		}
		if (stopY > maxY) {
			stopY = maxY;
		}
		//
		int sampleColorX = startX + blockSize / 2;
		int sampleColorY = startY + blockSize / 2;
		//
		if (sampleColorX > maxX) {
			sampleColorX = maxX;
		}
		if (sampleColorY > maxY) {
			sampleColorY = maxY;
		}
		int colorLinePosition = (sampleColorY - 1) * maxX;
		int sampleColor = pxs[colorLinePosition + sampleColorX - 1];// ���ش�1��ʼ�����������0��ʼ
		for (int y = startY; y <= stopY; y++) {
			int p = (y - 1) * maxX;
			for (int x = startX; x <= stopX; x++) {
				// ���ش�1��ʼ�����������0��ʼ
				pxs[p + x - 1] = sampleColor;
			}
		}
	}

	public int getPosition(int x, int y) {
		int position = -1;
		for (int i = 0; i < mFaceImages.size(); i++) {
			if (mFaceImages.get(i).getmRectF().contains(x, y)) {
				position = i;
				break;
			}
		}
		return position;
	}

	public LinkedList<FaceImage> getFaceImages() {
		return mFaceImages;
	}
}
