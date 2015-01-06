package app.next.udacity.com.nextthing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DetailActivity extends FragmentActivity {

    @InjectView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra("url")) {
            String url = intent.getStringExtra("url");
            mTextView.setText(url);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            String url = mTextView.getText().toString();
            share(url);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void share(String text) {
        String subject = this.getString(R.string.action_share);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(Intent.createChooser(intent, subject));
    }
}
