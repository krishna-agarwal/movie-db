package com.example.my_movie_db;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;


public class MvResult extends Activity {
	
	HttpClient client;
	TextView tv;
	ImageView iv;
	Bitmap bitmap;
	final static String URL = "http://api.themoviedb.org/2.1/Movie.search/en/json/cb9c1ff947ecd573b4be7a9eea0a5ed7/";
	JSONObject json,jsonx;
	
	String Released,Runtime,Genre,Director,Writer,Actors,imdbRating,imdbVotes,Overview;
	Button bTeam,bPoster,bOverview;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mv_result);
		
		Bundle bundle = this.getIntent().getExtras();
		String MV_NAME = bundle.getString("movie");
		
		
		tv = (TextView)findViewById(R.id.tv_details);
		iv = (ImageView)findViewById(R.id.iv_poster);
		bTeam = (Button)findViewById(R.id.b_team);
		bPoster = (Button)findViewById(R.id.b_poster);
		bOverview = (Button)findViewById(R.id.b_overview);
		client = new DefaultHttpClient();
		
		new Read().execute(MV_NAME);
		
	}
	
	public JSONObject mvDetails(String movieName) throws ClientProtocolException,IOException,JSONException, URISyntaxException{
		
		StringBuilder url = new StringBuilder(URL);
		url.append(movieName);
		String final_url = url.toString();
		
		HttpGet get = new HttpGet(final_url.replaceAll(" ", "%20"));
		HttpResponse r = client.execute(get);
		
		int status = r.getStatusLine().getStatusCode();
		if(status == 200){
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			
			JSONArray jar = new JSONArray(data);
			JSONObject job = jar.getJSONObject(0);
			return job;
		}else{
			Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
			return null;
		}
	
	}
	
	
public JSONObject mvDetails1(String movieName) throws ClientProtocolException,IOException,JSONException, URISyntaxException{
		
		
		HttpGet get = new HttpGet(movieName);
		HttpResponse r = client.execute(get);
		
		int status = r.getStatusLine().getStatusCode();
		if(status == 200){
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			JSONObject job = new JSONObject(data);
			return job;
		}else{
			Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
			return null;
		}
	
	}
	
	
	
	public class Read extends AsyncTask<String,Integer,String> implements OnClickListener{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				json = mvDetails(params[0]);
				JSONArray jar1 = json.getJSONArray("posters");
				JSONObject json1 = jar1.getJSONObject(3);
				JSONObject json2 = json1.getJSONObject("image");
				String Img_url = json2.getString("url");
				java.net.URL img = new java.net.URL(Img_url);
				
				Overview = json.getString("overview");
				
				new ReadImage().execute(img);
				
				String imdb_id = json.getString("imdb_id");
				String url1 = "http://www.omdbapi.com/?i="+imdb_id;
				
				jsonx = mvDetails1(url1);
				Released =  jsonx.getString("Released");
				Runtime = jsonx.getString("Runtime");
				Genre = jsonx.getString("Genre");
				Director = jsonx.getString("Director");
				Writer = jsonx.getString("Writer");
				Actors = jsonx.getString("Actors");
				imdbRating = jsonx.getString("imdbRating");
				imdbVotes = jsonx.getString("imdbVotes");
				
				return null;
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			tv.setTextColor(Color.WHITE);
			tv.setText("Released : "+Released+"\nDuration : "+Runtime+"\nGenre : "+Genre+"\nImdb Rating : "+imdbRating);
			bTeam.setOnClickListener(this);
		//	bPoster.setOnClickListener(this);
			bOverview.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.b_overview :
					//Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_LONG).show();
					Bundle b = new Bundle();
					b.putString("overView", Overview);
					Intent i = new Intent(MvResult.this,ShowDialog.class);
					i.putExtras(b);
					startActivity(i);
					break;
				case R.id.b_team :
					Bundle b1 = new Bundle();
					b1.putString("Director", Director);
					b1.putString("Writer", Writer);
					b1.putString("Actors", Actors);
					Intent i1 = new Intent(MvResult.this,ShowDialog.class);
					i1.putExtras(b1);
					startActivity(i1);
					break;
			}
			
		}
				
	}
	
	public class ReadImage extends AsyncTask<java.net.URL,Void,Bitmap> implements OnClickListener{
		
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//super.onPreExecute();
			
			
		}

		@Override
		protected Bitmap doInBackground(java.net.URL... params) {
			// TODO Auto-generated method stub
			
				HttpURLConnection connection;
				try {
					connection = (HttpURLConnection)params[0].openConnection();
					connection.setDoInput(true);
			        connection.connect();
			        InputStream is = connection.getInputStream();
			        
			        BufferedInputStream bis = new BufferedInputStream(is);
			        bitmap = BitmapFactory.decodeStream(bis);
			       // Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
			        
			        return bitmap;
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub	

			iv.setImageBitmap(bitmap);
			iv.setScaleType(ScaleType.FIT_XY);
			
			bPoster.setOnClickListener(this);
				}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch(v.getId()){
				case R.id.b_poster :
					WallpaperManager myWallpaperManager = WallpaperManager
                    .getInstance(getApplicationContext());
				try {
					myWallpaperManager.setBitmap(bitmap);
					Toast.makeText(MvResult.this, "Wallpaper Changed",
		                    Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
			}
			
		}
		
	}



}
