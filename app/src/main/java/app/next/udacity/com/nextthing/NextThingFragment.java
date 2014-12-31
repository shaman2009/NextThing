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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.util.ArrayList;

import app.next.udacity.com.nextthing.OkHttp.HttpCallBack;
import app.next.udacity.com.nextthing.OkHttp.ThingRequest;
import app.next.udacity.com.nextthing.model.NextThingPO;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingFragment extends Fragment implements WebViewCallBack {

    public static final String WEB_36_KR = "http://www.36kr.com";
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

    public void pullData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<NextThingPO> list = ThingRequest.getThings();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mNextThingAdapter.updateData(list);
                            mListView.onRefreshComplete();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    private void initData() {
        for (int i = 0; i < 2; i++) {
            NextThingPO po = new NextThingPO();
            po.setTitle("36Kr");
            po.setUrl(WEB_36_KR);
            po.setDescription(WEB_36_KR);
            po.setVote(3);
            mNextThingAdapter.addOneData(po);
        }
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

