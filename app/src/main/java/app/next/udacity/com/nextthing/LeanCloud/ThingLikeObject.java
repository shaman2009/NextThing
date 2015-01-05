package app.next.udacity.com.nextthing.LeanCloud;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Shaman on 1/5/15.
 */
public class ThingLikeObject {

    public static final String THING_LIKE = "ThingLike";
    public static final String USER_ID = "userId";
    public static final String THING_ID = "thingId";

    public static void save (String userId, String thingId, SaveCallback saveCallback) {
        AVObject nextThing = new AVObject(THING_LIKE);
        nextThing.put(USER_ID, userId);
        nextThing.put(THING_ID, thingId);
        nextThing.saveInBackground(saveCallback);
    }

    public static void query(String userId, String thingId,FindCallback callback) {
        AVQuery<AVObject> query = new AVQuery<>(THING_LIKE);
        query.whereEqualTo(USER_ID, userId).whereEqualTo(THING_ID, thingId);
        query.findInBackground(callback);
    }
    public static void saveWithoutCallBack (String userId, String thingId) {
        AVObject nextThing = new AVObject(THING_LIKE);
        nextThing.put(USER_ID, userId);
        nextThing.put(THING_ID, thingId);
        nextThing.saveInBackground();
    }
}
