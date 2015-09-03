package com.tcc.apptcc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.tcc.apptcc.R;


public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // 1. get a reference to recyclerView
        // mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        //TODO: por dados retornados com a pesquisa
       //////// ItemData itemsData[] = { new ItemData("Homem Desaparecido",R.drawable.homem1),
          //      new ItemData("Mulher Desaparecida",R.drawable.mulher1),
         //       new ItemData("Homem Desaparecido",R.drawable.homem2),
          //      new ItemData("Mulher Desaparecida",R.drawable.mulher2),
          //      new ItemData("Homem Desaparecido",R.drawable.homem3),
           //     new ItemData("Mulher Desaparecida",R.drawable.mulher3)};

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, 10);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //TODO specify an adapter (see also next example)
        /*The adapter provides access to the items in your data set,
        creates views for items, and replaces the content of some
         of the views with new data items when the original item is no longer visible
         */
       // mAdapter = new MyAdapter(itemsData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
}
