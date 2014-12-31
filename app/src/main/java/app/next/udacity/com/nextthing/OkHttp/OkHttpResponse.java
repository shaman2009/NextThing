package app.next.udacity.com.nextthing.OkHttp;

/**
 * Created by feng_xiang on 14-6-5.
 */
public class OkHttpResponse {
    private int httpCode;
    private String response;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
