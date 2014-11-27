package com.ihgoo.rosi.net;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.parser.ParserLargeImage;

public class LargeImageAsync extends AsyncTask<String, String, ImageBean>{

	private AndroidHttpClient client = AndroidHttpClient.newInstance("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
	private HttpContext localContext;
	private String ret;
	



	@Override
	public ImageBean doInBackground(String... arg0) {
		
		
		HttpGet httpGet = new HttpGet(arg0[0]);
		HttpEntity httpEntity;
		try {
			httpEntity = client.execute(httpGet, localContext).getEntity();
			ret = EntityUtils.toString(httpEntity, "utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (client!=null) {
				client.close();
			}
		}
		
		return ParserLargeImage.parser(ret);
	}





}
