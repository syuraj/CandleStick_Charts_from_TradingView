package com.siristechnology.candlestick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    List<String> stockSymbols;
    int currentPosition;
    StocksDatabaseHandler stocksDb = new StocksDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            String currentStockSymbol = stockSymbols.get(currentPosition);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ChartFragment.newInstance(currentStockSymbol))
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        currentPosition = position;

        if (position != stockSymbols.size() - 1) {
            String newStock = stockSymbols.get(position);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ChartFragment.newInstance(newStock))
                    .commit();
            return true;
        } else {
            Intent intent = new Intent(this, AddRemoveStockActivity.class);
            startActivity(intent);

            return true;
        }
    }

    public void refresh() {
        refreshSpinnerTabs();
    }

    private void refreshSpinnerTabs() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        stockSymbols = new ArrayList<String>();
        stockSymbols.addAll(stocksDb.getAllStocks());
        stockSymbols.add(getString(R.string.title_add_remove_stock));

        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        stockSymbols),
                this);
    }
}
