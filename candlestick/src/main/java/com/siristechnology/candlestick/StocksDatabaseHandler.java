package com.siristechnology.candlestick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class StocksDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StocksDB";
    private static final String TABLE_STOCKS = "StocksTable";

    private static final String COL_STOCK_SYMBOL = "StockSymbol";

    public StocksDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STOCKS + "("
                + COL_STOCK_SYMBOL + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);

        addStock(db, "FB");
        addStock(db, "MSFT");
        addStock(db, "AAPL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS);
        onCreate(db);
    }

    public void addStock(String stockSymbol) {
        SQLiteDatabase db = this.getWritableDatabase();
        addStock(db, stockSymbol);
        db.close();
    }

    public void addStock(SQLiteDatabase db, String stockSymbol) {
        ContentValues values = new ContentValues();
        values.put(COL_STOCK_SYMBOL, stockSymbol);

        db.insert(TABLE_STOCKS, null, values);
    }

    public List<String> getAllStocks() {
        List<String> stocks = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_STOCKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String stock = cursor.getString(0);
                stocks.add(stock);
            } while (cursor.moveToNext());
        }

        return stocks;
    }

    public void deleteStock(String stockSymbol) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STOCKS, COL_STOCK_SYMBOL + " = ?",
                new String[]{String.valueOf(stockSymbol)});
        db.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkDB != null ? true : false;
    }
}
