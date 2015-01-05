package app.next.udacity.com.nextthing.LeanCloud;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Shaman on 1/5/15.
 */
public class NextThingObject {

    public static final String NEXT_THING = "NextThing";
    public static final String URL = "url";
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String VOTE = "vote";


    public static void save (String title, String description, String url, SaveCallback saveCallback) {
        AVObject nextThing = new AVObject(NEXT_THING);
        nextThing.put(URL, url);
        nextThing.put(DESCRIPTION, description);
        nextThing.put(TITLE, title);
        nextThing.put(VOTE, 0);
        nextThing.saveInBackground(saveCallback);
    }
}
