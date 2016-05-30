package com.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**保存文件类**/
public class SaveFileUtil {
	
	/**保存序列文件**/
	public static void writeObjectToFile(Object obj, String path) {
		File file = new File(path);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception e) {
//			Log.v("LOG", "SaveFileUtil writeObjectToFile e: "+e.getMessage());
			e.printStackTrace();
			return ;
		}
	}
	
	/**保存字节数据到文件中**/
	public static void saveBytesToFile(byte[] flush, File file) {
		try {
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(flush);
			fos.flush();
			fos.close();
		} catch (Exception e) {
//			Log.v("LOG", "SaveFileUtil saveBytesToFile e: "+e.getMessage());
			e.printStackTrace();
			return ;
		}
	} 
	
	/**打印日志**/
	public static void printLog(Context context, String log) {
		File file = new File(context.getExternalCacheDir(), "log.txt");
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write(log.getBytes());
			fos.write("\r\n".getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
