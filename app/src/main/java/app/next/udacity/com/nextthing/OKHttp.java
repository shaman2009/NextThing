package app.next.udacity.com.nextthing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.next.udacity.com.nextthing.model.NextThingPO;

public class OKHttp {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException, JSONException {


    }
    public static ArrayList<NextThingPO> getThings() throws IOException {
        OKHttp example = new OKHttp();
        String response = example.run("http://v.zivoo.cn:8080/indefensible-launcher/thing");
        Type listType = new TypeToken<List<NextThingPO>>() {}.getType();
        ArrayList<NextThingPO> list = new Gson().fromJson(response, listType);
        return list;
    }
}
