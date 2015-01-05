package app.next.udacity.com.nextthing.LeanCloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

/**
 * Created by Shaman on 1/5/15.
 */
public class User {
    public void signup () {
        AVUser user = new AVUser();

    }
    public void login() {
        AVUser.logInInBackground("username", "password", new LogInCallback() {
            public void done(AVUser user, AVException e) {
                if (user != null) {
                    // 登录成功
                    user.getSessionToken();
                } else {
                    // 登录失败
                }
            }
        });
    }
}
