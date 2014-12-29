package app.next.udacity.com.nextthing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import app.next.udacity.com.nextthing.model.NextThingPO;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingFragment extends Fragment {

    public static final String WEB_36_KR = "http://www.36kr.com";
    NextThingAdapter mNextThingAdapter;

    @InjectView(R.id.listView)
    PullToRefreshListView mListView;
    Handler handler = new Handler();
    @InjectView(R.id.webview)
    WebView mWebview;

    public NextThingFragment() {
        mNextThingAdapter = new NextThingAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);

        // ListView
//        mListView.setVisibility(View.GONE);
        for (int i = 0; i < 2; i++) {
            NextThingPO po = new NextThingPO();
            po.setTitle("36Kr");
            po.setUrl(WEB_36_KR);
            mNextThingAdapter.addOneData(po);
        }
        mListView.setAdapter(mNextThingAdapter);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 5; i++) {
                    NextThingPO po = new NextThingPO();
                    po.setTitle("36Kr");
                    po.setUrl(WEB_36_KR);
                    mNextThingAdapter.addOneData(po);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.onRefreshComplete();
                    }
                }, 100);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final NextThingPO po = (NextThingPO)parent.getItemAtPosition(position);
                view.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWebview.loadUrl(po.getUrl());
                        mWebview.setVisibility(View.VISIBLE);
                    }
                });
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                    }
//                });
            }
        });

        return rootView;
    }


}

