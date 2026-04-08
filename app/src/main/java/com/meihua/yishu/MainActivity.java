package com.meihua.yishu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView mWebView;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 沉浸式状态栏（黑色背景 + 白色图标）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0f0f0f"));
        }

        // 全屏显示（隐藏导航栏虚拟按钮）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        mWebView = new WebView(this);
        setContentView(mWebView);

        // ---- WebView 设置 ----
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);          // 允许 JS
        settings.setDomStorageEnabled(true);          // 允许 localStorage
        settings.setDatabaseEnabled(true);            // 允许数据库
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);               // 禁止缩放（App 体验）
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 允许混合内容（HTTP图片等）
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // User-Agent 加上 App 标识
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + " MeiHuaYiShu/6.0");

        // ---- 注入 Android 桥接对象（供 JS 调用） ----
        mWebView.addJavascriptInterface(new AndroidBridge(this), "Android");

        // ---- WebViewClient：拦截外链 ----
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                // 本地 assets 文件正常加载
                if (url.startsWith("file://")) {
                    return false;
                }
                // HTTP/HTTPS 跳外部浏览器（防止意外跳转）
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    // API 请求由 WebView 内 fetch 直接发出，不拦截
                    return false;
                }
                return true;
            }
        });

        // ---- WebChromeClient：支持 alert/confirm ----
        mWebView.setWebChromeClient(new WebChromeClient());

        // ---- 加载本地 H5 ----
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    // 返回键：优先 WebView 后退，否则退出
    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    // ============================================================
    //  AndroidBridge：JS -> Java 桥接
    //  在 HTML 里通过 window.Android.xxx() 调用
    // ============================================================
    public static class AndroidBridge {
        private final Context mContext;

        AndroidBridge(Context context) {
            this.mContext = context;
        }

        /** 复制文本到剪贴板 */
        @JavascriptInterface
        public void copyText(String text) {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                ClipData clip = ClipData.newPlainText("meihua", text);
                cm.setPrimaryClip(clip);
                // 提示（Android 13+ 系统自带提示，低版本自己 toast）
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    Toast.makeText(mContext, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                }
            }
        }

        /** 显示原生 Toast 通知 */
        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }

        /** 获取 Android 版本 */
        @JavascriptInterface
        public int getAndroidVersion() {
            return Build.VERSION.SDK_INT;
        }

        /** 打开外部浏览器（分享链接等） */
        @JavascriptInterface
        public void openBrowser(String url) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "无法打开链接", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
