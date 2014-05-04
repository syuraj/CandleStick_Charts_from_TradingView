package com.siristechnology.candlestick;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddRemoveStockActivity extends ListActivity implements StocksListAdapter.IStocksListListener {
    StocksListAdapter adapter;
    StocksDatabaseHandler stocksDb = new StocksDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addremovestock);

        RefreshStocksList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_action_settings_stock_add) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Enter Stock Symbol");
            final EditText input = new EditText(this);
            input.requestFocus();
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String stock = input.getText().toString().toUpperCase();
                    stock = stock.replace(" ", "");
                    stock = stock.replace("$", "");

                    if (!stock.isEmpty()) {
                        stocksDb.addStock(stock);
                        RefreshStocksList();
                    }
                }
            });

            alert.setNegativeButton("Cancel", null);
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshStocksList() {
        try {
            adapter = new StocksListAdapter(this, stocksDb.getAllStocks());
            setListAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteStock(String stock) {
        stocksDb.deleteStock(stock);

        String del_msg = getString(R.string.stock_deleted_msg);
        del_msg = String.format(del_msg, stock);

        Toast toast = Toast.makeText(this, del_msg, Toast.LENGTH_SHORT);
        toast.show();

        RefreshStocksList();
    }

}
