package com.siristechnology.candlestick;

import android.app.Activity;
import android.webkit.JavascriptInterface;


public class StockSymbolJavaScriptInterface {
    String stock;

    StockSymbolJavaScriptInterface(String stock) {
        this.stock = stock;
    }

    @JavascriptInterface
    public String getStockSymbol() {
        return stock;
    }
}
