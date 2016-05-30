package com.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**图片工具类**/
public class ImgUtil {
	 public static final int UNCONSTRAINED = -1;
	
	/** 图片压缩 */
	public static Bitmap recyclePic(Context context, int resId) {
		Options opt = new Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		//Bitma
		return BitmapFactory.decodeStream(is, null, opt);
	}
	/**得到压缩后的资源图片**/
	public static BitmapDrawable getBitmapDrawable(Context context, int drawableId) {
		Bitmap bitmap = recyclePic(context, drawableId);
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/**
	 * 得到图片
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapByPath(String path) {
		File file = new File(path);
		Bitmap bitmap = null;
		if(!file.exists()) {
			return null;
		}
		Options opt = new Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = compreImg((int) file.length());
		bitmap =   BitmapFactory.decodeFile(path, opt);
		try{
			bitmap.getWidth();
		}catch(Exception e) {
//			Log.v("LOG", "ImgUtil getBitmapByPath e:"+e.getMessage());
			return null;
		}

		return bitmap;
	}


	/**根据view 大小加载图片**/
	public static Bitmap getBitmapByBytes(byte[] flush) {
		Options opt = new Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = compreImg(flush.length);
		return BitmapFactory.decodeByteArray(flush, 0, flush.length, opt);
	}

	public static Bitmap getBitmap(byte[] data) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		try{
			bitmap.getWidth();
		}catch(Exception e) {
			return null;
		}
		return bitmap;
	}

	/**得到高清图片**/
	public static Bitmap getBigBitmapByPath(String path,int widht, int height) {
		File file = new File(path);
		Bitmap bitmap = null;
		if(!file.exists()) {
			return null;
		}

		Options o = getOptions(path);
		int w = o.outWidth;
		int scale = w/widht;

		Options opt = new Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = scale;
		bitmap =   BitmapFactory.decodeFile(path, opt);
		try{
			bitmap.getWidth();
		}catch(Exception e) {
//			Log.v("LOG", "ImgUtil getBitmapByPath e:"+e.getMessage());
			return null;
		}
		return bitmap;
	}
	
	/**压缩图片大小**/
	private static int compreImg(int size) {
		int compress = 1;
		 // 数字越大读出的图片占用的heap越小 不然总是溢出
        if (size < 20480) {       // 0-20k
         compress = 1;
        } else if (size < 51200) { // 20-50k
         compress = 2;
        } else if (size < 307200) { // 50-300k
         compress = 4;
        } else if (size < 819200) { // 300-800k
         compress = 6;
        } else if (size < 1048576) { // 800-1024k
         compress = 8;
        } else {
         compress = 10;
        }
        return compress;
	}
	
	  /*  获得设置信息 */  
	 public static Options getOptions(String path){  
		  Options options = new Options();  
		  options.inJustDecodeBounds = true;//只描边，不读取数据  
		  BitmapFactory.decodeFile(path, options);  
		  return options;  
	 }





	 /**
	  * 保存图片
	  * @param bitmap
	  * @param path
	  */
	 public static void saveImageToPath(Bitmap bitmap, String path) {
		 File file = new File(path);
		 if(!file.getParentFile().exists()) {
			 file.getParentFile().mkdirs();
		 }
		 try{
			 FileOutputStream fos = new FileOutputStream(file);
			 bitmap.compress(CompressFormat.PNG, 100, fos);
			 fos.flush();
			 fos.close();
		 }catch(Exception e) {
//			 Log.v("LOG", "ImgUtil saveImageToPath e: "+e.getMessage());
			 return ;
		 }
	 }

	/**
	 * 保存图片
	 * @param bitmap
	 * @param format 格式
	 * @param path
	 */
	public static void saveImageToPath(Bitmap bitmap,CompressFormat format, String path) {
		File file = new File(path);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try{
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(format, 100, fos);
			fos.flush();
			fos.close();
		}catch(Exception e) {
//			 Log.v("LOG", "ImgUtil saveImageToPath e: "+e.getMessage());
			return ;
		}
	}



	 /****
	  * 保存图片
	  * @param flush
	  * @param path
	  */
	 public static void saveImageByBytes(byte[] flush, String path) {
		 File file = new File(path);
		 if(!file.getParentFile().exists()) {
			 file.getParentFile().mkdirs();
		 }
		 try{
			 FileOutputStream fos = new FileOutputStream(file);
			 fos.write(flush);
			 fos.flush();
			 fos.close();
		 }catch(Exception e) {
//			 Log.v("LOG", "ImgUtil saveImageByBytes e: "+e.getMessage());
			 return ;
		 }
	 }
	 
	 /**
	  * Bitmap 转为字节数据
	  * @param bitmap
	  * @return
	  */
	 public static byte[] onBitmapToBytes(Bitmap bitmap) {
		 try{
			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 bitmap.compress(CompressFormat.PNG, 100, bos);
			 byte[] flush = bos.toByteArray();
			 bos.close();
			 return flush;
		 }catch(Exception e) {
//			 Log.v("LOG", "ImgUtil saveImageToPath e: "+e.getMessage());
			 return null;
		 }
	 }


	public static Bitmap changleBitmap(Bitmap desBitmap, int width,int height) {
		Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
		Rect desRect  = new Rect(0,0,desBitmap.getWidth(), desBitmap.getHeight());
		Rect outRect = new Rect(0,0, width,height);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(desBitmap,desRect,outRect, null);

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bitmap;

	}


	/**
	 * 创建带logo 的二维码图片
	 * @param bgBitmap
	 * @param logoBitmap
     * @return
     */
	public static Bitmap createLogoBitmap(Bitmap bgBitmap, Bitmap logoBitmap) {

		Bitmap resultBitmap = Bitmap.createBitmap(bgBitmap.getWidth(), bgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(resultBitmap);// 使用空白图片生成canvas
		canvas.drawColor(Color.WHITE);
		int weigth = bgBitmap.getHeight() / 5;
		Rect srcRect = new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight());
		Paint paint = new Paint();

		canvas.drawBitmap(bgBitmap, srcRect, srcRect, paint);
		logoBitmap = ImgUtil.changleBitmap(logoBitmap, weigth,weigth);
		Rect logRect = new Rect(0, 0, logoBitmap.getWidth(), logoBitmap.getHeight());

		Rect desRect = new Rect(weigth * 2, weigth * 2, weigth * 3, weigth * 2);

		canvas.clipRect(desRect, Region.Op.UNION);
		canvas.drawBitmap(logoBitmap, weigth * 2, weigth * 2, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);

		return resultBitmap;

	}

}
