package com.shinaier.laundry.snlfactory.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.CraftworkAddPriceActivity;
import com.shinaier.laundry.snlfactory.util.ExitManager;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2016/12/8.
 */

public class WebViewActivity extends ToolBarActivity {
    private static final int REQUEST_ADD_PROJECT_CONFIRM = 0x1;

    public static final String EXTRA_URL = "URL";
    public static final String EXTRA_TITLE = "title";
    private String url;
    private String title;
    private ProgressBar mHorizontalProgress;
    private WebView mWebView;
    private ImageView backBtn;
    private boolean mIsImmediateBack = false;
    private boolean mIsLeftBtnDisplay = true;
    private boolean mIsRightBtnDisplay = true;
    private Handler refreshProgressHandler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.arg1 >= 100) {
                if (mHorizontalProgress != null) {
                    mHorizontalProgress.setVisibility(View.GONE);
                }
            } else {
                if (mHorizontalProgress != null && msg.arg1 >= 0) {
                    mHorizontalProgress.setVisibility(View.VISIBLE);
                    mHorizontalProgress.setProgress(msg.arg1);
                }
            }
        }
    };
    private String orderId;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_act);
        ExitManager.instance.addItemActivity(this);

        url = getIntent().getStringExtra(EXTRA_URL);
        title = getIntent().getStringExtra(EXTRA_TITLE);
        orderId = getIntent().getStringExtra("order_id");
        if(!TextUtils.isEmpty(title)){
            setCenterTitle(title);
        }
        mHorizontalProgress = (ProgressBar) findViewById(R.id.progress_horizontal);
        mWebView = (WebView) findViewById(R.id.webview);
        ImageView leftButton = (ImageView) findViewById(R.id.left_button);
        if(leftButton != null){
            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        //设置支持JavaScript
        if(mWebView != null){
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setSupportZoom(true);
            webSettings.setGeolocationEnabled(true);
            webSettings.setDomStorageEnabled(true);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            if (Build.VERSION.SDK_INT > 11) {
                mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.contains("shuxier.com")) {
                        mWebView.loadUrl(url);
                        return true;
                    }
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                    resend.sendToTarget();
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(view.getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                }
            });

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (refreshProgressHandler != null) {
                        if (refreshProgressHandler.hasMessages(0)) {
                            refreshProgressHandler.removeMessages(0);
                        }
                        Message mMessage = refreshProgressHandler.obtainMessage(0, newProgress, 0, null);
                        refreshProgressHandler.sendMessageDelayed(mMessage, 100);
                    }
                }

                public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                    callback.invoke(origin, true, false);
                    super.onGeolocationPermissionsShowPrompt(origin, callback);
                }
            });
            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            mWebView.loadUrl(url);

        }

        mWebView.addJavascriptInterface(new JsInterface(),"jsInterface" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        if (backBtn != null) {
            if (mIsLeftBtnDisplay) {
                backBtn.setVisibility(View.VISIBLE);
            } else {
                backBtn.setVisibility(View.GONE);
            }

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(backBtn.getApplicationWindowToken(), 0);

                    if (mIsImmediateBack) {
                        onBackPressed();
                    } else {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        } else {
                            onBackPressed();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    /**
     * 提供给js调用native
     */
    class JsInterface{
        @JavascriptInterface
        public void nextStep(String data){
            IdentityHashMap<String,String> params = new IdentityHashMap<>();
            params.put("token", UserCenter.getToken(WebViewActivity.this));
            params.put("items",data);
            params.put("oid",orderId);
            requestHttpData(Constants.Urls.URL_POST_ADD_PROJECT_CONFIRM,REQUEST_ADD_PROJECT_CONFIRM, FProtocol.HttpMethod.POST,params);
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_ADD_PROJECT_CONFIRM:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            LogUtil.e("zhang","添加成功");
                            Intent intent = new Intent(this,CraftworkAddPriceActivity.class);
                            intent.putExtra("id",orderId);
                            startActivity(intent);
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
