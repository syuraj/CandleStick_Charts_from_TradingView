package com.siristechnology.candlestick;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class AppWebViewClient extends WebViewClient {
    Context context;

    public AppWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // Ignore SSL certificate errors
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);

        return true;
    }

    @Override
    public void onLoadResource (WebView view, String url) {
        if(view.getHitTestResult() != null &&
                (view.getHitTestResult().getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE ||
                        view.getHitTestResult().getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE)){
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            view.stopLoading();
        }
    }

}
