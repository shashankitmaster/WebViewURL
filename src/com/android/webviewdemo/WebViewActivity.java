package com.android.webviewdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WebViewActivity extends Activity {
	private TextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.webView);
		textView.setTextColor(Color.WHITE);

	/*	ProgressThread progressThread = new ProgressThread();
		progressThread.start();*/

		Button button = (Button) findViewById(R.id.score);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getScore();
			}
		});
	}

//	private class ProgressThread extends Thread {
//		@Override
//		public void run() {
//
//			getScore();
//			try {
//				Thread.sleep(150000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	private void getScore() {
		HttpClient httpclient = new DefaultHttpClient();
		// HttpGet httpget = new
		// HttpGet("http://feeds.delicious.com/v2/json");
		HttpGet httpget = new HttpGet(
				"http://json-cricket.appspot.com/score.json");
		HttpResponse response;
		try {
			String x = "";
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				JSONObject entries = new JSONObject(result);

				x += "Batting Team : " + entries.getString("batting_team")
						+ "\n";
				x += "Date: " + entries.getString("date") + "\n";
				/*
				 * array = post.getJSONArray("t"); x += array.get(i);
				 */
				x += "Match: " + entries.getString("match") + "\n";
				x += "Score: " + entries.getString("score") + "\n";
				x += "Summary: " + entries.getString("summary") + "\n\n";

				// JSONObject post = null;
				// for (int i = 0; i < entries.length(); i++) {
				// //post = entries.getJSONObject(i);
				// x += "------------\n";
				// System.gc();
				// x += "URL :" + post.getString("batting_team") + "\n";
				// x += "Date:" + post.getString("date") + "\n";
				// /*
				// * array = post.getJSONArray("t"); x += array.get(i);
				// */
				// x += "Match:" + post.getString("match") + "\n";
				// x += "Score:" + post.getString("score") + "\n";
				// x += "Summary:" + post.getString("summary") + "\n\n";
				// post = null;
				// System.gc();
				// }

				//Log.i("JSON", x);
				textView.setText(x);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}