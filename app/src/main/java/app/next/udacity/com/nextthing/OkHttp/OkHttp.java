package app.next.udacity.com.nextthing.OkHttp;

import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttp {


    public static OkHttpClient client = new OkHttpClient();


    public static String get(URL url) throws IOException {

        HttpURLConnection connection = client.open(url);
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            byte[] response = readFully(in);
            return new String(response, "UTF-8");
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static OkHttpResponse getWizHttpCodeReturn(URL url) throws IOException {

        HttpURLConnection connection = client.open(url);
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            byte[] response = readFully(in);
            OkHttpResponse okHttpResponse = new OkHttpResponse();
            okHttpResponse.setResponse(new String(response, "UTF-8"));
            okHttpResponse.setHttpCode(connection.getResponseCode());
            return okHttpResponse;
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static OkHttpResponse post(URL url, byte[] body) throws IOException {

        HttpURLConnection connection = client.open(url);
        OutputStream out = null;
        InputStream in = null;
        try {
            // Write the request.
            connection.setRequestMethod("POST");
            out = connection.getOutputStream();
            out.write(body);
            out.close();

            // Read the response.
            int responseCode = connection.getResponseCode();
            if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = connection.getErrorStream();
            } else {
                in = connection.getInputStream();
            }

            String response = readFirstLine(in);
            OkHttpResponse ok = new OkHttpResponse();
            ok.setResponse(response);
            ok.setHttpCode(responseCode);
            return ok ;
        } finally {
            // Clean up.
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }
    }

    public static String put(URL url, byte[] body) throws IOException {

        HttpURLConnection connection = client.open(url);
        OutputStream out = null;
        InputStream in = null;
        try {
            // Write the request.
            connection.setRequestMethod("PUT");
            out = connection.getOutputStream();
            out.write(body);
            out.close();

            // Read the response.
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                in = connection.getErrorStream();
            } else {
                in = connection.getInputStream();
            }

            return readFirstLine(in);
        } finally {
            // Clean up.
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }
    }

    public static OkHttpResponse putWizHttpCodeReturn(URL url, byte[] body) throws IOException {

        HttpURLConnection connection = client.open(url);
        OutputStream out = null;
        InputStream in = null;
        try {
            // Write the request.
            connection.setRequestMethod("PUT");
            out = connection.getOutputStream();
            out.write(body);
            out.close();

            // Read the response.
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                in = connection.getErrorStream();
            } else {
                in = connection.getInputStream();
            }
            OkHttpResponse okHttpResponse = new OkHttpResponse();
            okHttpResponse.setResponse(readFirstLine(in));
            okHttpResponse.setHttpCode(connection.getResponseCode());
            return okHttpResponse;
        } finally {
            // Clean up.
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }
    }

    public static String readFirstLine(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return reader.readLine();
    }

    public static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    public static void main(String[] args) throws IOException {


    }
}