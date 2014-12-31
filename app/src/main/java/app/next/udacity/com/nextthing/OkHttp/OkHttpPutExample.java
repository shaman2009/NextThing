package app.next.udacity.com.nextthing.OkHttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;


public class OkHttpPutExample {
	public static OkHttpClient client = new OkHttpClient();



	public static String put(URL url, byte[] body) throws IOException, URISyntaxException {
		HttpURLConnection connection = client.open(url);
		OutputStream out = null;
		InputStream in = null;
		try {
			// Write the request.
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "123");
			out = connection.getOutputStream();
			out.write(body);
			out.close();
			
			// Read the response.
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected HTTP response: "
						+ connection.getResponseCode() + " "
						+ connection.getResponseMessage());
			}
			in = connection.getInputStream();
			return readFirstLine(in);
		} finally {
			// Clean up.
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
	}

	public static String readFirstLine(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		return reader.readLine();
	}



	public static void main(String[] args) throws IOException, JSONException, URISyntaxException {
//		new OkHttpPutExample().getThings();
	}
}