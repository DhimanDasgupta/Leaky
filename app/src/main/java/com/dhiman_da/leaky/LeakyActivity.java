package com.dhiman_da.leaky;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

public class LeakyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaky);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.title_activity_leaky);
            setSupportActionBar(toolbar);
        }

        new ListGeneratorAsyncTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        final RefWatcher refWatcher = LeakyApplication.getRefWatcher(getBaseContext());
        refWatcher.watch(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaky, menu);
        return true;
    }

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

    private void setupList(@NonNull final List<String> list) {
        final ListView listView = (ListView) findViewById(R.id.activity_list_view);
        if (listView != null) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
    }

    public class ListGeneratorAsyncTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            final List<String> list = new ArrayList<String>();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 100; i++) {
                list.add("Item Number " + (i + 1));
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<String> list) {
            super.onPostExecute(list);

            setupList(list);
        }
    }
}
