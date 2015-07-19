package com.dhiman_da.leaky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.title_activity_launch);
            setSupportActionBar(toolbar);
        }

        if (findViewById(R.id.activity_list_view) != null) {
            setupList((ListView) findViewById(R.id.activity_list_view));
        }
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
        getMenuInflater().inflate(R.menu.menu_launch, menu);
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

    private void setupList(@NonNull final ListView listView) {
        final List<String> list = new ArrayList<String>();

        list.add(getPackageName() + "." + LeakyActivity.class.getSimpleName());
        list.add(getPackageName() + "." + FixedActivity.class.getSimpleName());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    final Intent intent = new Intent(getBaseContext(), Class.forName(adapter.getItem(position)));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    Snackbar.make(listView, "Activity Not Found, please register in manifest", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
