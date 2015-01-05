package app.next.udacity.com.nextthing.LeanCloud;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Shaman on 1/5/15.
 */
public class NextThingObject {

    public static final String NEXT_THING = "NextThing";

    public static void save (SaveCallback saveCallback) {
        AVObject nextThing = new AVObject(NEXT_THING);
        nextThing.put("url", "http://www.pingwest.com");
        nextThing.put("description", "科技新闻");
        nextThing.put("title", "Pingwest");
        nextThing.put("vote", 0);
        nextThing.saveInBackground(saveCallback);
    }
}
