package app.next.udacity.com.nextthing.OkHttp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.next.udacity.com.nextthing.model.NextThingPO;
import app.next.udacity.com.nextthing.model.NextThingRequestPO;

/**
 * Created by Shaman on 12/31/14.
 */
public class ThingRequest {

    public static final String THING_URL = "http://v.zivoo.cn:8080/indefensible-launcher/thing";

    public static ArrayList<NextThingPO> getThings() throws IOException {
        String response = OkHttp.get(new URL(THING_URL));
        Type listType = new TypeToken<List<NextThingPO>>() {}.getType();
        ArrayList<NextThingPO> list = new Gson().fromJson(response, listType);
        for (NextThingPO nextThingPO : list) {
            System.out.println(nextThingPO.getTitle());
        }
        return list;
    }
    public static OkHttpResponse addThing(String title, String description, String url) throws IOException {
        Gson gson = new Gson();
        NextThingRequestPO po = new NextThingRequestPO();
        po.setDescription(description);
        po.setTitle(title);
        po.setUrl(url);
        final String params = "j=" + gson.toJson(po).toString();
        OkHttpResponse response = OkHttp.post(new URL(THING_URL), params.getBytes());
        return response;
    }
}

