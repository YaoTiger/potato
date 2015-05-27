package com.seable.potato.ui.activity;//package com.seable.withome.property.ui.activity;
//
//
//
//import org.apache.http.util.EncodingUtils;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.webkit.WebSettings;
//import android.webkit.WebSettings.RenderPriority;
//import android.webkit.WebView;
//
//import com.leo.base.activity.LActivity;
//import com.leo.base.application.LApplication;
//import com.leo.base.util.L;
//import com.leo.base.util.Util_Cache;
//import com.leo.base.widget.LWebView;
//import com.leo.base.widget.webview.LWebViewClient;
//import com.seable.withome.property.R;
//import com.seable.withome.property.ui.view.View_TitleBar;
//
///**
// * 
// * @ClassName: McWebViewActivity
// * @Description: 通用webview显示界面
// * @author 王维玉
// * @date 2015-01-23 16:43
// * 
// */
//public class Activity_WebView extends LActivity{
//
//	public static String EXTRA_TITLE="title";
//	public static String EXTRA_WEBURL="weburl";
//	public static String EXTRA_POST="post";
//    private LWebView webview;
//
//    private View_TitleBar titleBar;
//    
//    private View mProgressBar;
//    
//    private String weburl="";
//
//    private String title="";
//	private String post="";
//
//	
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);
//        Intent intent1 = getIntent();
//        title=intent1.getStringExtra(EXTRA_TITLE);
//        weburl = intent1.getStringExtra(EXTRA_WEBURL);
//        post = intent1.getStringExtra(EXTRA_POST);
//        webview = (LWebView) findViewById(R.id.mc_webView);
////        webview.addJavascriptInterface(mContext, "wst");
//        mProgressBar = (View)findViewById(R.id.progress_container);
//        titleBar = (View_TitleBar) findViewById(R.id.TitleBar);
//        titleBar.setBackImshow(true);
//        titleBar.setTitleName(title);
//        //允许webview支持javascript
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setRenderPriority(RenderPriority.HIGH); 
//        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 
//        // 开启 DOM storage API 功能 
//        webview.getSettings().setDomStorageEnabled(true); 
//        //开启 database storage API 功能 
//        webview.getSettings().setDatabaseEnabled(true);  
//        String cacheDirPath = null;
//        if(Util_Cache.isExternalStorageWritable()){
//        	cacheDirPath = LApplication.getInstance().getContext().getExternalFilesDir(null) + "/" + Util_Cache.getCacheDecodeString(weburl);
//        }else{
//        	cacheDirPath =LApplication.getInstance().getCacheDir() + "/" + Util_Cache.getCacheDecodeString(weburl);
//        }
////      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME; 
//        //设置数据库缓存路径 
//        webview.getSettings().setDatabasePath(cacheDirPath); 
//        //设置  Application Caches 缓存目录 
//        webview.getSettings().setAppCachePath(cacheDirPath); 
//        //开启 Application Caches 功能 
//        webview.getSettings().setAppCacheEnabled(true); 
//        
//    }
//    
//    
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        webview.setWebViewClient(new LWebViewClient() {
//
//            @Override
//            public void onPageFinished(WebView view, String url){
//                super.onPageFinished(view, url);
//                mProgressBar.setVisibility(View.GONE);
//                //TODO 回头在玩哈哈哈
////                webview.loadUrl("javascript:con(8)");
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon){
//                super.onPageStarted(view, url, favicon);
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                view.loadUrl(url);
//                return true;
//            }
//        });
////        webview.loadUrl(weburl);//webView 加载url
//        if(post == null ||"".equals(post)){
//        	webview.loadUrl(weburl);//webView 加载url
//        }else{
////      post方式传送参数  
////        	String postData = "id = cid & username = name";  
//        	L.e(post);
//        	webview.postUrl(weburl, EncodingUtils.getBytes(post, "utf-8"));  
//        }
//    }
//    
//    /**跳转到McWebViewAcitity
//     * @param context 上下文
//     * @param title 标题
//     * @param weburl 要加载的url地址
//     * 
//     * */
//	public static void launchActivity(Context context, String title,
//			String weburl) {
//		Intent intent = new Intent(context, Activity_WebView.class);
//		intent.putExtra(EXTRA_TITLE, title);
//		intent.putExtra(EXTRA_WEBURL, weburl);
//		context.startActivity(intent);
//	}
//    
//}
