package app.next.udacity.com.nextthing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.next.udacity.com.nextthing.GreenDao.Dao;
import app.next.udacity.com.nextthing.GreenDao.Next;
import app.next.udacity.com.nextthing.LeanCloud.NextThingObject;
import app.next.udacity.com.nextthing.LeanCloud.ThingLikeObject;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingFragment extends Fragment implements WebViewCallBack {
    private NextThingAdapter mNextThingAdapter;

    @InjectView(R.id.listView)
    PullToRefreshListView mListView;
    Handler handler = new Handler();
    @InjectView(R.id.webview)
    WebView mWebview;
    @InjectView(R.id.webLayout)
    FrameLayout mWebLayout;

    public NextThingFragment() {
        mNextThingAdapter = new NextThingAdapter();
        mNextThingAdapter.addCallBack(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        initData();
        mListView.setAdapter(mNextThingAdapter);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                pullData();
            }
        });
        return rootView;
    }
    public void pullData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Next> list = getNextThingPOs();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mNextThingAdapter.updateData(list);
                            mListView.onRefreshComplete();
                        }
                    });
                } catch (AVException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ArrayList<Next> getNextThingPOs() throws AVException {

        AVUser user = AVUser.getCurrentUser();
        List<AVObject> likeList = null;
        if (user != null) {
            AVQuery<AVObject> query = new AVQuery<>(ThingLikeObject.THING_LIKE);
            query.whereEqualTo(ThingLikeObject.USER_ID, user.getObjectId());
            likeList = query.find();
        }
        final ArrayList<Next> list = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>(NextThingObject.NEXT_THING);
        List<AVObject> avObjectList = query.find();
        for (AVObject avObject : avObjectList) {
            Next next = new Next();
            next.setTitle(avObject.getString(NextThingObject.TITLE));
            next.setDescription(avObject.getString(NextThingObject.DESCRIPTION));
            next.setUrl(avObject.getString(NextThingObject.URL));
            next.setVote(avObject.getInt(NextThingObject.VOTE));
            next.setObjectId(avObject.getObjectId());
            next.setLiked(false);
            if (likeList != null && likeList.size() != 0) {
                for (AVObject object : likeList) {
                   if (next.getObjectId().equals(object.getString(ThingLikeObject.THING_ID))) {
                        next.setLiked(true);
                    }
                }
            }
            list.add(next);
        }


        Collections.sort(list, new Comparator<Next>() {
            @Override
            public int compare(Next lhs, Next rhs) {
                return ((Integer)rhs.getVote()).compareTo(lhs.getVote());
            }
        });


        for (Next nextThingPO : list) {
            Dao dao = new Dao(this.getActivity());
            dao.insert(nextThingPO);
        }
        return list;
    }


    private void initData() {
        Dao dao = new Dao(this.getActivity());
        List<Next> list = dao.get();
        mNextThingAdapter.updateData(list);
    }


    public boolean backPress() {
        if (mWebLayout.getVisibility() == View.VISIBLE) {
            mWebLayout.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setWebViewVisible(String url) {
        mWebview.loadUrl(url);
        mWebLayout.setVisibility(View.VISIBLE);
    }
}

