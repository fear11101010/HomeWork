package com.example.homework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class WebService {

	private static final String hostURL = "http://darksider.byethost13.com/action_perform.php";
	private HttpClient httpClient;
	public WebService(){
		StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(tp);
		httpClient = new DefaultHttpClient();
	}
	public String InsertData(List<NameValuePair> data){
		try{
			
			JSONObject serverData = getWebData(data);
			if(serverData==null) return "Can`t connect to server";
			int connectionState=0;
			if(serverData.has("connection")){
				connectionState = serverData.getInt("connection");
				if(connectionState==0) return "Can`t connect to server";
				if(serverData.has("insert")){
					if(serverData.getInt("insert")==1) return "Registration complete";
					else return "An error occure while registering. Try again later";
				}
			}
			else return "Can`t connect to server";
		}catch(Exception e){
			Log.d("error", e.getMessage());
			return "An error occure while registering. Try again later";
		}
		return null;
		
	}
	public int logIn(List<NameValuePair> data){
		try{
		    
			JSONObject serverData = getWebData(data);
		    if(serverData==null) return -1;
		    int connectionState=0;
		    if(serverData.has("connection")){
		    	connectionState = serverData.getInt("connection");
		        if(connectionState==0) return -2;
		        if(serverData.has("login")){
		        	if(serverData.getInt("login")==1) return serverData.getInt("id");
		        	else return -3;
		        }
		    }
		else return -1;
		}catch(Exception e){
			return -1;
		}
		
		
		return 0;
		
	}
	public ArrayList<String> getUserDetail(List<NameValuePair> data) {
		
		try{
			ArrayList<String> l = new ArrayList<String>();
			JSONObject serverData = getWebData(data);
			if(serverData==null)return null;
			int connectionState=0;
			if(serverData.has("connection")){
				connectionState = serverData.getInt("connection");
				if(connectionState==0) return null;
				if(serverData.has("detail")){
					JSONArray detail = serverData.getJSONArray("detail");
					for (int i = 0; i < detail.length(); i++) {
						l.add(detail.getString(i));
					}
					return l;
				}
				else return null;
			}
		}catch(Exception e){
			Log.d("error", e.getMessage());
			e.printStackTrace();
		}
		return null;
		
	}
    public int updateDetail(List<NameValuePair> data) {
		
		try{
			
			JSONObject serverData = getWebData(data);
			if(serverData==null)return 0;
			int connectionState=0;
			if(serverData.has("connection")){
				connectionState = serverData.getInt("connection");
				if(connectionState==0) return 0;
				if(serverData.has("update")){
					if(serverData.getInt("update")==1) return 1;
					else return 0;
				}
				else return 0;
			}
		}catch(Exception e){
			Log.d("error", e.getMessage());
			e.printStackTrace();
		}
		return 0;
		
	}
     
     

	private JSONObject getWebData(List<NameValuePair> data){
		HttpPost hp = new HttpPost(hostURL);
		JSONObject serverData = null;
		try{
			HttpEntity he = new UrlEncodedFormEntity(data);
			hp.setEntity(he);
			HttpResponse hr = httpClient.execute(hp);
			BufferedReader br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
			StringBuilder sb = new StringBuilder();
			String json = br.readLine();
			while(json!=null){
				sb.append(json);
				json = br.readLine();
			}
			serverData = new JSONObject(sb.toString());
			Log.d("msg", sb.toString());
		}catch(Exception e){
			//Log.d("error", e.getMessage());
			e.printStackTrace();
			return serverData;
		}
		return serverData;
	}
}
