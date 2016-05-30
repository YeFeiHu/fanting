package com.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.util.math.MathUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {

	private static HttpUtil instance = null;
	private HttpUtil() {};
	public synchronized static HttpUtil getInstance() {
		if(instance == null)
			instance = new HttpUtil();
		return instance;
	}

	public HttpURLConnection getConn(String addr, String cookie) throws IOException {
		URL url = new URL(addr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//conn.setRequestMethod("GET");

		if(cookie != null && !cookie.equals("")) {
			conn.setRequestProperty("Cookie", cookie);
		}
		conn.setRequestProperty("User-Agent",
                  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31");
        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
          return conn;
	}

	/**得到验证码**/
	public byte[] getVarCode(String addr, String cookie) throws IOException {
		HttpURLConnection conn = getConn(addr, cookie);
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	 getCookie(conn);
        	BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        	byte[] flush = new byte[1024];
        	int len = 0;
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	while(-1 != (len = bis.read(flush))) {
        		bos.write(flush, 0, flush.length);
        	}
        	flush = bos.toByteArray();
        	bos.close();
        	bis.close();
        	conn.disconnect();
        	return flush;
        }
        return null;
	}

	/**得到内容**/
	public byte[] getContent(String addr, String cookie) throws IOException {
		HttpURLConnection conn = getConn(addr, cookie);
		conn.setInstanceFollowRedirects(true);
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	 getCookie(conn);
        	BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        	byte[] flush = new byte[1024];
        	int len = 0;
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	while(-1 != (len = bis.read(flush))) {
        		bos.write(flush, 0, len);
        	}
        	flush = bos.toByteArray();
        	bos.close();
        	bis.close();
        	conn.disconnect();
        	return flush;
        }
        return null;
	}

	public String getCookies() {
		Iterator iter = cookies.entrySet().iterator();
		String data = "";
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String val = (String) entry.getValue();
			String cookie = val.substring(0, val.indexOf(";")+1);
			System.out.println("key "+key+" :  value ======== "+val);
			System.out.println(cookie);
			data += cookie;
		}
		for(int i = 0;i < 2; i++) {
			System.out.println();
		}
		System.out.println("Cookie === "+data);
		return data;
	}

	HashMap<String,String> cookies;
	private void getCookie(HttpURLConnection conn) {
		if(cookies == null)
			cookies = new HashMap<String,String>();
		String values = null;
        for(int i = 0; ((values = conn.getHeaderField(i)) != null) ; i++) {
     	   String key = conn.getHeaderFieldKey(i);
        	System.out.println(key+": "+values);

     	   if(key != null && key.equals("Set-Cookie")) {
     		   cookies.put(key, values);
     		   System.out.println(key+": ============ "+values);
     	   }
        }
	}

	/**请求图片**/
	public byte[] requestImage(HttpURLConnection conn) {
		try{
			if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] flush = new byte[is.available() > 2048 ? is.available() : 2048];
				int len = 0;
				long time = System.currentTimeMillis();
				while(-1 != (len = is.read(flush))) {
					bos.write(flush, 0, len);
					if(time+1000 < System.currentTimeMillis()) {
						time = System.currentTimeMillis();
						Log.v("LOG", "NetUtil requestImage leng "+ bos.size()/1024);
					}
				}
				is.close();
				conn.disconnect();
				flush = bos.toByteArray();
				Log.v("LOG", "leng size "+ flush.length/1024);
				bos.close();
				return flush;
			}
			return null;
		} catch (Exception e) {
			Log.v("LOG", "NetUtil.class checkEmail error:"+e.getMessage());
			return null;
		}
	}

	public String sendData(String path, String data) {
		URL url = null;
		String msg = null;
		try {
			HttpURLConnection conn = getConn(path, getCookies());
			url = new URL(path);
			conn.setDoOutput(true);//允许向服务器输出内容
			conn.setDoInput(true);//允许读取服务器返回的内容
			conn.setUseCaches(false);//不缓存
			OutputStream os =conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();
			int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
			if(responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();

				byte[] flush = new byte[1024];
				int len = 0;
				while(-1 != (len = is.read(flush))) {
					msg = new String(flush, 0, len);
				}
				is.close();
				conn.disconnect();
				msg = new String(flush, 0, flush.length, "gb2312");
			} else {
				msg = "连接失败";
				Log.v("LOG", "连接失败");
			}
			return msg;
		} catch (Exception e) {
			Log.v("LOG", "NetUtil.class checkEmail error:"+e.getMessage());
			return "error";
		}
	}

	/**
	 * 判断Url连接是否有文件存在
	 * @param url
	 * @return
	 */
	public static boolean hasFile(String url) {
		HttpURLConnection connection = null;
		try {
			URL u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			connection.connect();
			return HttpURLConnection.HTTP_OK == connection.getResponseCode() && connection.getContentLength() > 0;
		}
		catch ( Exception e ) {
			e.printStackTrace();
			return false;
		}
		finally {
			if ( connection != null )
				connection.disconnect();
		}
	}

	public static String sendPostData(String url, String data) {
		HttpURLConnection connection = null;
		try {
			URL u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);//允许向服务器输出内容
			connection.setDoInput(true);//允许读取服务器返回的内容
			connection.setUseCaches(false);//不缓存
			OutputStream os =connection.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();
			String msg ="null";
			if(HttpURLConnection.HTTP_OK == connection.getResponseCode() && connection.getContentLength() > 0) {
				InputStream is = connection.getInputStream();

				byte[] flush = new byte[1024];
				int len = 0;
				while(-1 != (len = is.read(flush))) {
					msg = new String(flush, 0, len);
				}
				is.close();
				connection.disconnect();
				msg = new String(flush, 0, flush.length, "gb2312");
				return msg;
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
//			Log.v("LOG", "error = "+e.getMessage());
		}
		finally {
			if ( connection != null )
				connection.disconnect();
		}

		return null;
	}

	/**
	 * 下载图片
	 * @param url
	 * @return
	 */
	public static byte[] downloadImage(String url) {
		  boolean has = HttpUtil.hasFile(url);
		  String iconNet;
          byte[] flush = null;
          try {
				flush = HttpUtil.getInstance().getContent(url, null);
				return flush;
          } catch (IOException e) {
				e.printStackTrace();
				return null;
		}

	}

	/**
	 * 抓取数据
	 * @param is
	 * @return
     */
	public static byte[] downloadContent(@NonNull InputStream is) {

		return null;
	}


	//得到连接
	public static HttpURLConnection getConnect(String url) {
		HttpURLConnection connection = null;
		try {
			URL urlConn = new URL(url);
			connection = (HttpURLConnection) urlConn.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);//允许读取服务器返回的内容
			connection.setUseCaches(false);//不缓存
			connection.setInstanceFollowRedirects( true );
			connection.setConnectTimeout( 3000 );
			connection.setReadTimeout( 3000 );
			connection
					.setRequestProperty( "User-Agent",
							"Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; MB525 Build/3.4.2-117) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}


	/** 得到下载文件名和文件大小
	 * @param url
	 *
	 * @return
	 * 返回值1 为文件名 返回值2 为文件大小
	 * 默认返回空
	 */
	public static String[] getDownFileInfo(String url) {
		boolean has = HttpUtil.hasFile(url);
		HttpURLConnection httpURLConnection = null;
		try{
			if(has) {
				httpURLConnection = getConnect(url);
				String fileName = null;
				if(HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode() &&
						httpURLConnection.getContentLength() > 0) {
					long size = httpURLConnection.getContentLength();
					String disposition = httpURLConnection.getHeaderField("Content-Disposition");
					if(disposition != null && disposition.length() > 5) {
						disposition = disposition.trim();
						Pattern namePattern = Pattern.compile("filename=\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);
						Matcher nameMatcher = namePattern.matcher(disposition);
						if(nameMatcher.find()) {
							String filename = nameMatcher.group();
							int startIndex = filename.indexOf("\"");
							int endIndex = filename.lastIndexOf("\"");
							if(startIndex > 0 && endIndex > 0 && startIndex < endIndex) {
								fileName = filename.substring(startIndex+1, endIndex);
							}

						}
					}

					if(fileName == null) {
						String path = httpURLConnection.getURL().getPath();
						int start = path.lastIndexOf("/");
						path = path.substring(start+1, path.length());
						fileName = path;
					}

//					if(fileName != null) {
//					//	fileName = URLDecoder.decode(fileName,"UTF-8");
//					}

					String strSize = MathUtil.convertUnit(size);
					return new String[]{fileName, strSize};

				}

			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return null;
	}


	public static String downUrl(String url,String userAgent, String savePath) {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url)
				.addHeader("User-Agent", userAgent)
				.build();
		Response response;
		try {
			response = okHttpClient.newCall(request).execute();
			if(response.isSuccessful()) {
				String content = response.body().string();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return null;
	}

}
