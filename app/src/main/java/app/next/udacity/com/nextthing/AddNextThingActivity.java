package app.next.udacity.com.nextthing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import org.apache.http.HttpStatus;

import java.io.IOException;

import app.next.udacity.com.nextthing.LeanCloud.NextThingObject;
import app.next.udacity.com.nextthing.OkHttp.OkHttpResponse;
import app.next.udacity.com.nextthing.OkHttp.ThingRequest;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class AddNextThingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_next_thing);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_next_thing, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        Handler handler = new Handler();
        @InjectView(R.id.product_title)
        EditText mProductTitle;
        @InjectView(R.id.product_url)
        EditText mProductUrl;
        @InjectView(R.id.product_description)
        EditText mProductDescription;
        @InjectView(R.id.submit)
        Button mSubmit;


        String url;
        String description;
        String title;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_next_thing, container, false);
            ButterKnife.inject(this, rootView);

            AVUser currentUser = AVUser.getCurrentUser();
            if (currentUser == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                this.startActivity(intent);
                //缓存用户对象为空时， 可打开用户注册界面…
            }


            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        attemptSubmit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return rootView;
        }

        public void attemptSubmit() throws IOException {
            mProductDescription.setError(null);
            mProductTitle.setError(null);
            mProductUrl.setError(null);

            description = mProductDescription.getText().toString();
            title = mProductTitle.getText().toString();
            url = mProductUrl.getText().toString();

            boolean cancel = false;
            View focusView = null;


            if (TextUtils.isEmpty(description)) {
                mProductDescription.setError(getString(R.string.custom_error_field_required));
                focusView = mProductDescription;
                cancel = true;
            }
            if (TextUtils.isEmpty(url)) {
                mProductUrl.setError(getString(R.string.custom_error_field_required));
                focusView = mProductUrl;
                cancel = true;
            }
            if (TextUtils.isEmpty(title)) {
                mProductTitle.setError(getString(R.string.custom_error_field_required));
                focusView = mProductTitle;
                cancel = true;
            }
            if (cancel) {
                focusView.requestFocus();
            } else {
//                addNextThingRequest();
                addNextThingRequestViaLeanCloud();
            }

        }
        public void addNextThingRequestViaLeanCloud() {
            NextThingObject.save(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        //Success
                        Toast.makeText(getActivity(), R.string.toast_add_next_thing + " ... via LeanCloud", Toast.LENGTH_SHORT).show();
                    } else {
                        //Failed
                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void addNextThingRequest() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpResponse response = ThingRequest.addThing(title, description, url);
                        final int code = response.getHttpCode();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (code >= HttpStatus.SC_BAD_REQUEST) {
                                    Toast.makeText(getActivity(), R.string.http_error, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), R.string.toast_add_next_thing, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
